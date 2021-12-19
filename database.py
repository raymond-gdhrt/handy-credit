import mysql.connector
from mysql.connector import Error
import pandas as pd 
 

def create_server_connection(host_name, user_name, user_password):
	connection = None
	try:
		connection = mysql.connector.connect(
			host = host_name,
			user = user_name,
			passwd = user_password)
		print("MySQL Database Connection Successful")

	except Error as err:
		print(f"Error:'{err}'")
	return connection


def create_db(connection, query):
	cursor = connection.cursor()
	try:
		cursor.execute(query)
		print("DB Created Successfully")

	except Error as err:
		print(f"Error: '{err}'")


def connect_to_db(host_name, user_name, user_password, db_name):
	connection = None
	try:
		connection = mysql.connector.connect(
			host = host_name,
			user = user_name,
			passwd = user_password, 
			database = db_name)
		print("Database Connection Successful")

	except Error as err:
		print(f"Error: '{err}'")

	return connection


def execute_query(connection, query):
	cursor = connection.cursor()
	try:
		cursor.execute(query)
		connection.commit()
		print("Query Successful")
	except Error as err:
		print(f"Error: '{err}'")


def read_query(connection, query):
	cursor = connection.cursor
	result = None
	try:
		cursor.execute(query)
		result = cursor.fetchall()
		return result

	except Error as err:
		print(f"Error: '{err}'")







conn = connect_to_db('localhost', 'root', 'mak123', 'handy')
