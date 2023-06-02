import requests

from flask import Flask, request

app = Flask(__name__)

# openweathermap API endpoint
WEATHER_API_ENDPOINT = "https://api.openweathermap.org/data/2.5/weather"

# giphy API endpoint
GIPHY_API_ENDPOINT = "https://api.giphy.com/v1/gifs/translate"

# API keys
WEATHER_API_KEY = "<your openweathermap API key>"
GIPHY_API_KEY = "<your giphy API key>"

# map temperature ranges to corresponding Giphy search terms
TEMP_TO_GIF_MAPPING = {
    range(-273, 0): "snow",
    range(0, 10): "cold",
    range(10, 20): "mild",
    range(20, 30): "warm",
    range(30, 40): "hot",
    range(40, 100): "fire",
}


@app.route("/")
def home():
    city_name = request.args.get("city")

    # make a request to openweathermap API to get temperature
    weather_response = requests.get(
        WEATHER_API_ENDPOINT,
        params={"q": city_name, "appid": WEATHER_API_KEY, "units": "metric"},
    )

    if weather_response.ok:
        temperature = weather_response.json()["main"]["temp"]

        # find the corresponding Giphy search term based on temperature
        for temp_range, search_term in TEMP_TO_GIF_MAPPING.items():
            if temperature in temp_range:
                break

        # make a request to giphy API to get a gif
        giphy_response = requests.get(
            GIPHY_API_ENDPOINT,
            params={"api_key": GIPHY_API_KEY, "s": search_term},
        )

        if giphy_response.ok:
            gif_url = giphy_response.json()["data"]["images"]["downsized"]["url"]
            return f"The temperature in {city_name} is {temperature}°C. <img src='{gif_url}'/>"
        else:
            return "Failed to get gif from Giphy API"
    else:
        return "Failed to get temperature from OpenWeatherMap API"


if __name__ == "__main__":
    app.run(debug=True)
