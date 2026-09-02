import firebase_admin
from firebase_admin import credentials, firestore
import time
import requests
from smart_room_ai import smart_room_ai

cred = credentials.Certificate(
    "firebase-service-account.json"
)  # İndirdiğin JSON dosyasının adı
firebase_admin.initialize_app(cred)
db = firestore.client()

SENSOR_URL = "http://localhost:8080/api/smart-room-monitor/sensor-data/room/1/latest"
WEATHER_URL = "http://localhost:8080/api/weather/tomorrow"

INTERVAL_SECONDS = 10  # kaç saniyede bir çalışsın


def get_sensor_data():
    response = requests.get(SENSOR_URL, timeout=5)
    response.raise_for_status()
    return response.json()


def get_weather_data():
    response = requests.get(WEATHER_URL, timeout=5)
    response.raise_for_status()
    return response.json()


def run():
    sensor_data = get_sensor_data()
    weather_data = get_weather_data()

    # AI tavsiyelerini al
    recommendations = smart_room_ai(
        temperature=sensor_data["temperature"],
        humidity=sensor_data["humidity"],
        motion=sensor_data["motion"],
        light_level=sensor_data["lightLevel"],
        tomorrow_temp=weather_data.get("temperature"),
    )

    # Tavsiyeleri tek bir metin yap
    ai_cumlesi = " ".join(recommendations)

    # --- KRİTİK KISIM BURASI ---
    # Firebase'e gönderilen pakete ai_notu'nu ekliyoruz
    data_to_send = {
        "sicaklik": sensor_data["temperature"],
        "nem": sensor_data["humidity"],
        "isik": sensor_data["lightLevel"],
        "hareket": sensor_data["motion"],
        "ai_notu": ai_cumlesi,  # Bu satır eksik olduğu için siliniyor!
    }

    # Veriyi gönder
    db.collection("odadurumu").document("oda1").set(data_to_send)
    print(">>> Veriler ve AI Notu başarıyla güncellendi.")

    print("\n--- AI ÖNERİLERİ ---")
    for r in recommendations:
        print("-", r)


if __name__ == "__main__":
    print("🔁 Smart Room AI çalışıyor (CTRL+C ile durdur)")

    while True:
        try:
            run()
        except Exception as e:
            print("Hata oluştu:", e)

        time.sleep(INTERVAL_SECONDS)
