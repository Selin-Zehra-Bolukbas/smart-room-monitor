import firebase_admin
from firebase_admin import credentials, firestore
import serial
import time

# Firebase Hazırlık
cred = credentials.Certificate("firebase-service-account.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

# Seri Port Bağlantısı (Pico'nun bağlı olduğu port - COM5 olduğunu görüyorum)
ser = serial.Serial("COM5", 115200, timeout=1)

while True:
    if ser.in_waiting > 0:
        line = ser.readline().decode("utf-8").strip()
        print(f"Pico'dan Gelen: {line}")  # 25,40,150,1 gibi bir veri gelir

        try:
            v = line.split(",")
            data = {
                "sicaklik": float(v[0]),
                "nem": float(v[1]),
                "isik": float(v[2]),
                "hareket": v[3] == "1",
            }
            db.collection("odadurumu").document("oda1").set(data)
            print(">>> Firebase Güncellendi!")
        except:
            continue
