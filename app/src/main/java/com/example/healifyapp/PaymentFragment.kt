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
import com.google.android.material.textfield.TextInputEditText

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = BookingState
        val total = s.hotelPricePerNight * s.stayDays + 500
        view.findViewById<TextView>(R.id.tvPaymentAmount).text = "€$total"

        val etCard = view.findViewById<TextInputEditText>(R.id.etCardNumber)
        val etHolder = view.findViewById<TextInputEditText>(R.id.etCardHolder)
        val etExpiry = view.findViewById<TextInputEditText>(R.id.etExpiry)
        val etCvv = view.findViewById<TextInputEditText>(R.id.etCvv)
        val btnPay = view.findViewById<Button>(R.id.btnPay)
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
            val holder = etHolder.text.toString().trim()
            val expiry = etExpiry.text.toString().trim()
            val cvv = etCvv.text.toString().trim()

            when {
                cardNum.length < 16 -> Toast.makeText(requireContext(), "Geçerli bir kart numarası girin", Toast.LENGTH_SHORT).show()
                holder.isEmpty() -> Toast.makeText(requireContext(), "Kart sahibinin adını girin", Toast.LENGTH_SHORT).show()
                expiry.length < 5 -> Toast.makeText(requireContext(), "Son kullanma tarihini girin (MM/YY)", Toast.LENGTH_SHORT).show()
                cvv.length < 3 -> Toast.makeText(requireContext(), "CVV kodunu girin", Toast.LENGTH_SHORT).show()
                else -> {
                    btnPay.isEnabled = false
                    btnPay.alpha = 0.4f
                    layoutSuccess.visibility = View.VISIBLE
                }
            }
        }
    }
}
