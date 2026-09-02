from datetime import datetime


def get_time_period(hour):
    if 6 <= hour < 12:
        return "morning"
    elif 12 <= hour < 18:
        return "day"
    elif 18 <= hour < 23:
        return "evening"
    else:
        return "night"


def calculate_brightness(light_level, max_value=63695):
    """
    Sensör ters çalışıyor:
    Yüksek değer = karanlık
    """
    brightness = 100 - (light_level / max_value * 100)
    return max(0, min(100, brightness))


def get_stable_message(period):
    if period == "morning":
        return "Her şey stabil görünüyor. Güzel bir sabah dileriz ☀️"
    elif period == "day":
        return "Oda koşulları ideal. İyi çalışmalar dileriz."
    elif period == "evening":
        return "Her şey yolunda. Keyifli bir akşam geçirmenizi dileriz 🌆"
    else:
        return "Oda koşulları stabil. İyi geceler 🌙"


def smart_room_ai(
    temperature,
    humidity,
    motion,
    light_level,
    tomorrow_temp=None
):
    recommendations = []

    now = datetime.now()
    period = get_time_period(now.hour)
    brightness = calculate_brightness(light_level)

    # ------------------------------------------------
    # 💡 IŞIK & HAREKET (HER ZAMAN DEĞERLENDİRİLİR)
    # ------------------------------------------------

    if period == "night" and brightness > 40 and not motion:
        recommendations.append(
            "Şu an gece saati, odada hareket algılanmadı. Lütfen ışıkları kapatınız."
        )

    if period == "night" and brightness < 30 and motion:
        recommendations.append(
            "Gece saatlerinde odada hareket algılandı ancak ortam karanlık. Işık açılabilir."
        )

    if period in ["morning", "day", "evening"] and brightness < 30 and motion:
        recommendations.append(
            "Odada hareket var ancak ortam karanlık. Aydınlatma artırılabilir."
        )

    if period in ["morning", "day", "evening"] and brightness > 50 and not motion:
        recommendations.append(
            "Odada hareket algılanmadı ancak ışıklar açık. Enerji tasarrufu için kapatılabilir."
        )

    # ------------------------------------------------
    # 🌡️ SICAKLIK
    # ------------------------------------------------

    if temperature < 20:
        recommendations.append("Oda sıcaklığı düşük. Isıtma artırılabilir.")
    elif temperature > 28:
        recommendations.append("Oda sıcaklığı yüksek. Serinletme açılabilir.")

    # ------------------------------------------------
    # 💧 NEM
    # ------------------------------------------------

    if humidity < 30:
        recommendations.append("Nem oranı düşük, hava kuru olabilir.")
    elif humidity > 60:
        recommendations.append("Nem oranı yüksek, havalandırma önerilir.")

    # ------------------------------------------------
    # 🌤️ YARININ HAVASI (SADECE AKŞAM)
    # ------------------------------------------------

    if period == "evening" and tomorrow_temp is not None:
        if tomorrow_temp < 10:
            recommendations.append(
                f"Yarın hava {tomorrow_temp}°C olacak. Daha sıcak bir ortam planlanabilir."
            )
        elif tomorrow_temp > 30:
            recommendations.append(
                f"Yarın hava {tomorrow_temp}°C olacak. Serinletme ayarları gözden geçirilebilir."
            )

    # ------------------------------------------------
    # 🌿 HİÇBİR KURAL TETİKLENMEDİYSE
    # ------------------------------------------------

    if not recommendations:
        recommendations.append(get_stable_message(period))

    return recommendations
