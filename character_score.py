import pandas as pd 
from datetime import date, timedelta
import data_reading as dr 
import sys


def payments(b_id):
	business_id = b_id
	deliquencies = 0
	missed_payment = 0
	arrears = []
	recently_missed =[]

	data = dr.getLoan(business_id)
	loan_ids = []
	due_dates = []
	pay_dates = []

	for i in data:
		loan_ids.append(i[0])
		due_dates.append(i[1])
		pay_dates.append(i[2])

	payments_due_dates = {loan_ids[i]: due_dates[i] for i in range(len(loan_ids))}
	actual_pay_dates = {loan_ids[i]: pay_dates[i] for i in range(len(loan_ids))}

	for x in loan_ids:
		pay_date = actual_pay_dates[x]
		if (pay_date != ''):
			if (pay_date== payments_due_dates[x]):
				pass
			else:
				ar = pay_date - payments_due_dates[x]
				arrears.append(ar)
		else:
			deliquencies = deliquencies + 1
			ar = date.today() - payments_due_dates[x]
			arrears.append(ar)
			recently_missed.append(payments_due_dates[x])

		payment_score = pay_score(arrears, missed_payment, recently_missed, deliquencies)
		return payment_score


def pay_score(ars, missed, recent, dels):
	number = 0
	arrears_score = 0
	missed_payment_score =0
	deliquency_score = 0
	recently_missed_score =0


	average_arrears = sum(ars)/len(ars)
	if (average_arrears>=8):
		arrears_score = 0
	else:
		if ((average_arrears>=1) & (average_arrears<=7) ):
			arrears_score = 2
		else:
			if (average_arrears == 0):
				arrears_score = 4


	if (missed == 0):
		missed_payment_score = 4
	else:
		if ((missed == 1) | (missed == 2)):
			missed_payment_score = 2
		else:
			if( missed > 3):
				missed_payment_score = 0

	if (dels == 0):
		deliquency_score = 4
	else:
		deliquency_score = 0


	
	start_date = date.today() - timedelta(days=60)
	end_date = date.today()
	for rec in recent:
		if ((rec>= start_date) & (rec<end_date)):
			number = number + 1

	if (number == 0):
		recently_missed_score = 4
	else:
		if(number == 1):
			recently_missed_score = 2
		else:
			recently_missed_score = 0

	payment_histroy_score = (arrears_score + missed_payment_score + deliquency_score + recently_missed_score)

	return payment_histroy_score


def amount_owed(b_id):
	business_id = b_id
	loans = []
	payments =[]

	amount = dr.getAmount(business_id)
	for i in amount:
		loans.append(i[1])
		payments.append(i[2])


	loan =sum(loans)
	paid = sum(payments)
	balances = (loan - paid)


	utilization_score = utilization_ratio_score(balances, loan)
	balances_score = balances(balances)
	amount_score = (utilization_score + balances_score) 
	return amount_score

def utilization_ratio_score(balance, loan):
	util_score = 0
	ratio = (balance/loan)

	if (ratio >=0.5):
		util_score = 0
	else:
		util_score = 4

	return util_score

def balances(balance):
	balance_score = 0
	if (balance > 10000000):
		balance_score = 0
	else:
		if ((balance > 1000000) & (balance < 10000000)):
			balance_score = 2
		else:
			if (balance_ratio < 1000000):
				balance_score = 4
	return balance_score



def account_length(b_id):
	business_id = b_id
	created_date = dr.get_account_age(business_id)
	latest_usage = dr.last_used(business_id)
	age_score = account_age(created_date)
	last_used = last_used(latest_usage)
	length_score = (age_score + last_used)
	return length_score


def account_age(Date):
	# Select date created from user table where user_id = user_id
	age_score = 0
	date_created = date(Date)
	age = date.today() - date_created

	if ((age>=30) & (age<60)):
		age_score = 2
	else:
		if (age>60):
			age_score = 4
		else:
			if ((age>=0) & (age<30)):
				age_score = 0
	return age_score


def last_used(Date):
	activity_score = 0
	latest_loan = date(Date)
	life = date.today() - latest_loan

	if (life > 120):
		activity_score = 0
	else:
		activity_score = 4

	return activity_score



def recent_credit_activity(b_id):
	business_id = b_id
	score = 0
	count = get_inquiries(business_id)

	if (count <4):
		score = 2
	else:
		score = 0

	return score


def get_inquiries(b_id):
	business_id =b_id
	inquiries = 0
	application_dates = dr.get_inquiries(business_id)
	app_dates = application_dates.sort(reverse=True)

	# 2 months period
	for i in app_dates:
		if ((date.today()-i)<60):
			inquiries = inquiries+1
	return inquiries


def character_score(Business):
	business = Business

	weights = [1.6, 1.2, 0.6, 0.6]
	payment_score = payments(business)
	amount_owed = amount_owed(business)
	length = account_length(business)
	recent_activity = recent_credit_activity(business)
	scores = [payment_score, amount_owed, length, recent_activity]
	weighted_score =[(1.6*payment_score), (1.2*amount_owed), (0.6*length), (0.6*recent_activity)]

	df = {'Factors': ['Payment History', 'Amount Owed', 'Account Length', 'Recent Activity'],
	'Scores':[payment_score, amount_owed, length, recent_activity], 
	'Weighted Scores':[(1.6*payment_score), (1.2*amount_owed), (0.6*length), (0.6*recent_activity)]}
	scores_df = pd.DataFrame(df)

	w_score = sum(weighted_score)

	return w_score






