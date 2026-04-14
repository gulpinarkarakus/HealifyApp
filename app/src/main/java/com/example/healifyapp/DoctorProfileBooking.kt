package com.example.healifyapp

data class DoctorProfileBooking(
    val name: String,
    val title: String,
    val category: String,
    val cv: String
)

val allDoctors = listOf(
    DoctorProfileBooking("Prof. Dr. Ahmet Yılmaz", "KBB Uzmanı", "Rinoplasti", "25 yıllık tecrübe..."),
    DoctorProfileBooking("Doç. Dr. Selin Erdem", "Estetik Cerrah", "Rinoplasti", "Piezo cerrahisi..."),
    DoctorProfileBooking("Uzm. Dr. Hakan Kaya", "Dermatolog", "Saç Ekimi", "FUE tekniği uzmanı"),
    DoctorProfileBooking("Op. Dr. Ayşe Akın", "Plastik Cerrah", "Liposuction", "Vaser liposuction...")
)

val categoryInfo = mapOf(
    "Rinoplasti" to "Burun estetiği (Rinoplasti) operasyonlarında uzmanlaşmış, yüz hatlarınıza en uygun ve sağlıklı nefes almanızı sağlayacak cerrahlarımız.",
    "Saç Ekimi" to "En son teknoloji (FUE, DHI) yöntemlerle doğal görünümlü saç ekimi gerçekleştiren uzman kadromuz.",
    "Liposuction" to "Vücut şekillendirme ve bölgesel yağ alımı konusunda deneyimli, modern teknikleri kullanan estetik cerrahlarımız.",
    "Dudak Dolgusu" to "Doğal ve estetik bir gülüş tasarımı için medikal estetik alanında uzman hekimlerimiz."
)