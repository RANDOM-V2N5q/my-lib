package com.example.mylib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Firebase.auth.currentUser != null) {
            Navigation.findNavController(this, R.id.rootNavHostFragment)
                .navigate(R.id.action_authentication_to_mainFragment)
        }
    }
}