package com.example.healifyapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class BookingFragment : Fragment(R.layout.fragment_booking) {

    private val args: BookingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doctor = args.selectedDoctor

        view.findViewById<TextView>(R.id.bookingDoctorName).text = doctor.name
        view.findViewById<TextView>(R.id.bookingDoctorTitle).text = doctor.title
        view.findViewById<TextView>(R.id.bookingDoctorCV).text = doctor.cv


        view.findViewById<TextView>(R.id.bookingInstitutions).text = "• ${doctor.category} Araştırma Hastanesi\n• Healify Estetik Merkezi"
        view.findViewById<TextView>(R.id.bookingArticles).text = "• ${doctor.category} Alanında Güncel Yaklaşımlar\n• Uluslararası Cerrahi Dergisi Yayınları"
    }
}