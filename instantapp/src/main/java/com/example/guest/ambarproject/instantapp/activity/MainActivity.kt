package com.example.guest.ambarproject.instantapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.guest.ambarproject.instantapp.R
import com.example.guest.ambarproject.instantapp.network.RetrofitInitializer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitInitializer()
    }
}
