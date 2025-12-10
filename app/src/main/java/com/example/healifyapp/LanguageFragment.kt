import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.healifyapp.R
import java.util.Locale

class LanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TÜRKÇE SEÇİMİ
        view.findViewById<Button>(R.id.btnTurkish).setOnClickListener {
            setLocale("tr")
            findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
        }

        // İNGİLİZCE SEÇİMİ
        view.findViewById<Button>(R.id.btnEnglish).setOnClickListener {
            setLocale("en")
            findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.btnGerman).setOnClickListener {
            setLocale("de")
            findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
        }

//        view.findViewById<Button>(R.id.btnArabic).setOnClickListener {
//            setLocale("ar")
//            findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
//        }

    }

    // --------- DİL DEĞİŞTİRME FONKSİYONU ---------
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        // Seçilen dili kaydet
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putString("app_language", languageCode).apply()

        // Uygulamayı güncelle (zorunlu)
        activity?.recreate()
    }
}
