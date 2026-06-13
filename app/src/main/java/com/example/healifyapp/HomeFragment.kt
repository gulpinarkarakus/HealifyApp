package com.example.healifyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecycler)

        val list = listOf(
            CardItem(R.string.cat_sac_ekimi_name,   "Saç Ekimi",           R.drawable.drawable_hair),
            CardItem(R.string.cat_rinoplasti_name,  "Rinoplasti",          R.drawable.drawable_rinoplasti),
            CardItem(R.string.cat_liposuction_name, "Liposuction",         R.drawable.drawable_liposuction),
            CardItem(R.string.cat_dudak_dolgusu_name,"Dudak Dolgusu",      R.drawable.drawable_lips),
            CardItem(R.string.cat_goz_kapagi_name,  "Göz Kapağı Estetiği", R.drawable.drawable_eye),
            CardItem(R.string.cat_yuz_germe_name,   "Yüz Germe",           R.drawable.drawable_face)
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = CardAdapter(list) { selectedItem ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToDetailFragment(selectedItem.categoryKey)
            findNavController().navigate(action)
        }
    }
}
