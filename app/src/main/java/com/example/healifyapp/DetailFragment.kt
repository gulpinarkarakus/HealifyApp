package com.example.healifyapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    private val categoryInfo = mapOf(
        "Rinoplasti" to "Burun estetiği operasyonlarında uzmanlaşmış cerrahlarımız.",
        "Saç Ekimi" to "Doğal görünümlü saç ekimi gerçekleştiren uzman kadromuz.",
        "Liposuction" to "Modern teknikleri kullanan estetik cerrahlarımız."
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedCategory = args.doctorName

        view.findViewById<TextView>(R.id.detailTitle).text = "$selectedCategory Uzmanları"
        view.findViewById<TextView>(R.id.categorySubtitle).text = "$selectedCategory Hakkında"
        view.findViewById<TextView>(R.id.categoryDescriptionText).text =
            categoryInfo[selectedCategory] ?: "Uzman doktorlarımız aşağıda listelenmiştir."

        val filteredDoctors = allDoctors.filter { it.category == selectedCategory }

        val recyclerView = view.findViewById<RecyclerView>(R.id.doctorRecyclerView)
        recyclerView.adapter = DoctorAdapter(filteredDoctors)
    }
}