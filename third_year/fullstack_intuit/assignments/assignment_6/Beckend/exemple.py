# pip3 install mysql-connector-python
​
from flask import Flask, request
from settings import dbpwd
import mysql.connector as mysql
import json
​
db = mysql.connect(
	host = "localhost",
	user = "root",
	passwd = dbpwd,
	database = "world")
​
print(db)
​
app = Flask(__name__)
​
@app.route('/cities', methods=['GET', 'POST'])
def manage_cities():
	if request.method == 'GET':
		return get_all_cities()
	else:
		return add_city()
​
def get_all_cities():
	query = "select id, name, population from cities"
	cursor = db.cursor()
	cursor.execute(query)
	records = cursor.fetchall()
	cursor.close()
	print(records)
	# [(1, 'Herzliya', 95142), (2, 'Tel Aviv', 435855), (3, 'Jerusalem', 874186), (4, 'Bat Yam', 128898), (5, 'Ramat Gan', 153135), (6, 'Eilat', 47800), (7, 'Petah Tikva', 233577), (8, 'Tveriya', 41300)]
	header = ['id', 'name', 'population']
	data = []
	for r in records:
		data.append(dict(zip(header, r)))
	return json.dumps(data)
​
def get_city(id):
	query = "select id, name, population from cities where id = %s"
	values = (id,)
	cursor = db.cursor()
	cursor.execute(query, values)
	record = cursor.fetchone()
	cursor.close()
	header = ['id', 'name', 'population']
	return json.dumps(dict(zip(header, record)))
​
def add_city():
	data = request.get_json()
	print(data)
	query = "insert into cities (name, population) values (%s, %s)"
	values = (data['name'], data['population'])
	cursor = db.cursor()
	cursor.execute(query, values)
	db.commit()
	new_city_id = cursor.lastrowid
	cursor.close()
	return get_city(new_city_id)
​
if __name__ == "__main__":
	app.run()