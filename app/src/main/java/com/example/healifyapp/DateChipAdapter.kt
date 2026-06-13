package com.example.healifyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateChipAdapter(
    private val dates: List<Calendar>,
    private val onDateSelected: (Calendar) -> Unit
) : RecyclerView.Adapter<DateChipAdapter.DateViewHolder>() {

    private var selectedIndex = -1

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDayName: TextView = itemView.findViewById(R.id.tvDayName)
        val tvDayNumber: TextView = itemView.findViewById(R.id.tvDayNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date_chip, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = dates[position]
        val dayOfWeek = date.get(Calendar.DAY_OF_WEEK)
        val dayNumber = date.get(Calendar.DAY_OF_MONTH)

        val today = Calendar.getInstance()
        val isToday = date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
        val isSelected = position == selectedIndex

        val dayFmt = SimpleDateFormat("EEE", Locale.getDefault())
        holder.tvDayName.text = dayFmt.format(date.time).uppercase(Locale.getDefault())
        holder.tvDayNumber.text = dayNumber.toString()

        when {
            isSelected -> {
                holder.itemView.setBackgroundResource(R.drawable.bg_date_chip_selected)
                holder.tvDayName.setTextColor(0xFFFFFFFF.toInt())
                holder.tvDayNumber.setTextColor(0xFFFFFFFF.toInt())
            }
            isToday -> {
                holder.itemView.setBackgroundResource(R.drawable.bg_date_chip_today)
                holder.tvDayName.setTextColor(0xFF3A7BD5.toInt())
                holder.tvDayNumber.setTextColor(0xFF3A7BD5.toInt())
            }
            else -> {
                holder.itemView.setBackgroundResource(R.drawable.bg_date_chip_default)
                holder.tvDayName.setTextColor(0xFF666666.toInt())
                holder.tvDayNumber.setTextColor(0xFF1A1A1A.toInt())
            }
        }

        holder.itemView.setOnClickListener {
            val old = selectedIndex
            selectedIndex = holder.adapterPosition
            notifyItemChanged(old)
            notifyItemChanged(selectedIndex)
            onDateSelected(date)
        }
    }

    override fun getItemCount() = dates.size
}
