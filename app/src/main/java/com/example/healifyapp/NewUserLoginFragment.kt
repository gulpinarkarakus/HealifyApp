package com.example.healifyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import android.util.Log

class NewUserLoginFragment : Fragment() {

    private lateinit var prefs: UserPreferences
    private val TAG = "NEW_USER_LOG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView çağrıldı")
        return inflater.inflate(R.layout.fragment_new_user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated başladı")

        prefs = UserPreferences(requireContext())
        Log.i(TAG, "UserPreferences initialize edildi")

        val etName = view.findViewById<EditText>(R.id.etName)
        val etEmail = view.findViewById<EditText>(R.id.etEmailSignup)
        val etPassword = view.findViewById<EditText>(R.id.etPasswordSignup)

        // Şifre tekrar alanı
        val etPasswordConfirm = view.findViewById<EditText>(R.id.etPasswordConfirm)

        val btnSignup = view.findViewById<Button>(R.id.btnSignup)

        btnSignup.setOnClickListener {
            Log.i(TAG, "Kayıt butonuna tıklandı")

            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val passwordConfirm = etPasswordConfirm.text.toString().trim()

            Log.i(TAG, "Girilen ad: $name")
            Log.i(TAG, "Girilen email: $email")
            Log.i(TAG, "Girilen password: $password")
            Log.i(TAG, "Girilen passwordConfirm: $passwordConfirm")

            if (email.isEmpty()) {
                Log.w(TAG, "Email boş")
                Toast.makeText(requireContext(), "Email alanı boş bırakılamaz!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
            if (!emailRegex.matches(email)) {
                Log.w(TAG, "Geçersiz email formatı: $email")
                Toast.makeText(requireContext(), "Geçerli bir email adresi girin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Log.w(TAG, "Şifre boş")
                Toast.makeText(requireContext(), "Şifre alanı boş bırakılamaz!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Log.w(TAG, "Şifre çok kısa: ${password.length} karakter")
                Toast.makeText(requireContext(), "Şifre en az 6 karakter olmalı!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✔ EKLENEN: Şifre eşleşme kontrolü
            if (password != passwordConfirm) {
                Log.w(TAG, "Şifreler uyuşmuyor. password=$password, confirm=$passwordConfirm")
                Toast.makeText(requireContext(), "Şifreler uyuşmuyor!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                Log.i(TAG, "Şifreler eşleşti")
            }

            Log.i(TAG, "Tüm validasyonlar başarıyla geçti, kayıt işlemi başlatılıyor")

            lifecycleScope.launch {
                Log.i(TAG, "DataStore saveUser çağrılıyor")

                prefs.saveUser(email, password, name)

                Log.i(TAG, "Kullanıcı başarıyla kaydedildi: $email")

                Toast.makeText(requireContext(), "Hesap başarıyla oluşturuldu!", Toast.LENGTH_SHORT).show()

                Log.i(TAG, "LoginFragment'a yönlendiriliyor")
                findNavController().navigate(R.id.action_newUserLoginFragment_to_loginFragment)
            }
        }
    }
}
