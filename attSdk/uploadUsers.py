import mysql.connector
from zk import ZK, const
import json
conn = None
db = None
try:
    db = mysql.connector.connect(host="localhost",
                                 user="acapytradeahmedsaleh",
                                 password="as01203904426",
                                 db="elbarbary_hospital")
    print("connect")
except Exception as e:
    print("try to ip")
    try:
        db = mysql.connector.connect(host="192.168.1.90",
                                     user="acapytradeahmedsaleh",
                                     password="as01203904426",
                                     db="elbarbary_hospital")
    except Exception as e:
        print("cannt connect to db")

if(db):
    cur = db.cursor()
    cur.execute("SELECT * FROM `att_machines` WHERE `id` in (SELECT `device_id` FROM `att_target_devices`)")
    myresult = cur.fetchall()

    for x in myresult:
        zk = ZK(x[2], port=int(x[3]), timeout=5, password=int(x[4]), force_udp=False, ommit_ping=False)

        try:
            print("get users from database")
            cur.execute("SELECT * FROM `att_device_users`")
            myusers = cur.fetchall() 
            conn = zk.connect()
            conn.disable_device()
            print("add users to device")
            for a in myusers:
                if a[2] == "User":
                    conn.set_user(uid=a[0], name=a[1], privilege=const.USER_DEFAULT, password=a[3], user_id=a[5])
                else:
                    conn.set_user(uid=a[0], name=a[1], privilege=const.USER_ADMIN, password=a[3], user_id=a[5])
             
            conn.test_voice()
            conn.enable_device() 
            print("done")
        except Exception as e:
            print("Process terminate : {}".format(e))
        finally:
            if conn:
                conn.disconnect()
