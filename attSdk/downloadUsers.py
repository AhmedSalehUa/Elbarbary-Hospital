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
        zk = ZK(x[2], port=int(x[3]), timeout=5, password=int(
            x[4]), force_udp=False, ommit_ping=False)

        try:
            conn = zk.connect()
            conn.disable_device()
            print("get attend from device")
            users = conn.get_users()
            conn.test_voice()
            conn.enable_device()
            print("add users to database")
            for user in users:
                privilege = 'User'
                if user.privilege == const.USER_ADMIN:
                   privilege = 'Admin'
                sql = "REPLACE INTO `att_device_users`(`uid`, `name`, `privileges`, `password`, `groupId`, `userId`, `card`) VALUES  (%s,%s,%s,%s,%s,%s,%s)"
                val = (user.uid,user.name,privilege,user.password,user.group_id,user.user_id,user.card)
                cur.execute(sql, val)
            
            db.commit()
            '''print("delete duplicate value")
            cur.execute("""DELETE t1 FROM att_attendance t1
                            INNER JOIN att_attendance t2 
                            WHERE 
                            t1.id < t2.id AND 
                            t1.employee_id = t2.employee_id and t1.date = t2.date AND t1.time = t2.time;""")
            db.commit()
            cur.execute("DELETE FROM `att_attendance` WHERE `date`='0000-00-00'")
            print("delete null value")
            db.commit() '''
            
        except Exception as e:
            print("Process terminate : {}".format(e))
        finally:
            if conn:
                conn.disconnect()