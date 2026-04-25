package com.example.healifyapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorProfileBooking(
    val name: String,
    val title: String,
    val category: String,
    val cv: String
) : Parcelable

val allDoctors = listOf(
    // Rinoplasti
    DoctorProfileBooking("Prof. Dr. Ahmet Yılmaz", "KBB Uzmanı", "Rinoplasti", "25 yıllık burun cerrahisi tecrübesi."),
    DoctorProfileBooking("Doç. Dr. Selin Erdem", "Estetik Cerrah", "Rinoplasti", "Piezo cerrahisi ve revizyon uzmanı."),
    DoctorProfileBooking("Op. Dr. Kerem Soylu", "KBB Uzmanı", "Rinoplasti", "Fonksiyonel ve estetik burun cerrahisinde 15 yıl."),

    // Saç Ekimi
    DoctorProfileBooking("Uzm. Dr. Hakan Kaya", "Dermatolog", "Saç Ekimi", "FUE tekniği ve saç kökü analizi uzmanı."),
    DoctorProfileBooking("Op. Dr. Leyla Aydın", "Medikal Estetik", "Saç Ekimi", "Safir DHI saç ekimi uygulamalarında öncü."),
    DoctorProfileBooking("Dr. Murat Demir", "Saç Cerrahı", "Saç Ekimi", "Doğal saç çizgisi tasarımı üzerine uzmanlaşmış."),

    // Liposuction
    DoctorProfileBooking("Op. Dr. Ayşe Akın", "Plastik Cerrah", "Liposuction", "Vaser liposuction ve vücut şekillendirme."),
    DoctorProfileBooking("Prof. Dr. Canan Batur", "Estetik Cerrah", "Liposuction", "Lazer liposuction tekniklerinde uluslararası sertifika."),
    DoctorProfileBooking("Op. Dr. Fırat Çelik", "Plastik Cerrah", "Liposuction", "Bölgesel yağ alma ve deri sıkılaştırma."),

    // Göz Kapağı Estetiği
    DoctorProfileBooking("Op. Dr. Zeynep Aras", "Göz Cerrahı", "Göz Kapağı Estetiği", "Oküloplastik cerrahi ve göz kapağı düşüklüğü."),
    DoctorProfileBooking("Op. Dr. Metehan Öz", "Plastik Cerrah", "Göz Kapağı Estetiği", "Blefaroplasti operasyonlarında 20 yıllık deneyim."),
    DoctorProfileBooking("Op. Dr. Seda Güneş", "Estetik Cerrah", "Göz Kapağı Estetiği", "Gençleşme ve göz çevresi estetikleri."),

    // Yüz Germe
    DoctorProfileBooking("Prof. Dr. Nilüfer Yalın", "Estetik Cerrah", "Yüz Germe", "İz bırakmayan derin düzlem yüz germe uzmanı."),
    DoctorProfileBooking("Doç. Dr. Barış Vural", "Plastik Cerrah", "Yüz Germe", "Yüz ovalini belirginleştirme ve germe operasyonları."),
    DoctorProfileBooking("Op. Dr. Deniz Tan", "Estetik Cerrah", "Yüz Germe", "Minimal invaziv yüz gençleştirme teknikleri."),

    // Dudak Dolgusu
    DoctorProfileBooking("Uzm. Dr. Burcu Şen", "Medikal Estetik", "Dudak Dolgusu", "Dudak hacimleme ve altın oran uygulamaları."),
    DoctorProfileBooking("Dr. Caner Ege", "Medikal Estetik", "Dudak Dolgusu", "Hyaluronik asit ile doğal dudak tasarımı."),
    DoctorProfileBooking("Uzm. Dr. İrem Gür", "Dermatolog", "Dudak Dolgusu", "Dudak kontürü ve estetik dolgu uzmanı.")
)

val categoryDescriptions = mapOf(
    "Rinoplasti" to "Yüz hatlarınızla uyumlu, doğal ve fonksiyonel nefes alışverişini destekleyen burun estetiği operasyonlarımız, alanında uzman cerrahlarımız tarafından titizlikle planlanmaktadır.",
    "Saç Ekimi" to "En son teknoloji Safir FUE ve DHI tekniklerini kullanarak, dökülme problemi yaşayan danışanlarımıza ömür boyu kalıcı, yoğun ve tamamen doğal görünümlü saç restorasyonu sağlıyoruz.",
    "Liposuction" to "Vücut hatlarını yeniden şekillendirmek amacıyla uygulanan modern liposuction yöntemleriyle, inatçı bölgesel yağlanmalardan kurtulmanızı ve daha fit bir silüete sahip olmanızı hedefliyoruz.",
    "Göz Kapağı Estetiği" to "Blefaroplasti operasyonlarımız ile göz çevresindeki sarkma, torbalanma ve yaşlılık belirtilerini ortadan kaldırarak, bakışlarınıza çok daha dinç, genç ve enerjik bir ifade kazandırıyoruz.",
    "Yüz Germe" to "Zamanın etkilerini minimize eden yüz germe prosedürlerimiz, cildin alt dokularını sıkılaştırarak yüz ovalini netleştirir ve sarkmaları gidererek doğal bir gençleşme etkisi yaratır.",
    "Dudak Dolgusu" to "Yüz simetrinize uygun hacim ve belirginlik kazandıran dudak dolgusu uygulamalarımızda, yüksek kaliteli hyaluronik asit içerikleriyle doğal ve çekici bir görünüm elde edilmektedir."
)