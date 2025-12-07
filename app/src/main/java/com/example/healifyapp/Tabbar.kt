
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.healifyapp.R
import java.text.SimpleDateFormat
import java.util.*

object Tabbar {

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    fun setup(context: Context, tabbarView: View, isWhiteIcon: Boolean = true) {
        updateWifiIcon(context, tabbarView, isWhiteIcon)
        startClockUpdater(tabbarView)
    }

    // -------------------------------------------------------------
    //  Saat & Tarih Güncelleme
    // -------------------------------------------------------------
    private fun startClockUpdater(tabbarView: View) {
        val timeTextView = tabbarView.findViewById<TextView>(R.id.time_textview)
        val dateTextView = tabbarView.findViewById<TextView>(R.id.date_textview)

        val formatterTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formatterDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        handler?.removeCallbacks(runnable ?: return)
        handler = Handler(Looper.getMainLooper())

        runnable = object : Runnable {
            override fun run() {
                val now = Date()

                timeTextView.text = formatterTime.format(now)
                dateTextView.text = formatterDate.format(now)

                handler?.postDelayed(this, 60_000) // her 1 dk
            }
        }

        handler?.post(runnable!!)
    }

    // -------------------------------------------------------------
    //  Wifi Durumu Güncelleme
    // -------------------------------------------------------------
    private fun updateWifiIcon(context: Context, tabbarView: View, isWhiteIcon: Boolean) {
        val wifiImage = tabbarView.findViewById<ImageView>(R.id.wifi_image)

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val rssi = wifiManager.connectionInfo.rssi

        Log.d("TabbarController", "updateWifiIcon: connected=$isConnected, rssi=$rssi")

        val iconRes = if (!isConnected) {
            R.drawable.no_wifi
        } else when {
            rssi >= -50 -> R.drawable.full_wifi
            rssi >= -70 -> R.drawable.double_wifi
            else -> R.drawable.onlyone_wifi
        }

        wifiImage.setImageResource(iconRes)
    }

    fun stopClock() {
        handler?.removeCallbacks(runnable ?: return)
        runnable = null
        handler = null
    }
}
