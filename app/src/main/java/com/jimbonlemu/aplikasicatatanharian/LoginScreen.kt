package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get.GetToast

class LoginScreen : AppCompatActivity() {

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (isValidCredentials(email, password)) {
                GetToast("Login Berhasil Gro!")
                Get.offAll(this, ListNoteScreen::class.java)

            } else {
                GetToast(" Password atau Username salah")
            }
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {

        val validEmail = "admincmd"
        val validPassword = "admincmd"

        return email == validEmail && password == validPassword
    }

}