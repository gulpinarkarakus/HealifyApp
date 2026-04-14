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
            CardItem("Saç Ekimi", R.drawable.drawable_hair),
            CardItem("Rinoplasti", R.drawable.drawable_rinoplasti),
            CardItem("Liposuction", R.drawable.drawable_liposuction),
            CardItem("Dudak Dolgusu", R.drawable.drawable_lips),
            CardItem("Göz Kapağı Estetiği", R.drawable.drawable_eye),
            CardItem("Yüz Germe", R.drawable.drawable_face)
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = CardAdapter(list) { selectedItem ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToDetailFragment(selectedItem.title)

            findNavController().navigate(action)
        }
    }
}