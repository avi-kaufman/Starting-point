import os
from pocketsphinx import AudioFile, get_model_path, Decoder

# Set input and output file paths
input_file = 'soccer_game.mp4'
output_audio_file = 'soccer_game_audio.wav'
exciting_words_file = 'exciting_words.txt'
output_gif_file = 'important_game_moments.gif'

# Step 1: Extract audio from video
subprocess.run(['ffmpeg', '-i', input_file, '-vn', '-acodec', 'pcm_s16le', '-ar', '44100', '-ac', '2', output_audio_file])

# Step 2: Analyze audio for peak volumes
sound = AudioSegment.from_wav(output_audio_file)
sound_arr = np.array(sound.get_array_of_samples())
window_size = 1000  # in milliseconds
stride = 500  # in milliseconds
max_volume_threshold = 0.8  # maximum volume threshold for detecting peaks
important_moments = []
for i in range(0, len(sound_arr) - window_size, stride):
    window = sound_arr[i:i+window_size]
    max_volume = np.max(np.abs(window)) / 2**15  # normalize to [-1, 1] range
    if max_volume > max_volume_threshold:
        important_moments.append(i / 1000)  # convert to seconds

# Step 3: Convert audio to text using Pocketsphinx
model_path = get_model_path()
config = Decoder.default_config()
config.set_string('-hmm', os.path.join(model_path, 'en-us'))
config.set_string('-lm', os.path.join(model_path, 'en-us.lm.bin'))
config.set_string('-dict', os.path.join(model_path, 'cmudict-en-us.dict'))
decoder = Decoder(config)
decoder.start_utt()
with AudioFile(output_audio_file) as f:
    for block in f:
        decoder.process_raw(block.data, False, False)
decoder.end_utt()
text = decoder.hyp().hypstr

# Step 4: Search for exciting words in the text
with open(exciting_words_file) as f:
    exciting_words = set([line.strip() for line in f])
exciting_moments = []
for word in exciting_words:
    index = 0
    while True:
        index = text.find(word, index)
        if index == -1:
            break
        exciting_moments.append(decoder.get_lattice().bestpath(index).start_frame / 100)
        index += len(word)

# Step 6: Print important game moments
important_moments = sorted(list(set(important_moments + exciting_moments)))
for moment in important_moments:
    print(f"{moment:.2f} seconds")

# Step 7: Save important frames as a GIF
output_frames = []
cap = cv2.VideoCapture(input_file)
for moment in important_moments:
    cap.set(cv2.CAP_PROP_POS_FRAMES, int(moment * cap.get(cv2.CAP_PROP_FPS)))
    ret, frame = cap.read()
    if ret:
        output_frames.append(frame)
cap.release()
imageio.mimsave(output_gif_file, output_frames, duration=0.5)
