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

        val selectedCategory = args.doctorName

        setupUI(view, selectedCategory)

        setupRecyclerView(view, selectedCategory)
    }

    private fun setupUI(view: View, category: String) {
        val title = view.findViewById<TextView>(R.id.detailTitle)
        val subtitle = view.findViewById<TextView>(R.id.categorySubtitle)
        val description = view.findViewById<TextView>(R.id.categoryDescriptionText)

        title.text = "$category Uzmanları"
        subtitle.text = "$category Hakkında"

        description.text = categoryDescriptions[category] ?: "Uzman doktorlarımız listelenmiştir."
    }

    private fun setupRecyclerView(view: View, category: String) {
        val filteredDoctors = allDoctors.filter { it.category == category }

        val recyclerView = view.findViewById<RecyclerView>(R.id.doctorRecyclerView)

        recyclerView.adapter = DoctorAdapter(filteredDoctors) { clickedDoctor ->
            onDoctorSelected(clickedDoctor)
        }
    }

    private fun onDoctorSelected(doctor: DoctorProfileBooking) {
        val action = DetailFragmentDirections.actionDetailFragmentToBookingFragment(doctor)
        findNavController().navigate(action)
    }
}