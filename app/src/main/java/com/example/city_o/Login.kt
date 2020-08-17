package com.example.city_o

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.properties.Delegates

class Login : AppCompatActivity() {

    private val TAG = "LoginActivity"
    //global variables
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var mProgressBar: ProgressBar

    //This variable is for firebase authentication
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialise()
    }

    private fun initialise() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mProgressBar = ProgressBar(this)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun loginUser() {

        email = etEmail.text.toString()
        password = etPassword.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
           // mProgressBar.isAnimating
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                        task ->
                    if (task.isSuccessful) {
                        goHome()
                    } else {
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goHome() {
        //mProgressBar.hide()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun login(view: View) {
        loginUser()
    }

   /** fun forgotPassword(view: View) {
        startActivity(Intent(this,
            ForgotPasswordActivity::class.java))
    }*/
    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }



}