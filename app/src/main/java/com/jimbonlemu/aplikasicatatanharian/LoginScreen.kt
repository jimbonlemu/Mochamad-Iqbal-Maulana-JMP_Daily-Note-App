package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get.GetToast

class LoginScreen : AppCompatActivity() {

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (isValidCredentials(email, password)) {
                GetToast("Login successful!")
                Get.off(this, ListNoteScreen::class.java)

            } else {
                GetToast("Invalid credentials. Please try again.")
            }
        }
    }

    private fun isValidCredentials(email: String, password: String): Boolean {

        val validEmail = "admincmd"
        val validPassword = "admincmd"

        return email == validEmail && password == validPassword
    }

}