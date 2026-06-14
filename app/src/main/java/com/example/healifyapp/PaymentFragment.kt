package com.example.healifyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = BookingState
        val total = s.hotelPricePerNight * s.stayDays + 500
        view.findViewById<TextView>(R.id.tvPaymentAmount).text = "€$total"

        val etCard   = view.findViewById<TextInputEditText>(R.id.etCardNumber)
        val etHolder = view.findViewById<TextInputEditText>(R.id.etCardHolder)
        val etExpiry = view.findViewById<TextInputEditText>(R.id.etExpiry)
        val etCvv    = view.findViewById<TextInputEditText>(R.id.etCvv)
        val btnPay   = view.findViewById<Button>(R.id.btnPay)
        val layoutForm    = view.findViewById<LinearLayout>(R.id.layoutPaymentForm)
        val layoutSuccess = view.findViewById<LinearLayout>(R.id.layoutPaySuccess)

        etCard.addTextChangedListener(object : TextWatcher {
            private var formatting = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (formatting) return
                formatting = true
                val digits = s.toString().filter { it.isDigit() }.take(16)
                val formatted = digits.chunked(4).joinToString("  ")
                s?.replace(0, s.length, formatted)
                formatting = false
            }
        })

        etExpiry.addTextChangedListener(object : TextWatcher {
            private var formatting = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (formatting) return
                formatting = true
                val digits = s.toString().filter { it.isDigit() }.take(4)
                val formatted = if (digits.length > 2) "${digits.substring(0, 2)}/${digits.substring(2)}" else digits
                s?.replace(0, s.length, formatted)
                formatting = false
            }
        })

        btnPay.setOnClickListener {
            val cardNum = etCard.text.toString().filter { it.isDigit() }
            val holder  = etHolder.text.toString().trim()
            val expiry  = etExpiry.text.toString().trim()
            val cvv     = etCvv.text.toString().trim()

            when {
                cardNum.isEmpty() -> Toast.makeText(requireContext(), getString(R.string.payment_err_card),   Toast.LENGTH_SHORT).show()
                holder.isEmpty()  -> Toast.makeText(requireContext(), getString(R.string.payment_err_holder), Toast.LENGTH_SHORT).show()
                expiry.isEmpty()  -> Toast.makeText(requireContext(), getString(R.string.payment_err_expiry), Toast.LENGTH_SHORT).show()
                cvv.isEmpty()     -> Toast.makeText(requireContext(), getString(R.string.payment_err_cvv),    Toast.LENGTH_SHORT).show()
                else -> {
                    showSuccess(view, layoutForm, layoutSuccess, total)
                }
            }
        }
    }

    private fun showSuccess(
        view: View,
        layoutForm: LinearLayout,
        layoutSuccess: LinearLayout,
        total: Int
    ) {
        val s = BookingState
        layoutForm.visibility = View.GONE
        layoutSuccess.visibility = View.VISIBLE

        // Random 6-char booking reference
        val ref = (1..6).map { "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".random() }.joinToString("")
        view.findViewById<TextView>(R.id.tvBookingRef).text = "HLF-$ref"

        // Doctor
        view.findViewById<TextView>(R.id.tvSuccessDoctor).text = s.doctorName

        // Appointment date + time
        val dateFmt = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val apptCal = Calendar.getInstance().apply { timeInMillis = s.appointmentDateMillis }
        view.findViewById<TextView>(R.id.tvSuccessDate).text =
            "${dateFmt.format(apptCal.time)}  •  ${s.appointmentTime}"

        // Hotel
        view.findViewById<TextView>(R.id.tvSuccessHotel).text = s.hotelName

        // Amount
        view.findViewById<TextView>(R.id.tvSuccessAmount).text = "€$total"

        // Home button
        view.findViewById<Button>(R.id.btnGoHome).setOnClickListener {
            findNavController().navigate(
                PaymentFragmentDirections.actionPaymentFragmentToHomeFragment()
            )
        }
    }
}
