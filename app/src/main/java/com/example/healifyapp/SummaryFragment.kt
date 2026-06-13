package com.example.healifyapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SummaryFragment : Fragment(R.layout.fragment_summary) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = BookingState
        val dateFmt = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val appointment = Calendar.getInstance().apply { timeInMillis = s.appointmentDateMillis }
        val arrival = Calendar.getInstance().apply { timeInMillis = s.arrivalMillis }
        val returnCal = Calendar.getInstance().apply { timeInMillis = s.returnMillis }

        fun setRow(rowId: Int, label: String, value: String) {
            val row = view.findViewById<View>(rowId)
            row.findViewById<TextView>(R.id.tvLabel).text = label
            row.findViewById<TextView>(R.id.tvValue).text = value
        }

        setRow(R.id.rowDoctor,   getString(R.string.summary_lbl_doctor),    s.doctorName)
        setRow(R.id.rowCategory, getString(R.string.summary_lbl_procedure),
            getString(getCategoryNameResId(s.category)))
        setRow(R.id.rowDate,     getString(R.string.summary_lbl_date),      dateFmt.format(appointment.time))
        setRow(R.id.rowTime,     getString(R.string.summary_lbl_time),      s.appointmentTime)

        setRow(R.id.rowHotel,    getString(R.string.summary_lbl_hotel),     s.hotelName)
        setRow(R.id.rowCheckin,  getString(R.string.summary_lbl_checkin),   dateFmt.format(arrival.time))
        setRow(R.id.rowCheckout, getString(R.string.summary_lbl_checkout),  dateFmt.format(returnCal.time))
        setRow(R.id.rowStay,     getString(R.string.summary_lbl_stay),
            getString(R.string.summary_stay_nights, s.stayDays))

        setRow(R.id.rowDriver,   getString(R.string.summary_lbl_driver),   s.driverName)
        setRow(R.id.rowVehicle,  getString(R.string.summary_lbl_vehicle),  s.vehicle)
        setRow(R.id.rowPlate,    getString(R.string.summary_lbl_plate),    s.plate)
        view.findViewById<View>(R.id.rowPlate).findViewById<TextView>(R.id.tvValue).apply {
            setTextColor(0xFF3A7BD5.toInt())
            textSize = 15f
        }
        setRow(R.id.rowLanding,  getString(R.string.summary_lbl_landing),  s.landingTime)
        setRow(R.id.rowPickup,   getString(R.string.summary_lbl_pickup),   s.pickupTime)

        val hotelTotal = s.hotelPricePerNight * s.stayDays
        val deposit = 500
        val total = hotelTotal + deposit
        setRow(R.id.rowHotelCost,    getString(R.string.summary_lbl_hotel_cost),
            getString(R.string.summary_hotel_cost_format, s.hotelPricePerNight, s.stayDays, hotelTotal))
        setRow(R.id.rowTransferCost, getString(R.string.summary_lbl_transfer_cost),
            getString(R.string.summary_transfer_free))
        setRow(R.id.rowDeposit,      getString(R.string.summary_lbl_deposit), "€$deposit")
        view.findViewById<TextView>(R.id.tvTotalAmount).text = "€$total"

        // Update buttons
        view.findViewById<MaterialButton>(R.id.btnUpdateAppointment).setOnClickListener {
            val doctor = s.selectedDoctor ?: return@setOnClickListener
            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToCalendarFragment(doctor)
            )
        }

        view.findViewById<MaterialButton>(R.id.btnUpdateHotel).setOnClickListener {
            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToHotelFragment()
            )
        }

        view.findViewById<MaterialButton>(R.id.btnUpdateTransfer).setOnClickListener {
            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToTransferFragment(
                    arrivalMillis = s.arrivalMillis,
                    doctorName = s.doctorName,
                    category = s.category
                )
            )
        }

        view.findViewById<Button>(R.id.btnGoToPayment).setOnClickListener {
            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToPaymentFragment()
            )
        }
    }
}
