package com.example.healifyapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class HotelFragment : Fragment(R.layout.fragment_hotel) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardConfirm = view.findViewById<CardView>(R.id.cardHotelConfirm)
        val tvConfirmed = view.findViewById<TextView>(R.id.tvConfirmedHotel)
        val btnLuxury = view.findViewById<Button>(R.id.btnSelectLuxury)
        val btnStandard = view.findViewById<MaterialButton>(R.id.btnSelectStandard)

        btnLuxury.setOnClickListener {
            BookingState.hotelName = "Healify Suite & Spa"
            BookingState.hotelPricePerNight = 150
            showConfirmation("Healify Suite & Spa", cardConfirm, tvConfirmed)
            btnLuxury.isEnabled = false
            btnStandard.isEnabled = false
        }

        btnStandard.setOnClickListener {
            BookingState.hotelName = "City Inn Istanbul"
            BookingState.hotelPricePerNight = 75
            showConfirmation("City Inn Istanbul", cardConfirm, tvConfirmed)
            btnLuxury.isEnabled = false
            btnStandard.isEnabled = false
        }

        view.findViewById<Button>(R.id.btnGoToSummary).setOnClickListener {
            findNavController().navigate(
                HotelFragmentDirections.actionHotelFragmentToSummaryFragment()
            )
        }
    }

    private fun showConfirmation(hotelName: String, card: CardView, tvName: TextView) {
        tvName.text = hotelName
        card.visibility = View.VISIBLE
        view?.findViewById<Button>(R.id.btnGoToSummary)?.visibility = View.VISIBLE
    }
}
