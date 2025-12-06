import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.healifyapp.R

class LanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.btnTurkish).setOnClickListener {
            findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.btnEnglish).setOnClickListener {
            findNavController().navigate(R.id.action_languageFragment_to_loginFragment)
        }
    }
}