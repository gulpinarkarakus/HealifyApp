package com.example.healifyapp
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = DetailFragmentArgs.fromBundle(requireArguments())

        val titleText = view.findViewById<TextView>(R.id.detailTitle)
        val descText = view.findViewById<TextView>(R.id.detailDesc)

        titleText.text = args.doctorName
        descText.text = args.doctorSpecialty
    }
}