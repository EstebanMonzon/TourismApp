package com.ort.tourismapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.ort.tourismapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //viewBinding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseAuath
        var firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
    }

    private fun checkUser(){

    }
}