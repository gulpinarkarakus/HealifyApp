package com.example.healifyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorAdapter(
    private val doctors: List<DoctorProfileBooking>,
    private val onDoctorClick: (DoctorProfileBooking) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.doctorNameText)
        val titleText: TextView = view.findViewById(R.id.doctorTitleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_item, parent, false) // Kendi layout adını yaz
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.nameText.text = doctor.name
        holder.titleText.text = doctor.title

        holder.itemView.setOnClickListener {
            onDoctorClick(doctor)
        }
    }

    override fun getItemCount() = doctors.size
}