package com.example.healifyapp

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorProfileBooking(
    val name: String,
    @StringRes val titleResId: Int,
    val category: String,
    @StringRes val cvResId: Int,
    val isFemale: Boolean = false
) : Parcelable

val allDoctors = listOf(
    // ── Rinoplasti ──────────────────────────────────────────────────────────────
    DoctorProfileBooking("Prof. Dr. Ahmet Yılmaz",  R.string.title_kbb_uzmani,      "Rinoplasti",           R.string.cv_ahmet_yilmaz,  isFemale = false),
    DoctorProfileBooking("Doç. Dr. Selin Erdem",    R.string.title_estetik_cerrah,  "Rinoplasti",           R.string.cv_selin_erdem,   isFemale = true),
    DoctorProfileBooking("Op. Dr. Kerem Soylu",     R.string.title_kbb_uzmani,      "Rinoplasti",           R.string.cv_kerem_soylu,   isFemale = false),
    DoctorProfileBooking("Dr. Ece Kılıç",           R.string.title_plastik_cerrah,  "Rinoplasti",           R.string.cv_ece_kilic,     isFemale = true),
    DoctorProfileBooking("Doç. Dr. Berk Ateş",      R.string.title_kbb_uzmani,      "Rinoplasti",           R.string.cv_berk_ates,     isFemale = false),
    DoctorProfileBooking("Op. Dr. Gizem Aksoy",     R.string.title_estetik_cerrah,  "Rinoplasti",           R.string.cv_gizem_aksoy,   isFemale = true),

    // ── Saç Ekimi ───────────────────────────────────────────────────────────────
    DoctorProfileBooking("Uzm. Dr. Hakan Kaya",     R.string.title_dermatolog,      "Saç Ekimi",            R.string.cv_hakan_kaya,    isFemale = false),
    DoctorProfileBooking("Op. Dr. Leyla Aydın",     R.string.title_medikal_estetik, "Saç Ekimi",            R.string.cv_leyla_aydin,   isFemale = true),
    DoctorProfileBooking("Dr. Murat Demir",         R.string.title_sac_cerrahi,     "Saç Ekimi",            R.string.cv_murat_demir,   isFemale = false),
    DoctorProfileBooking("Op. Dr. Serkan Yurt",     R.string.title_dermatolog,      "Saç Ekimi",            R.string.cv_serkan_yurt,   isFemale = false),
    DoctorProfileBooking("Uzm. Dr. Nihan Polat",    R.string.title_sac_cerrahi,     "Saç Ekimi",            R.string.cv_nihan_polat,   isFemale = true),
    DoctorProfileBooking("Dr. Emre Koç",            R.string.title_medikal_estetik, "Saç Ekimi",            R.string.cv_emre_koc,      isFemale = false),

    // ── Liposuction ─────────────────────────────────────────────────────────────
    DoctorProfileBooking("Op. Dr. Ayşe Akın",       R.string.title_plastik_cerrah,  "Liposuction",          R.string.cv_ayse_akin,     isFemale = true),
    DoctorProfileBooking("Prof. Dr. Canan Batur",   R.string.title_estetik_cerrah,  "Liposuction",          R.string.cv_canan_batur,   isFemale = true),
    DoctorProfileBooking("Op. Dr. Fırat Çelik",     R.string.title_plastik_cerrah,  "Liposuction",          R.string.cv_firat_celik,   isFemale = false),
    DoctorProfileBooking("Op. Dr. Tuba Yıldız",     R.string.title_plastik_cerrah,  "Liposuction",          R.string.cv_tuba_yildiz,   isFemale = true),
    DoctorProfileBooking("Doç. Dr. Bora Şahin",     R.string.title_estetik_cerrah,  "Liposuction",          R.string.cv_bora_sahin,    isFemale = false),
    DoctorProfileBooking("Op. Dr. Aslı Çetin",      R.string.title_plastik_cerrah,  "Liposuction",          R.string.cv_asli_cetin,    isFemale = true),

    // ── Göz Kapağı Estetiği ─────────────────────────────────────────────────────
    DoctorProfileBooking("Op. Dr. Zeynep Aras",     R.string.title_goz_cerrahi,     "Göz Kapağı Estetiği",  R.string.cv_zeynep_aras,   isFemale = true),
    DoctorProfileBooking("Op. Dr. Metehan Öz",      R.string.title_plastik_cerrah,  "Göz Kapağı Estetiği",  R.string.cv_metehan_oz,    isFemale = false),
    DoctorProfileBooking("Op. Dr. Seda Güneş",      R.string.title_estetik_cerrah,  "Göz Kapağı Estetiği",  R.string.cv_seda_gunes,    isFemale = true),
    DoctorProfileBooking("Op. Dr. Alper Kurt",      R.string.title_goz_cerrahi,     "Göz Kapağı Estetiği",  R.string.cv_alper_kurt,    isFemale = false),
    DoctorProfileBooking("Uzm. Dr. Yasemin Doğan",  R.string.title_estetik_cerrah,  "Göz Kapağı Estetiği",  R.string.cv_yasemin_dogan, isFemale = true),
    DoctorProfileBooking("Dr. Cenk Arslan",         R.string.title_plastik_cerrah,  "Göz Kapağı Estetiği",  R.string.cv_cenk_arslan,   isFemale = false),

    // ── Yüz Germe ───────────────────────────────────────────────────────────────
    DoctorProfileBooking("Prof. Dr. Nilüfer Yalın", R.string.title_estetik_cerrah,  "Yüz Germe",            R.string.cv_nilufer_yalin, isFemale = true),
    DoctorProfileBooking("Doç. Dr. Barış Vural",    R.string.title_plastik_cerrah,  "Yüz Germe",            R.string.cv_baris_vural,   isFemale = false),
    DoctorProfileBooking("Op. Dr. Deniz Tan",       R.string.title_estetik_cerrah,  "Yüz Germe",            R.string.cv_deniz_tan,     isFemale = false),
    DoctorProfileBooking("Op. Dr. Melis Çelik",     R.string.title_estetik_cerrah,  "Yüz Germe",            R.string.cv_melis_celik,   isFemale = true),
    DoctorProfileBooking("Prof. Dr. Ozan Kara",     R.string.title_plastik_cerrah,  "Yüz Germe",            R.string.cv_ozan_kara,     isFemale = false),
    DoctorProfileBooking("Doç. Dr. Eda Arslan",     R.string.title_estetik_cerrah,  "Yüz Germe",            R.string.cv_eda_arslan,    isFemale = true),

    // ── Dudak Dolgusu ───────────────────────────────────────────────────────────
    DoctorProfileBooking("Uzm. Dr. Burcu Şen",      R.string.title_medikal_estetik, "Dudak Dolgusu",        R.string.cv_burcu_sen,     isFemale = true),
    DoctorProfileBooking("Dr. Caner Ege",           R.string.title_medikal_estetik, "Dudak Dolgusu",        R.string.cv_caner_ege,     isFemale = false),
    DoctorProfileBooking("Uzm. Dr. İrem Gür",       R.string.title_dermatolog,      "Dudak Dolgusu",        R.string.cv_irem_gur,      isFemale = true),
    DoctorProfileBooking("Op. Dr. Sena Yıldırım",   R.string.title_medikal_estetik, "Dudak Dolgusu",        R.string.cv_sena_yildirim, isFemale = true),
    DoctorProfileBooking("Uzm. Dr. Tarık Aydın",    R.string.title_dermatolog,      "Dudak Dolgusu",        R.string.cv_tarik_aydin,   isFemale = false),
    DoctorProfileBooking("Dr. Pınar Karahan",       R.string.title_medikal_estetik, "Dudak Dolgusu",        R.string.cv_pinar_karahan, isFemale = true)
)

