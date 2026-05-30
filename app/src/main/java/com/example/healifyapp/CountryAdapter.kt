package com.example.healifyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    private val countries: List<CountryOption>,
    private val onCountrySelected: (CountryOption) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var selectedIndex = -1

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFlag: TextView = itemView.findViewById(R.id.tvFlag)
        val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        val tvCityIata: TextView = itemView.findViewById(R.id.tvCityIata)
        val tvCheck: TextView = itemView.findViewById(R.id.tvCheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        val isSelected = position == selectedIndex

        holder.tvFlag.text = country.flag
        holder.tvCountryName.text = country.name
        holder.tvCityIata.text = "${country.city}  •  ${country.iataCode}"
        holder.tvCheck.visibility = if (isSelected) View.VISIBLE else View.GONE
        holder.itemView.setBackgroundResource(
            if (isSelected) R.drawable.bg_country_selected else R.drawable.bg_country_default
        )

        holder.itemView.setOnClickListener {
            val old = selectedIndex
            selectedIndex = holder.adapterPosition
            notifyItemChanged(old)
            notifyItemChanged(selectedIndex)
            onCountrySelected(country)
        }
    }

    override fun getItemCount() = countries.size
}
