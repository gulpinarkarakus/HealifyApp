package com.example.healifyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecycler)

        val list = listOf(
            CardItem("Dr. Ahmet", "Kardiyoloji"),
            CardItem("Dr. Ayşe", "Dermatoloji"),
            CardItem("Dr. Mehmet", "Nöroloji"),
            CardItem("Dr. Elif", "Ortopedi"),
            CardItem("Dr. Ali", "Göz Hastalıkları")
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // Adapter ile tıklama listener ekliyoruz
        recyclerView.adapter = CardAdapter(list) { selectedItem ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToDetailFragment(
                    selectedItem.title,
                    selectedItem.desc
                )
            findNavController().navigate(action)
        }
    }
}