import database as db 

conn = db.connect_to_db("localhost", "root", "password", "database_name")


def getLoan(id):
	from_db = []
	businessId = id
	qry = """ SELECT business_credit_histories.loan_name, business_credit_histories.expected_clearance_date , 
	business_credit_histories.actual_clearance_date FROM business_credit_histories
	 WHERE business_credit_histories.business_id =businessId """

	results = db.read_query(conn, qry)
	for result in results:
		res = list(result)
		from_db.append(res)

	return from_db

def getAmount(id):
	from_db = []
	businessId = id
	qry = """ SELECT business_credit_histories.loan_name, business_credit_histories.amount_borrowed , 
	business_credit_histories.amount_paid FROM business_credit_histories
	 WHERE business_credit_histories.business_id =businessId """

	results = db.read_query(conn, qry)
	for result in results:
		res = list(result)
		from_db.append(res)

	return from_db


def get_account_age(id):
	dates= []
	businessId = id
	qry2 = """ SELECT """
	qry = """ SELECT business_credit_histories.loan_name FROM business_credit_histories
	 WHERE business_credit_histories.business_id =businessId """

	results = db.read_query(conn, qry)
	for result in results:
		loanId = result
		qry2 = """ SELECT loans.loan_provider, loans.date_created WHERE loans.id = loanId """
		results2 = db.read_query(conn, qry2)

		for res in results2:
			dates.append(res[1])

	sort_dates = dates.sort()
	return sort_dates[0]

def last_used(id):
	dates= []
	businessId = id
	qry2 = """ SELECT """
	qry = """ SELECT business_credit_histories.loan_name FROM business_credit_histories
	 WHERE business_credit_histories.business_id =businessId """

	results = db.read_query(conn, qry)
	for result in results:
		loanId = result
		qry2 = """ SELECT loans.loan_provider, loans.date_created WHERE loans.id = loanId """
		results2 = db.read_query(conn, qry2)

		for res in results2:
			dates.append(res[1])

	sort_dates = dates.sort(reverse=True)
	return sort_dates[0]
		
def get_innquiries(id):
	businessId =id
	from_db = []
	qry = """ SELECT loan_applications.date_created FROM loan_applications
	 WHERE loan_applications.business_id =businessId """

	results = db.read_query(conn, qry)
	for i in results:
		from_db.append(i)

	return from_db




