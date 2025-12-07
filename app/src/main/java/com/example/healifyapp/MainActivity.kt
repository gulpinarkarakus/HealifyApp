package com.example.healifyapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healifyapp.databinding.ActivityMainBinding // Lütfen kendi Binding sınıf adınızla değiştirin!
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.navigation.findNavController
import androidx.navigation.NavController
import com.example.healifyapp.R


// Handler nesnesini ve Görev değişkenini sınıf dışında global olarak tanımlıyoruz
private val handler = Handler(Looper.getMainLooper())
private lateinit var updateTimeTask: Runnable


 fun updateWifiIcon(context: Context, wifiImageView: ImageView) {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: run {
        wifiImageView.setImageResource(R.drawable.no_wifi)
        return
    }

    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: run {
        wifiImageView.setImageResource(R.drawable.no_wifi)
        return
    }

    // WiFi bağlantısı yoksa
    if (!capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
        wifiImageView.setImageResource(R.drawable.no_wifi)
        return
    }

    // Gerçek sinyal gücü (dBm)
    val signalStrength = capabilities.signalStrength

    if (signalStrength == NetworkCapabilities.SIGNAL_STRENGTH_UNSPECIFIED) {
        wifiImageView.setImageResource(R.drawable.no_wifi)
        return
    }

    // dBm değerine göre ikon seçimi
    when {
        signalStrength >= -50 -> wifiImageView.setImageResource(R.drawable.full_wifi)       // Çok iyi
        signalStrength >= -65 -> wifiImageView.setImageResource(R.drawable.double_wifi)     // Orta
        signalStrength >= -80 -> wifiImageView.setImageResource(R.drawable.onlyone_wifi)    // Zayıf
        else -> wifiImageView.setImageResource(R.drawable.no_wifi)                          // Çok zayıf
    }
}


/**
 * Saati ve tarihi her saniye güncelleyen Runnable görevini oluşturur.
 */
private fun createUpdateTask(timeTextView: TextView, dateTextView: TextView): Runnable {
    return object : Runnable {
        override fun run() {
            val calendar = Calendar.getInstance()

            // Saat formatı (HH:mm)
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            timeTextView.text = timeFormat.format(calendar.time)

            // Tarih formatı (dd.MM.yyyy)
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            dateTextView.text = dateFormat.format(calendar.time)

            // 1 saniye (1000ms) sonra tekrar çalıştır
            handler.postDelayed(this, 1000)
        }
    }
}

/**
 * Saat/Tarih güncelleme döngüsünü başlatır.
 */
fun startClockUpdates(timeTextView: TextView, dateTextView: TextView) {
    // Eğer görev zaten başlatılmamışsa veya durdurulmuşsa yeniden oluştur ve başlat.
    if (!::updateTimeTask.isInitialized || !handler.hasCallbacks(updateTimeTask)) {
        updateTimeTask = createUpdateTask(timeTextView, dateTextView)
        handler.post(updateTimeTask)
    }
}

/**
 * Saat/Tarih güncelleme döngüsünü durdurur.
 */
fun stopClockUpdates() {
    if (::updateTimeTask.isInitialized) {
        handler.removeCallbacks(updateTimeTask)
    }
}


// ----------------------------------------------------------------------------------

class MainActivity : AppCompatActivity() {

    // View Binding nesnesini tanımlayın. XML dosyanızın adı activity_main.xml olduğu varsayılmıştır.
    private lateinit var binding: ActivityMainBinding

    override fun attachBaseContext(newBase: Context?) {
        // Kaydedilen dili al
        val prefs = newBase?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs?.getString("app_language", "en") ?: "en"

        // Locale ayarla
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)

        val config = newBase?.resources?.configuration
        config?.setLocale(locale)

        val context = newBase?.createConfigurationContext(config!!)
        super.attachBaseContext(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View Binding'i başlatın
        // ActivityMainBinding.inflate(layoutInflater) kodunu kullanabilmek için
        // build.gradle (Module: app) dosyanızda viewBinding'in etkinleştirildiğinden emin olun:
        // buildFeatures { viewBinding true }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EdgeToEdge ayarları için rootView (binding.root) kullanılır
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // --- Tabbar Bileşenlerine Erişim ve Ayarlama ---

        binding.backButton.setOnClickListener {
            val navController = findNavController(R.id.nav_host_fragment)
            val currentDestination = navController.currentDestination?.id

            when (currentDestination) {

                // Login Fragment’ta → LanguageFragment'a dön
                R.id.loginFragment -> {
                    navController.navigate(R.id.languageFragment)
                }

                // Language Fragment’ta → IntroFragment'a dön
                R.id.languageFragment -> {
                    navController.navigate(R.id.introFragment)
                }

                // Diğer fragment'lar için normal davranış
                else -> {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }


        val timeTextView = binding.timeTextview
        val dateTextView = binding.dateTextview
        val wifiImageView = binding.wifiImage

        // 1. Wi-Fi Durumunu İlk Kez Ayarla (onResume'da da tekrar kontrol edilecek)
        updateWifiIcon(this, wifiImageView)

        // 2. Saat/Tarih Güncellemesini Başlat (onResume'da da tekrar başlatılacak)
        startClockUpdates(timeTextView, dateTextView)


    }
    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.introFragment -> {
                    binding.backButton.visibility = View.GONE
                    binding.titleHealify.visibility = View.GONE
                    binding.titleHealify2.visibility = View.VISIBLE
                }
                else -> {
                    binding.backButton.visibility = View.VISIBLE
                    binding.titleHealify2.visibility = View.GONE
                    binding.titleHealify.visibility = View.VISIBLE
                }
            }
        }
    }



    /**
     * Activity tekrar görünür olduğunda (ön plana geldiğinde) çağrılır.
     * Bu, Handler görevlerinin tekrar başlatılması için en iyi yerdir.
     */
    override fun onResume() {
        super.onResume()
        // Saat güncellemesini tekrar başlat
        startClockUpdates(binding.timeTextview, binding.dateTextview)
        // Wi-Fi durumu değişmiş olabileceği için tekrar kontrol et
        updateWifiIcon(this, binding.wifiImage)
    }

    /**
     * Activity görünmez olduğunda (arka plana gittiğinde veya üstüne başka bir Activity geldiğinde) çağrılır.
     * Bu, sürekli çalışan görevleri durdurarak pil tüketimini azaltmak için en iyi yerdir.
     */
    override fun onPause() {
        super.onPause()
        // Sürekli çalışan Handler görevini durdur
        stopClockUpdates()
    }
}