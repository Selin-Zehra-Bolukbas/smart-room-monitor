import machine
import time
import network
import urequests
import json
import dht

# --- AYARLAR ---
WIFI_SSID = "YOUR_WIFI_SSID"
WIFI_PASSWORD = "YOUR_WIFI_PASSWORD"
LOCAL_API_URL = "http://YOUR_BACKEND_HOST:8080/api/smart-room-monitor/sensor-data"
# Firestore REST API URL
FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/smart-monitor-eeenub/databases/(default)/documents/odadurumu/oda1"

# --- SENSÖRLER ---
ldr_pin = machine.ADC(26)
pir_pin = machine.Pin(15, machine.Pin.IN)
dht_sensor = dht.DHT11(machine.Pin(22))

# --- WIFI BAĞLANTISI ---
wlan = network.WLAN(network.STA_IF)
wlan.active(True)


def connect_wifi():
    if not wlan.isconnected():
        print("WiFi aranıyor...")
        wlan.connect(WIFI_SSID, WIFI_PASSWORD)
        while not wlan.isconnected():
            time.sleep(1)
    print("Bağlantı tamam:", wlan.ifconfig()[0])


connect_wifi()

while True:
    try:
        # 1. Sensörleri Oku
        isik_val = ldr_pin.read_u16()
        hareket_val = bool(pir_pin.value())
        sicaklik_val, nem_val = 0, 0
        try:
            dht_sensor.measure()
            sicaklik_val = dht_sensor.temperature()
            nem_val = dht_sensor.humidity()
        except:
            print("DHT hatası")

        # 2. Yerel Veritabanı İçin JSON (Spring Boot)
        local_data = {
            "lightLevel": isik_val,
            "motion": hareket_val,
            "roomId": 1,
            "temperature": sicaklik_val,
            "humidity": nem_val,
        }

        # 3. Firestore İçin JSON (Görseldeki isimlerle uyumlu)
        firestore_data = {
            "fields": {
                "isik": {"integerValue": isik_val},
                "hareket": {"booleanValue": hareket_val},
                "sicaklik": {"integerValue": sicaklik_val},
                "nem": {"integerValue": nem_val},
            }
        }

        # --- GÖNDERİM İŞLEMLERİ ---

        # Yerel Sunucuya Gönder
        try:
            print("\nYerel API'ye gönderiliyor...")
            res_l = urequests.post(LOCAL_API_URL, json=local_data)
            print("Yerel Durum:", res_l.status_code)
            res_l.close()
        except:
            print("Yerel sunucuya ulaşılamadı.")

        # Firestore'a Gönder (UpdateMask ile sadece belirtilen alanları günceller)
        try:
            print("Firestore'a (oda1) yazılıyor...")
            mask = "?updateMask.fieldPaths=isik&updateMask.fieldPaths=hareket&updateMask.fieldPaths=sicaklik&updateMask.fieldPaths=nem"
            res_f = urequests.patch(FIRESTORE_URL + mask, json=firestore_data)
            print("Firestore Durum:", res_f.status_code)
            res_f.close()
        except Exception as e:
            print("Firestore hatası:", e)

    except Exception as e:
        print("Döngü hatası:", e)

    print("10 saniye bekleme...")
    time.sleep(10)
