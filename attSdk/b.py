import mysql.connector

db = mysql.connector.connect(host="localhost",
                    user="acapytradeahmedsaleh",
                    password="as01203904426", 
                    db="elbarbary_hospital")

cur = db.cursor()
cur.execute("select * from users")
myresult = cur.fetchall()

for x in myresult:
  print(x)