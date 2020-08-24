package com.example.city_o

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goMainScreamMenu()

    }

    private fun goMainScreamMenu(){
        val intent = Intent(this, MainScreamMenu::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    //This is a firebase test
    private var database: DatabaseReference = Firebase.database.reference;
    //val citiesRef = database.collection("Users")

}