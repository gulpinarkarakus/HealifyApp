package com.example.healifyapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.Locale

class LanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_language, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnTurkish).setOnClickListener {
            applyLocaleAndNavigate("tr")
        }
        view.findViewById<Button>(R.id.btnEnglish).setOnClickListener {
            applyLocaleAndNavigate("en")
        }
        view.findViewById<Button>(R.id.btnGerman).setOnClickListener {
            applyLocaleAndNavigate("de")
        }
        view.findViewById<Button>(R.id.btnArabic).setOnClickListener {
            applyLocaleAndNavigate("ar")
        }
    }

    private fun applyLocaleAndNavigate(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("app_language", languageCode).apply()

        // Navigate first — back stack will be preserved through recreation
        findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
        activity?.recreate()
    }
}
