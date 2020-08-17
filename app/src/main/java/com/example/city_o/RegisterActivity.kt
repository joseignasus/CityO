package com.example.city_o

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    //global variables
    private var firstName by Delegates.notNull<String>()
    private var lastName by Delegates.notNull<String>()
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initialise()
    }

    private fun initialise() {
        //llamamos nuestras vista
        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        databaseReference = database.reference.child("Users")
    }

    private fun createNewAccount() {

        firstName = txtName.text.toString()
        lastName = txtLastName.text.toString()
        email = txtEmail.text.toString()
        password = txtPassword.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
        ) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    val user: FirebaseUser = auth.currentUser!!
                    verifyEmail(user);
                    val currentUserDb = databaseReference.child(user.uid)
                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)
                    updateUserInfoAndGoHome()
                }.addOnFailureListener {
                    Toast.makeText(
                        this, "Error en la autenticación.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        } else {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    fun register(view: View) {
        createNewAccount()
    }

    private fun updateUserInfoAndGoHome() {
        //Nos vamos a la actividad home
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Email " + user.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Error al verificar el correo ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}