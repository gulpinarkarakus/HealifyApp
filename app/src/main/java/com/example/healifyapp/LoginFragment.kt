import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.healifyapp.R
import com.example.healifyapp.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())

        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val newUserSignup = view.findViewById<TextView>(R.id.newUserSignup)

        newUserSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_newUserLoginFragment)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val savedEmail = userPreferences.getEmail.first()
                val savedPassword = userPreferences.getPassword.first()

                if (email == savedEmail && password == savedPassword) {

                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                } else {
                    Toast.makeText(requireContext(), "Hatalı email veya şifre!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
