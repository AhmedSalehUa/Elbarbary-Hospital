from zk import ZK, const
import json
conn = None
# create ZK instance
zk = ZK('192.168.1.99', port=4370, timeout=5, password=0, force_udp=False, ommit_ping=False)
try:
    # connect to device
    conn = zk.connect()
    # disable device, this method ensures no activity on the device while the process is run
    conn.disable_device() 
    # another commands will be here!
    # Example: Get All Users
    
   # conn.restart()
    attendances = conn.get_attendance()
    theset = []
    for attend in  attendances:
        x = {
            "id" : attend.user_id,
            "time" : attend.timestamp.strftime("%m/%d/%Y, %H:%M:%S")
        }
        theset.append(x)
    jsonData=json.dumps(theset)
    print(jsonData)
   
     
    # Test Voice: Say Thank You
    conn.test_voice()
    # re-enable device after all commands already executed
    conn.enable_device()
except Exception as e:
    print ("Process terminate : {}".format(e))
finally:
    if conn:
        conn.disconnect()