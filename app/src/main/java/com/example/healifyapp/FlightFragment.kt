package com.example.healifyapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FlightFragment : Fragment(R.layout.fragment_flight) {

    private val args: FlightFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category
        val stayDays = procedureStayDays[category] ?: Pair(5, 7)

        view.findViewById<TextView>(R.id.tvFlightDoctorName).text = args.doctorName
        view.findViewById<TextView>(R.id.tvFlightCategory).text = category
        view.findViewById<TextView>(R.id.tvStayInfo).text =
            "Bu işlem için randevu tarihinden itibaren\n${stayDays.first}-${stayDays.second} gün Türkiye'de kalmanız önerilir."

        val appointmentCal = Calendar.getInstance().apply { timeInMillis = args.appointmentMillis }
        val arrivalCal = (appointmentCal.clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, -1) }
        val returnCal = (appointmentCal.clone() as Calendar).apply { add(Calendar.DAY_OF_MONTH, stayDays.second) }

        val displayFmt = SimpleDateFormat("dd MMM yyyy", Locale("tr"))
        view.findViewById<TextView>(R.id.tvArrivalDate).text = displayFmt.format(arrivalCal.time)
        view.findViewById<TextView>(R.id.tvReturnDate).text = displayFmt.format(returnCal.time)

        BookingState.arrivalMillis = arrivalCal.timeInMillis
        BookingState.returnMillis = returnCal.timeInMillis

        val btnSearch = view.findViewById<Button>(R.id.btnSearchFlights)
        var selectedCountry: CountryOption? = null

        val adapter = CountryAdapter(popularCountries) { country ->
            selectedCountry = country
            btnSearch.isEnabled = true
            btnSearch.alpha = 1f
        }

        view.findViewById<RecyclerView>(R.id.rvCountries).apply {
            layoutManager = LinearLayoutManager(requireContext())
            isNestedScrollingEnabled = false
            this.adapter = adapter
        }

        val kayakFmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        btnSearch.setOnClickListener {
            val country = selectedCountry ?: return@setOnClickListener
            val url = "https://www.kayak.com.tr/flights/${country.iataCode}-IST/" +
                    "${kayakFmt.format(arrivalCal.time)}/${kayakFmt.format(returnCal.time)}"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        view.findViewById<MaterialButton>(R.id.btnGoToTransfer).setOnClickListener {
            val action = FlightFragmentDirections.actionFlightFragmentToTransferFragment(
                arrivalMillis = arrivalCal.timeInMillis,
                doctorName = args.doctorName,
                category = category
            )
            findNavController().navigate(action)
        }
    }
}
