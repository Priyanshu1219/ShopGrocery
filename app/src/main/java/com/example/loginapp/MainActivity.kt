package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var signUpTextView: TextView
    private lateinit var logInButton: Button
    private lateinit var userNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rememberMeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signUpTextView = findViewById(R.id.signUp)
        logInButton = findViewById(R.id.logIn)
        userNameEditText = findViewById(R.id.userName)
        passwordEditText = findViewById(R.id.pswd)
        rememberMeSwitch = findViewById(R.id.rememberme)

        val signUpString = "Not an Existing User? Sign Up"
        val spannableString = SpannableString(signUpString)
        val sharedPreferences = getSharedPreferences("login_preferences", MODE_PRIVATE)
        val isRemembered = sharedPreferences.getBoolean("rememberMe", false)
        val editor = sharedPreferences.edit()

        if (isRemembered) {
            val userName = sharedPreferences.getString("username", "")
            userNameEditText.setText(userName)
            rememberMeSwitch.isChecked = true
        } else {
            editor.clear()
            editor.apply()
            userNameEditText.setText("")
            rememberMeSwitch.isChecked = false
        }

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Remove underline
            }
        }
        spannableString.setSpan(
            clickableSpan,
            signUpString.indexOf("Sign Up"),
            signUpString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        signUpTextView.text = spannableString
        signUpTextView.movementMethod = LinkMovementMethod.getInstance()

        logInButton.setOnClickListener {
            val userName = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (userName.isEmpty()) {
                userNameEditText.error = "Username is required"
                userNameEditText.requestFocus()
            } else {
                userNameEditText.error = null
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Password is required"
                passwordEditText.requestFocus()
            } else {
                passwordEditText.error = null
            }

            if (userName == "priyanshu@gmail.com" && password == "Priyanshu@12") {
                val intent = Intent(this@MainActivity, HomeScreen::class.java)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()

                if (rememberMeSwitch.isChecked) {
                    editor.putBoolean("rememberMe", true)
                    editor.putString("username", userName)
                    editor.apply()
                }else{
                    editor.putBoolean("rememberMe", false)
                    editor.clear().apply()
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Invalid Username or Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}