@StringRes
fun getCategoryNameResId(category: String): Int = when (category) {
    "Rinoplasti"           -> R.string.cat_rinoplasti_name
    "Saç Ekimi"            -> R.string.cat_sac_ekimi_name
    "Liposuction"          -> R.string.cat_liposuction_name
    "Göz Kapağı Estetiği"  -> R.string.cat_goz_kapagi_name
    "Yüz Germe"            -> R.string.cat_yuz_germe_name
    "Dudak Dolgusu"        -> R.string.cat_dudak_dolgusu_name
    else                   -> R.string.app_name
}

@StringRes
fun getCategorySpecialistsResId(category: String): Int = when (category) {
    "Rinoplasti"           -> R.string.cat_rinoplasti_specialists
    "Saç Ekimi"            -> R.string.cat_sac_ekimi_specialists
    "Liposuction"          -> R.string.cat_liposuction_specialists
    "Göz Kapağı Estetiği"  -> R.string.cat_goz_kapagi_specialists
    "Yüz Germe"            -> R.string.cat_yuz_germe_specialists
    "Dudak Dolgusu"        -> R.string.cat_dudak_dolgusu_specialists
    else                   -> R.string.app_name
}

@StringRes
fun getCategoryAboutResId(category: String): Int = when (category) {
    "Rinoplasti"           -> R.string.cat_rinoplasti_about
    "Saç Ekimi"            -> R.string.cat_sac_ekimi_about
    "Liposuction"          -> R.string.cat_liposuction_about
    "Göz Kapağı Estetiği"  -> R.string.cat_goz_kapagi_about
    "Yüz Germe"            -> R.string.cat_yuz_germe_about
    "Dudak Dolgusu"        -> R.string.cat_dudak_dolgusu_about
    else                   -> R.string.app_name
}

@StringRes
fun getCategoryDescResId(category: String): Int = when (category) {
    "Rinoplasti"           -> R.string.cat_rinoplasti_desc
    "Saç Ekimi"            -> R.string.cat_sac_ekimi_desc
    "Liposuction"          -> R.string.cat_liposuction_desc
    "Göz Kapağı Estetiği"  -> R.string.cat_goz_kapagi_desc
    "Yüz Germe"            -> R.string.cat_yuz_germe_desc
    "Dudak Dolgusu"        -> R.string.cat_dudak_dolgusu_desc
    else                   -> R.string.app_name
}
