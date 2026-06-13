package com.example.healifyapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.doctorName
        setupUI(view, category)
        setupRecyclerView(view, category)
    }

    private fun setupUI(view: View, category: String) {
        view.findViewById<TextView>(R.id.detailTitle).text =
            getString(getCategorySpecialistsResId(category))
        view.findViewById<TextView>(R.id.categorySubtitle).text =
            getString(getCategoryAboutResId(category))
        view.findViewById<TextView>(R.id.categoryDescriptionText).text =
            getString(getCategoryDescResId(category))
    }

    private fun setupRecyclerView(view: View, category: String) {
        val filtered = allDoctors.filter { it.category == category }
        view.findViewById<RecyclerView>(R.id.doctorRecyclerView).adapter =
            DoctorAdapter(filtered) { doctor -> onDoctorSelected(doctor) }
    }

    private fun onDoctorSelected(doctor: DoctorProfileBooking) {
        val action = DetailFragmentDirections.actionDetailFragmentToBookingFragment(doctor)
        findNavController().navigate(action)
    }
}
