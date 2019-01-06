package com.example.guest.ambarproject.instantapp.service

import com.example.guest.ambarproject.instantapp.model.Repository
import retrofit2.Call
import retrofit2.http.GET

interface RepoService {
    @GET("repositories")
    fun repositories(): Call<List<Repository>>
}