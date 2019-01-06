package com.example.guest.ambarproject.instantapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    fun init() {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/repositories")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}