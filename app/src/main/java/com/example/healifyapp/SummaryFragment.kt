package com.example.healifyapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SummaryFragment : Fragment(R.layout.fragment_summary) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = BookingState
        val dateFmt = SimpleDateFormat("dd MMMM yyyy", Locale("tr"))
        val appointment = Calendar.getInstance().apply { timeInMillis = s.appointmentDateMillis }
        val arrival = Calendar.getInstance().apply { timeInMillis = s.arrivalMillis }
        val returnCal = Calendar.getInstance().apply { timeInMillis = s.returnMillis }

        fun setRow(rowId: Int, label: String, value: String) {
            val row = view.findViewById<View>(rowId)
            row.findViewById<TextView>(R.id.tvLabel).text = label
            row.findViewById<TextView>(R.id.tvValue).text = value
        }

        setRow(R.id.rowDoctor, "Doktor", s.doctorName)
        setRow(R.id.rowCategory, "İşlem", s.category)
        setRow(R.id.rowDate, "Tarih", dateFmt.format(appointment.time))
        setRow(R.id.rowTime, "Saat", s.appointmentTime)

        setRow(R.id.rowHotel, "Otel", s.hotelName)
        setRow(R.id.rowCheckin, "Giriş", dateFmt.format(arrival.time))
        setRow(R.id.rowCheckout, "Çıkış", dateFmt.format(returnCal.time))
        setRow(R.id.rowStay, "Süre", "${s.stayDays} gece")

        setRow(R.id.rowDriver, "Sürücü", s.driverName)
        setRow(R.id.rowVehicle, "Araç", s.vehicle)
        setRow(R.id.rowPlate, "Plaka", s.plate)
        view.findViewById<View>(R.id.rowPlate).findViewById<TextView>(R.id.tvValue).apply {
            setTextColor(0xFF3A7BD5.toInt())
            textSize = 15f
        }
        setRow(R.id.rowLanding, "İniş saati", s.landingTime)
        setRow(R.id.rowPickup, "Alış saati", s.pickupTime)

        val hotelTotal = s.hotelPricePerNight * s.stayDays
        val deposit = 500
        val total = hotelTotal + deposit
        setRow(R.id.rowHotelCost, "Konaklama", "€${s.hotelPricePerNight} × ${s.stayDays} gece = €$hotelTotal")
        setRow(R.id.rowTransferCost, "Transfer", "Ücretsiz")
        setRow(R.id.rowDeposit, "İşlem depozitosu", "€$deposit")
        view.findViewById<TextView>(R.id.tvTotalAmount).text = "€$total"

        val userPreferences = UserPreferences(requireContext())

        view.findViewById<MaterialButton>(R.id.btnSendEmail).setOnClickListener {
            lifecycleScope.launch {
                val email = userPreferences.getEmail.first()
                val name = userPreferences.getName.first()
                sendConfirmationEmail(email, name, s, dateFmt, arrival, returnCal, hotelTotal, deposit, total)
            }
        }

        view.findViewById<Button>(R.id.btnGoToPayment).setOnClickListener {
            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToPaymentFragment()
            )
        }
    }

    private fun sendConfirmationEmail(
        email: String, name: String, s: BookingState,
        dateFmt: SimpleDateFormat,
        arrival: java.util.Calendar, returnCal: java.util.Calendar,
        hotelTotal: Int, deposit: Int, total: Int
    ) {
        val appointmentCal = java.util.Calendar.getInstance().apply { timeInMillis = s.appointmentDateMillis }
        val body = """
Sayın ${name.ifEmpty { "Kullanıcı" }},

Healify aracılığıyla yaptığınız rezervasyonun detayları aşağıda yer almaktadır.

━━━━━━━━━━━━━━━━━━━━━━━━
🏥 İŞLEM VE RANDEVU
━━━━━━━━━━━━━━━━━━━━━━━━
Doktor       : ${s.doctorName}
İşlem        : ${s.category}
Randevu      : ${dateFmt.format(appointmentCal.time)} - ${s.appointmentTime}

━━━━━━━━━━━━━━━━━━━━━━━━
🏨 KONAKLAMA
━━━━━━━━━━━━━━━━━━━━━━━━
Otel         : ${s.hotelName}
Giriş        : ${dateFmt.format(arrival.time)}
Çıkış        : ${dateFmt.format(returnCal.time)}
Süre         : ${s.stayDays} gece × €${s.hotelPricePerNight} = €$hotelTotal

━━━━━━━━━━━━━━━━━━━━━━━━
🚗 TRANSFER
━━━━━━━━━━━━━━━━━━━━━━━━
Sürücü       : ${s.driverName}
Araç         : ${s.vehicle}
Plaka        : ${s.plate}
İniş saati   : ${s.landingTime}
Tahmini alış : ${s.pickupTime}
Güzergah     : İstanbul Havalimanı → Healify Estetik Merkezi

━━━━━━━━━━━━━━━━━━━━━━━━
💳 ÖDEME ÖZETİ
━━━━━━━━━━━━━━━━━━━━━━━━
Konaklama         : €$hotelTotal
İşlem depozitosu  : €$deposit
Transfer          : Ücretsiz
──────────────────────────
TOPLAM            : €$total

━━━━━━━━━━━━━━━━━━━━━━━━

Sağlıklı günler dileriz.
Healify – Your Health Journey
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "Healify – Rezervasyon Onayınız ✓")
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            startActivity(intent)
        } catch (_: Exception) {
            android.widget.Toast.makeText(requireContext(),
                "Mail uygulaması bulunamadı. Email: $email", android.widget.Toast.LENGTH_LONG).show()
        }
    }
}
