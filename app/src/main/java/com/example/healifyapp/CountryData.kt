package com.example.healifyapp

data class CountryOption(
    val flag: String,
    val name: String,
    val city: String,
    val iataCode: String
)

val popularCountries = listOf(
    CountryOption("🇩🇪", "Almanya", "Frankfurt", "FRA"),
    CountryOption("🇬🇧", "İngiltere", "Londra", "LHR"),
    CountryOption("🇫🇷", "Fransa", "Paris", "CDG"),
    CountryOption("🇳🇱", "Hollanda", "Amsterdam", "AMS"),
    CountryOption("🇧🇪", "Belçika", "Brüksel", "BRU"),
    CountryOption("🇨🇭", "İsviçre", "Zürih", "ZRH"),
    CountryOption("🇦🇹", "Avusturya", "Viyana", "VIE"),
    CountryOption("🇸🇪", "İsveç", "Stockholm", "ARN"),
    CountryOption("🇳🇴", "Norveç", "Oslo", "OSL"),
    CountryOption("🇩🇰", "Danimarka", "Kopenhag", "CPH"),
    CountryOption("🇺🇸", "Amerika", "New York", "JFK"),
    CountryOption("🇨🇦", "Kanada", "Toronto", "YYZ"),
    CountryOption("🇦🇪", "BAE", "Dubai", "DXB"),
    CountryOption("🇸🇦", "Suudi Arabistan", "Riyad", "RUH"),
    CountryOption("🇦🇺", "Avustralya", "Sidney", "SYD")
)

val procedureStayDays = mapOf(
    "Rinoplasti"           to Pair(7, 10),
    "Saç Ekimi"            to Pair(3,  5),
    "Liposuction"          to Pair(5,  7),
    "Göz Kapağı Estetiği"  to Pair(3,  5),
    "Yüz Germe"            to Pair(7, 10),
    "Dudak Dolgusu"        to Pair(1,  2)
)
