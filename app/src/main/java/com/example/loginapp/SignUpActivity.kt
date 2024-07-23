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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var fNameEditText: EditText
    private lateinit var lNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var psswdEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var loggingInTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        fNameEditText = findViewById(R.id.fName)
        lNameEditText = findViewById(R.id.lName)
        emailEditText = findViewById(R.id.email)
        psswdEditText = findViewById(R.id.psswd)
        signUpButton = findViewById(R.id.signUp)
        loggingInTextView = findViewById(R.id.LoggingIn)

        val logInString = "Already an Existing User? Log In"
        val spannableString = SpannableString(logInString)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Remove underline
            }
        }

        spannableString.setSpan(
            clickableSpan,
            logInString.indexOf("Log In"),
            logInString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        loggingInTextView.text = spannableString
        loggingInTextView.movementMethod = LinkMovementMethod.getInstance()

        signUpButton.setOnClickListener {
            if (validateInputs()) {
                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@SignUpActivity, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun validateInputs(): Boolean {
        var isValid = true

        if (fNameEditText.text.toString().trim().isEmpty()) {
            fNameEditText.error = "First name is required"
            isValid = false
        }

        if (lNameEditText.text.toString().trim().isEmpty()) {
            lNameEditText.error = "Last name is required"
            isValid = false
        }

        if (emailEditText.text.toString().trim()
                .isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString())
                .matches()
        ) {
            emailEditText.error = "Invalid email address"
            isValid = false
        }

        if (psswdEditText.text.toString().trim()
                .isEmpty() || psswdEditText.text.toString().length < 8
        ) {
            psswdEditText.error = "Password must be at least 8 characters"
            isValid = false
        }

        return isValid
    }
}