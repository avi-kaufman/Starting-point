from flask import Flask, request, jsonify, send_from_directory
from bs4 import BeautifulSoup
import requests
import sqlite3
from datetime import datetime

app = Flask(__name__, static_folder="frontend")

def create_database():
    conn = sqlite3.connect('search_history.db')
    conn.execute('''CREATE TABLE IF NOT EXISTS searches (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        user_id INTEGER,
        query TEXT,
        time TIMESTAMP,
        item_name TEXT,
        amazon_com_price REAL,
        amazon_co_uk_price REAL,
        amazon_de_price REAL,
        amazon_ca_price REAL
    )''')
    conn.commit()
    conn.close()

create_database()

@app.route('/')
def serve_frontend():
    return send_from_directory('frontend', 'index.html')

def scrape_amazon_com(query):
    url = f'https://www.amazon.com/s?field-keywords={query}'
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3'}
    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, 'html.parser')
    return soup

@app.route('/search', methods=['POST'])
def search():
    query = request.form['query']
    # Scrape the top 10 results from Amazon.com
    soup = scrape_amazon_com(query)

    results = []
    for item in soup.find_all('div', {'data-index': True})[:10]:
        title = item.find('span', {'class': 'a-size-medium'})
        image = item.find('img', {'class': 's-image'})
        if title and image:
            results.append({'name': title.text.strip(), 'image': image['src']})

    # Save the search history in the SQLite database
    # For simplicity, we set user_id to 1 (the same for all users)
    conn = sqlite3.connect('search_history.db')
    cursor = conn.cursor()
    cursor.execute('INSERT INTO searches (user_id, query, time) VALUES (?, ?, ?)', (1, query, datetime.now()))
    conn.commit()
    conn.close()

    # Return the results as a JSON object
    return jsonify(results)

@app.route('/past_searches', methods=['GET'])
def past_searches():
    # Fetch past searches from the SQLite database
    conn = sqlite3.connect('search_history.db')
    cursor = conn.cursor()
    cursor.execute('SELECT * FROM searches WHERE user_id = 1')
    searches = cursor.fetchall()
    conn.close()

    # Return the results as a JSON object
    results = []
    for search in searches:
        result = {
            'query': search[2],
            'time': search[3],
            'item_name': search[4],
            'amazon_com_price': search[5],
            'amazon_co_uk_price': search[6],
            'amazon_de_price': search[7],
            'amazon_ca_price': search[8]
        }
        results.append(result)

    return jsonify(results)

if __name__ == '__main__':
    app.run()

