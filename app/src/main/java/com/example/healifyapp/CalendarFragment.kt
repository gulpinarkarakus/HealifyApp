package com.example.healifyapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private val args: CalendarFragmentArgs by navArgs()

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doctor = args.selectedDoctor

        val tvDoctorName = view.findViewById<TextView>(R.id.tvDoctorName)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        val tvSelectedDate = view.findViewById<TextView>(R.id.tvSelectedDate)
        val btnSelectTime = view.findViewById<Button>(R.id.btnSelectTime)
        val tvSelectedTime = view.findViewById<TextView>(R.id.tvSelectedTime)
        val btnCreateAppointment = view.findViewById<Button>(R.id.btnCreateAppointment)

        tvDoctorName.text = doctor.name

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            tvSelectedDate.text = "Seçilen Tarih: $selectedDate"
        }

        btnSelectTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    selectedTime = "$selectedHour:$selectedMinute"
                    tvSelectedTime.text = "Seçilen Saat: $selectedTime"
                },
                hour,
                minute,
                true
            )

            timePicker.show()
        }

        btnCreateAppointment.setOnClickListener {

            if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
                tvSelectedDate.text = "Lütfen tarih ve saat seçin!"
                return@setOnClickListener
            }

            tvSelectedDate.text = "Randevu Alındı "
            tvSelectedTime.text = "$selectedDate - $selectedTime"
        }
    }
}