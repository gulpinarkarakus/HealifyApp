package com.example.healifyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorAdapter(
    private val doctors: List<DoctorProfileBooking>,
    private val onDoctorClick: (DoctorProfileBooking) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.doctorNameText)
        val titleText: TextView = view.findViewById(R.id.doctorTitleText)
        val imgDoctor: ImageView = view.findViewById(R.id.imgDoctorList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_item, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.nameText.text = doctor.name
        holder.titleText.text = holder.itemView.context.getString(doctor.titleResId)
        holder.imgDoctor.setImageResource(
            if (doctor.isFemale) R.drawable.doctor_female else R.drawable.doctor_male
        )
        holder.itemView.setOnClickListener { onDoctorClick(doctor) }
    }

    override fun getItemCount() = doctors.size
}
