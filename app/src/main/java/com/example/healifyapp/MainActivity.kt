package com.example.healifyapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.healifyapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.navigation.findNavController

private val handler = Handler(Looper.getMainLooper())
private lateinit var updateTimeTask: Runnable

private fun createUpdateTask(timeTextView: TextView, dateTextView: TextView): Runnable {
    return object : Runnable {
        override fun run() {
            val calendar = Calendar.getInstance()
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            timeTextView.text = timeFormat.format(calendar.time)
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            dateTextView.text = dateFormat.format(calendar.time)
            handler.postDelayed(this, 1000)
        }
    }
}

fun startClockUpdates(timeTextView: TextView, dateTextView: TextView) {
    if (!::updateTimeTask.isInitialized || !handler.hasCallbacks(updateTimeTask)) {
        updateTimeTask = createUpdateTask(timeTextView, dateTextView)
        handler.post(updateTimeTask)
    }
}

fun stopClockUpdates() {
    if (::updateTimeTask.isInitialized) {
        handler.removeCallbacks(updateTimeTask)
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) { super.attachBaseContext(null); return }
        val prefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("app_language", "en") ?: "en"
        val locale = java.util.Locale(lang)
        java.util.Locale.setDefault(locale)
        val config = android.content.res.Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            val navController = findNavController(R.id.nav_host_fragment)
            when (navController.currentDestination?.id) {
                R.id.loginFragment -> navController.navigate(R.id.languageFragment)
                else -> onBackPressedDispatcher.onBackPressed()
            }
        }

        startClockUpdates(binding.timeTextview, binding.dateTextview)
    }

    override fun onResume() {
        super.onResume()
        startClockUpdates(binding.timeTextview, binding.dateTextview)
    }

    override fun onPause() {
        super.onPause()
        stopClockUpdates()
    }
}
