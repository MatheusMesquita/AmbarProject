package com.example.guest.ambarproject.instantapp.network

import com.example.guest.ambarproject.instantapp.service.RepoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun repoService(): RepoService = retrofit.create(RepoService::class.java)
}