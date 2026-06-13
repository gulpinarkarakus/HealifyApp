package com.example.healifyapp

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private val args: CalendarFragmentArgs by navArgs()
    private lateinit var doctor: DoctorProfileBooking

    private var selectedDate: Calendar? = null
    private var selectedTime: String = ""
    private var selectedTimeView: TextView? = null
    private var allDates: List<Calendar> = emptyList()
    private var weekOffset = 0

    private lateinit var tvMonthYear: TextView
    private lateinit var rvDates: RecyclerView
    private lateinit var cardSummary: CardView
    private lateinit var tvSummaryDate: TextView
    private lateinit var tvSummaryTime: TextView
    private lateinit var btnCreate: Button
    private lateinit var layoutSuccess: LinearLayout
    private lateinit var tvSuccessDetails: TextView
    private lateinit var tvStayDuration: TextView
    private lateinit var btnPlanFlight: MaterialButton

    private val unavailableSlots = setOf("09:30", "11:00", "14:00", "16:30")
    private val morningSlots = listOf("09:00", "09:30", "10:00", "10:30", "11:00", "11:30")
    private val afternoonSlots = listOf(
        "13:00", "13:30", "14:00", "14:30",
        "15:00", "15:30", "16:00", "16:30", "17:00"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doctor = args.selectedDoctor
        view.findViewById<TextView>(R.id.tvDoctorName).text = doctor.name

        tvMonthYear = view.findViewById(R.id.tvMonthYear)
        rvDates = view.findViewById(R.id.rvDates)
        cardSummary = view.findViewById(R.id.cardSummary)
        tvSummaryDate = view.findViewById(R.id.tvSummaryDate)
        tvSummaryTime = view.findViewById(R.id.tvSummaryTime)
        btnCreate = view.findViewById(R.id.btnCreateAppointment)
        layoutSuccess = view.findViewById(R.id.layoutSuccess)
        tvSuccessDetails = view.findViewById(R.id.tvSuccessDetails)
        tvStayDuration = view.findViewById(R.id.tvStayDuration)
        btnPlanFlight = view.findViewById(R.id.btnPlanFlight)

        allDates = generateDates()
        rvDates.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        loadWeek()

        view.findViewById<ImageButton>(R.id.btnPrevWeek).setOnClickListener {
            if (weekOffset > 0) { weekOffset--; loadWeek() }
        }
        view.findViewById<ImageButton>(R.id.btnNextWeek).setOnClickListener {
            val maxWeek = (allDates.size - 1) / 5
            if (weekOffset < maxWeek) { weekOffset++; loadWeek() }
        }

        setupTimeGrid(view)
        btnCreate.setOnClickListener { onBookAppointment() }
    }

    private fun loadWeek() {
        val start = weekOffset * 5
        val end = minOf(start + 5, allDates.size)
        val weekDates = if (start < allDates.size) allDates.subList(start, end) else emptyList()

        rvDates.adapter = DateChipAdapter(weekDates) { date ->
            selectedDate = date
            updateMonthYear(date)
            updateSummary()
        }
        if (weekDates.isNotEmpty()) updateMonthYear(weekDates[0])
    }

    private fun generateDates(): List<Calendar> {
        val dates = mutableListOf<Calendar>()
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        repeat(35) {
            val dow = cal.get(Calendar.DAY_OF_WEEK)
            if (dow != Calendar.SATURDAY && dow != Calendar.SUNDAY) {
                dates.add(cal.clone() as Calendar)
            }
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dates
    }

    private fun setupTimeGrid(view: View) {
        val container = view.findViewById<LinearLayout>(R.id.timeSlotContainer)
        container.removeAllViews()
        addSectionLabel(container, getString(R.string.cal_morning))
        addTimeRow(container, morningSlots)
        addSectionLabel(container, getString(R.string.cal_afternoon))
        addTimeRow(container, afternoonSlots)
    }

    private fun addSectionLabel(container: LinearLayout, text: String) {
        container.addView(TextView(requireContext()).apply {
            this.text = text
            textSize = 13f
            setTextColor(0xFF888888.toInt())
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also { it.setMargins(0, dp(12), 0, dp(8)) }
        })
    }

    private fun addTimeRow(container: LinearLayout, slots: List<String>) {
        slots.chunked(3).forEach { rowSlots ->
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).also { it.setMargins(0, 0, 0, dp(10)) }
            }
            rowSlots.forEach { time ->
                val isUnavailable = unavailableSlots.contains(time)
                row.addView(TextView(requireContext()).apply {
                    this.text = time
                    textSize = 14f
                    gravity = android.view.Gravity.CENTER
                    setPadding(0, dp(13), 0, dp(13))
                    setTextColor(if (isUnavailable) 0xFFBBBBBB.toInt() else 0xFF3A7BD5.toInt())
                    setBackgroundResource(
                        if (isUnavailable) R.drawable.bg_time_slot_unavailable
                        else R.drawable.bg_time_slot_default
                    )
                    if (isUnavailable) paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    layoutParams = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                    ).also { it.setMargins(0, 0, dp(8), 0) }
                    if (!isUnavailable) setOnClickListener { onTimeSelected(this, time) }
                })
            }
            repeat(3 - rowSlots.size) {
                row.addView(View(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(0, dp(46), 1f)
                        .also { it.setMargins(0, 0, dp(8), 0) }
                })
            }
            container.addView(row)
        }
    }

    private fun onTimeSelected(tv: TextView, time: String) {
        selectedTimeView?.apply {
            setBackgroundResource(R.drawable.bg_time_slot_default)
            setTextColor(0xFF3A7BD5.toInt())
        }
        tv.setBackgroundResource(R.drawable.bg_time_slot_selected)
        tv.setTextColor(0xFFFFFFFF.toInt())
        selectedTimeView = tv
        selectedTime = time
        updateSummary()
    }

    private fun updateSummary() {
        if (selectedDate != null) {
            cardSummary.visibility = View.VISIBLE
            val fmt = SimpleDateFormat("dd MMMM yyyy, EEEE", Locale.getDefault())
            tvSummaryDate.setTextColor(0xFF1A1A1A.toInt())
            tvSummaryDate.text = fmt.format(selectedDate!!.time)
            tvSummaryTime.text = if (selectedTime.isEmpty())
                getString(R.string.cal_time_not_selected)
            else
                getString(R.string.cal_time_selected, selectedTime)
        } else {
            cardSummary.visibility = View.GONE
        }
    }

    private fun onBookAppointment() {
        if (selectedDate == null) {
            cardSummary.visibility = View.VISIBLE
            tvSummaryDate.setTextColor(0xFFE53935.toInt())
            tvSummaryDate.text = getString(R.string.cal_please_select_date)
            tvSummaryTime.text = ""
            return
        }
        if (selectedTime.isEmpty()) {
            cardSummary.visibility = View.VISIBLE
            tvSummaryDate.setTextColor(0xFFE53935.toInt())
            tvSummaryDate.text = getString(R.string.cal_please_select_time)
            tvSummaryTime.text = ""
            return
        }

        val fmt = SimpleDateFormat("dd MMMM yyyy, EEEE", Locale.getDefault())
        tvSuccessDetails.text = "${fmt.format(selectedDate!!.time)}\n${getString(R.string.cal_time_selected, selectedTime)}"

        val stayDays = procedureStayDays[doctor.category] ?: Pair(5, 7)
        val categoryName = getString(getCategoryNameResId(doctor.category))
        tvStayDuration.text = getString(R.string.cal_stay_recommendation, categoryName, stayDays.first, stayDays.second)

        BookingState.selectedDoctor = doctor
        BookingState.doctorName = doctor.name
        BookingState.category = doctor.category
        BookingState.appointmentDateMillis = selectedDate!!.timeInMillis
        BookingState.appointmentTime = selectedTime
        BookingState.stayDays = stayDays.second

        btnPlanFlight.setOnClickListener {
            val action = CalendarFragmentDirections.actionCalendarFragmentToFlightFragment(
                category = doctor.category,
                appointmentMillis = selectedDate!!.timeInMillis,
                appointmentTime = selectedTime,
                doctorName = doctor.name
            )
            findNavController().navigate(action)
        }

        layoutSuccess.visibility = View.VISIBLE
        cardSummary.visibility = View.GONE
        btnCreate.isEnabled = false
        btnCreate.alpha = 0.4f
    }

    private fun updateMonthYear(cal: Calendar) {
        val fmt = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        tvMonthYear.text = fmt.format(cal.time).replaceFirstChar { it.uppercase() }
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
}
