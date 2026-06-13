package com.example.healifyapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TransferFragment : Fragment(R.layout.fragment_transfer) {

    private val args: TransferFragmentArgs by navArgs()

    private val drivers = listOf(
        Triple("Mehmet K.", "Mercedes E-Serisi", "34 KLM 891"),
        Triple("Ali Y.", "Toyota Camry", "34 ABC 456"),
        Triple("Hasan Ö.", "BMW 5 Serisi", "34 XYZ 234"),
        Triple("Murat D.", "Volkswagen Passat", "34 DEF 567")
    )

    private var selectedHour = -1
    private var selectedMinute = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrivalCal = Calendar.getInstance().apply { timeInMillis = args.arrivalMillis }
        val displayFmt = SimpleDateFormat("dd MMMM yyyy, EEEE", Locale.getDefault())

        view.findViewById<TextView>(R.id.tvTransferDoctorName).text = args.doctorName
        view.findViewById<TextView>(R.id.tvTransferArrivalDate).text = displayFmt.format(arrivalCal.time)

        val tvHour = view.findViewById<TextView>(R.id.tvHourDisplay)
        val tvMinute = view.findViewById<TextView>(R.id.tvMinuteDisplay)
        val btnPickTime = view.findViewById<Button>(R.id.btnPickTime)
        val btnRequest = view.findViewById<Button>(R.id.btnRequestTransfer)
        val cardConfirm = view.findViewById<CardView>(R.id.cardTransferConfirm)

        btnPickTime.setOnClickListener {
            val now = Calendar.getInstance()
            val picker = TimePickerBottomSheet.newInstance(
                hour = if (selectedHour >= 0) selectedHour else now.get(Calendar.HOUR_OF_DAY),
                minute = if (selectedMinute >= 0) selectedMinute else now.get(Calendar.MINUTE)
            )
            picker.onTimeSelected = { hour, minute ->
                selectedHour = hour
                selectedMinute = minute
                tvHour.text = String.format("%02d", hour)
                tvMinute.text = String.format("%02d", minute)
                tvHour.setTextColor(0xFF3A7BD5.toInt())
                tvMinute.setTextColor(0xFF3A7BD5.toInt())
            }
            picker.show(childFragmentManager, "time_picker")
        }

        btnRequest.setOnClickListener {
            if (selectedHour < 0) {
                tvHour.text = "!!"
                tvHour.setTextColor(0xFFE53935.toInt())
                tvMinute.text = "!!"
                tvMinute.setTextColor(0xFFE53935.toInt())
                return@setOnClickListener
            }

            val idx = ((args.arrivalMillis % drivers.size) + drivers.size).toInt() % drivers.size
            val (driverName, vehicle, plate) = drivers[idx]

            val pickupCal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute + 45)
            }
            val pickupTime = SimpleDateFormat("HH:mm", Locale.US).format(pickupCal.time)
            val landingTime = String.format("%02d:%02d", selectedHour, selectedMinute)

            BookingState.driverName = driverName
            BookingState.vehicle = vehicle
            BookingState.plate = plate
            BookingState.pickupTime = pickupTime
            BookingState.landingTime = landingTime

            view.findViewById<TextView>(R.id.tvDriverName).text = driverName
            view.findViewById<TextView>(R.id.tvVehicle).text = vehicle
            view.findViewById<TextView>(R.id.tvPlate).text = plate
            view.findViewById<TextView>(R.id.tvPickupInfo).text =
                getString(R.string.transfer_pickup_info, pickupTime)

            cardConfirm.visibility = View.VISIBLE
            btnRequest.isEnabled = false
            btnRequest.alpha = 0.4f

            view.findViewById<Button>(R.id.btnGoToHotel).setOnClickListener {
                findNavController().navigate(
                    TransferFragmentDirections.actionTransferFragmentToHotelFragment()
                )
            }
        }
    }
}
