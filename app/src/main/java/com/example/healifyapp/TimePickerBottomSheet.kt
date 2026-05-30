package com.example.healifyapp

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimePickerBottomSheet : BottomSheetDialogFragment() {

    var onTimeSelected: ((hour: Int, minute: Int) -> Unit)? = null

    private val minuteValues = Array(12) { String.format("%02d", it * 5) }
    private var initialHour = 12
    private var initialMinuteIndex = 0

    companion object {
        fun newInstance(hour: Int = 12, minute: Int = 0): TimePickerBottomSheet {
            return TimePickerBottomSheet().apply {
                initialHour = hour
                initialMinuteIndex = (minute / 5).coerceIn(0, 11)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_time_picker_bottom, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val npHour = view.findViewById<NumberPicker>(R.id.npHour)
        val npMinute = view.findViewById<NumberPicker>(R.id.npMinute)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirmTime)

        npHour.minValue = 0
        npHour.maxValue = 23
        npHour.value = initialHour
        npHour.displayedValues = Array(24) { String.format("%02d", it) }
        npHour.wrapSelectorWheel = true

        npMinute.minValue = 0
        npMinute.maxValue = 11
        npMinute.value = initialMinuteIndex
        npMinute.displayedValues = minuteValues
        npMinute.wrapSelectorWheel = true

        styleNumberPicker(npHour)
        styleNumberPicker(npMinute)

        btnConfirm.setOnClickListener {
            onTimeSelected?.invoke(npHour.value, npMinute.value * 5)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val sheet = (dialog as? BottomSheetDialog)
            ?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        sheet?.setBackgroundResource(android.R.color.transparent)
        sheet?.let { BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED }
    }

    private fun styleNumberPicker(picker: NumberPicker) {
        try {
            val paintField = NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint")
            paintField.isAccessible = true
            (paintField.get(picker) as? Paint)?.apply {
                color = Color.parseColor("#1A1A1A")
            }
            val dividerField = NumberPicker::class.java.getDeclaredField("mSelectionDivider")
            dividerField.isAccessible = true
            dividerField.set(picker, android.graphics.drawable.ColorDrawable(Color.parseColor("#3A7BD5")))
            picker.invalidate()
        } catch (_: Exception) { }
    }
}
