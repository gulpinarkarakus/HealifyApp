package com.example.healifyapp

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class HotelFragment : Fragment(R.layout.fragment_hotel) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardLuxury = view.findViewById<CardView>(R.id.cardLuxuryHotel)
        val cardStandard = view.findViewById<CardView>(R.id.cardStandardHotel)
        val cardConfirm = view.findViewById<CardView>(R.id.cardHotelConfirm)
        val tvConfirmed = view.findViewById<TextView>(R.id.tvConfirmedHotel)
        val btnLuxury = view.findViewById<Button>(R.id.btnSelectLuxury)
        val btnStandard = view.findViewById<MaterialButton>(R.id.btnSelectStandard)
        val btnCompare = view.findViewById<MaterialButton>(R.id.btnCompareHotels)
        val btnGoSummary = view.findViewById<Button>(R.id.btnGoToSummary)

        btnLuxury.setOnClickListener {
            BookingState.hotelName = "Healify Suite & Spa"
            BookingState.hotelPricePerNight = 150
            markSelected(
                selectedCard = cardLuxury,
                otherCard = cardStandard,
                selectedBtn = btnLuxury,
                otherBtn = btnStandard,
                isOtherOutlined = true
            )
            tvConfirmed.text = "Healify Suite & Spa"
            cardConfirm.visibility = View.VISIBLE
            btnGoSummary.visibility = View.VISIBLE
        }

        btnStandard.setOnClickListener {
            BookingState.hotelName = "City Inn Istanbul"
            BookingState.hotelPricePerNight = 75
            markSelected(
                selectedCard = cardStandard,
                otherCard = cardLuxury,
                selectedBtn = btnStandard,
                otherBtn = btnLuxury,
                isOtherOutlined = false
            )
            tvConfirmed.text = "City Inn Istanbul"
            cardConfirm.visibility = View.VISIBLE
            btnGoSummary.visibility = View.VISIBLE
        }

        btnCompare.setOnClickListener { showCompareDialog() }

        btnGoSummary.setOnClickListener {
            findNavController().navigate(
                HotelFragmentDirections.actionHotelFragmentToSummaryFragment()
            )
        }
    }

    private fun showCompareDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_hotel_compare, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        dialogView.findViewById<Button>(R.id.btnCloseCompare).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun markSelected(
        selectedCard: CardView,
        otherCard: CardView,
        selectedBtn: Button,
        otherBtn: Button,
        isOtherOutlined: Boolean
    ) {
        // Reset "other" card and button to original state so user can switch
        otherCard.setCardBackgroundColor(0xFFFFFFFF.toInt())
        otherCard.cardElevation = dp(2).toFloat()
        otherBtn.text = getString(R.string.hotel_select_btn)
        otherBtn.isEnabled = true
        if (isOtherOutlined) {
            (otherBtn as? MaterialButton)?.apply {
                strokeColor = ColorStateList.valueOf(0xFF3A7BD5.toInt())
                setTextColor(0xFF3A7BD5.toInt())
                backgroundTintList = ColorStateList.valueOf(android.graphics.Color.TRANSPARENT)
            }
        } else {
            otherBtn.backgroundTintList = ColorStateList.valueOf(0xFF3A7BD5.toInt())
            otherBtn.setTextColor(android.graphics.Color.WHITE)
        }

        // Apply selected state
        selectedCard.setCardBackgroundColor(0xFFEEF4FF.toInt())
        selectedCard.cardElevation = dp(8).toFloat()
        selectedBtn.text = getString(R.string.hotel_selected_btn)
        selectedBtn.backgroundTintList = ColorStateList.valueOf(0xFF2E7D32.toInt())
        selectedBtn.setTextColor(android.graphics.Color.WHITE)
        selectedBtn.isEnabled = false
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
}
