package com.example.healifyapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class BookingFragment : Fragment(R.layout.fragment_booking) {

    private val args: BookingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val doctor = args.selectedDoctor
        val categoryName = getString(getCategoryNameResId(doctor.category))

        view.findViewById<ImageView>(R.id.imgDoctor).setImageResource(
            if (doctor.isFemale) R.drawable.doctor_female else R.drawable.doctor_male
        )
        view.findViewById<TextView>(R.id.bookingDoctorName).text = doctor.name
        view.findViewById<TextView>(R.id.bookingDoctorTitle).text = getString(doctor.titleResId)
        view.findViewById<TextView>(R.id.bookingDoctorCV).text = getString(doctor.cvResId)

        view.findViewById<TextView>(R.id.bookingInstitutions).text =
            "• $categoryName ${getString(R.string.institution_healify_center)}"

        view.findViewById<TextView>(R.id.bookingArticles).text =
            "• ${getString(R.string.article_current_approaches, categoryName)}\n" +
            "• ${getString(R.string.article_international_journal)}"

        view.findViewById<Button>(R.id.btnMakeAppointment).setOnClickListener {
            findNavController().navigate(
                BookingFragmentDirections.actionBookingFragmentToCalendarFragment(doctor)
            )
        }
    }
}
