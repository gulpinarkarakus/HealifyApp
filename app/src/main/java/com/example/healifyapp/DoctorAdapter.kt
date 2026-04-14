package com.example.healifyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorAdapter(private val doctorList: List<DoctorProfileBooking>) :
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.doctorNameText)
        val title: TextView = view.findViewById(R.id.doctorTitleText)
        val cv: TextView = view.findViewById(R.id.doctorCvText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.doctor_item, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]
        holder.name.text = doctor.name
        holder.title.text = doctor.title
        holder.cv.text = doctor.cv
    }

    override fun getItemCount() = doctorList.size
}