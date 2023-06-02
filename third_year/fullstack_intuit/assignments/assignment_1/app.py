from flask import Flask
import requests
from keys import weather_api_key, giphy_api_key

app = Flask(__name__)


@app.route('/')
def hello():
    return "Welcome to my weather app!"




@app.route('/weather/<city>')
def weather(city):
    weather_url = "https://api.openweathermap.org/data/2.5/weather"
    weather_params = {'q': city, 'units': 'metric', 'appid': weather_api_key}
    weather_response = requests.get(url=weather_url, params=weather_params)
    weather_response = float(str(weather_response.json()['main']['temp']))
    temp_to_giphy = ''
    if weather_response < 0:
        temp_to_giphy = "snow"
    elif weather_response < 10:
        temp_to_giphy = "cold"
    elif weather_response < 20:
        temp_to_giphy = "mild"
    elif weather_response < 30:
        temp_to_giphy = "warm"
    elif weather_response < 40:
        temp_to_giphy = "hot"
    else:
        word_to_gif = "fire"
    giphy_url = "https://api.giphy.com/v1/gifs/translate"
    giphy_params = {'api_key': giphy_api_key, 's': temp_to_giphy}
    gif_response = requests.get(url=giphy_url, params=giphy_params)
    res = str(gif_response.json()['data']['images']['original']['url'])
    return f"The weather in {city} now are {weather_response} Celsius degree.<img src={res}>"


if __name__ == "__main__":
    app.run()
