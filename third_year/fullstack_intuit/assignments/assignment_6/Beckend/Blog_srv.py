from crypt import methods
from email import header
from optparse import Values
from flask import Flask, Requestfrom, request
from numpy import record
from requests import head 
from settings import dbpwd
import mysql.connector as mysql
import json

db = mysql.connect(
    host = "localhost"
    user = "root"
    password = dbpwd
    database = "blogdb"
)

app = Flask(__name__)

@app.route('/posts', methods['GET'])
def get_all_posts():
    query = "select id, title, content, user_id, category_id, created_at, utdated_at from Posts" 
    curser = db.cursor()
    curser.execute(query)
    record = curser.fetchall()
    curser.close()
    header = ["id", "title", "content", "user_id", "category_id", "created_at", "utdated_at"]
    data = []
    for r in record:
        data.append(dict(zip(header, r)))
    return json.dumps(data)


@app.route('/newPost' , methods['POST'])
def add_new_post():
    data = request.get_json
    query = 'insert into Posts (title, content, user_id, category_id, created_at, utdated_at)values(%s, %s, %s, %s, %s, %s)'
    values = (data['title'], data['content'], 5, 1, '2023-05-21 14:45:00', '2023-05-21 15:30:00')
    cursor = db.cursor()
    cursor.execute(query, values)
    db.commit()
    new_post_id = cursor.lastrowid
    cursor.close()
    return get_post(new_post_id)

def get_post(post_id):
    query = "select id, title, content, user_id, category_id, created_at, utdated_at from Posts where id = %s"
    values = (post_id,)
    cursor = db.cursor()
    cursor.execute(query, values)
    record = cursor.fetchone()
    cursor.close()
    header = ["id", "title", "content", "user_id", "category_id", "created_at", "utdated_at"] 
    return json.dumps(dict(zip(header, record)))