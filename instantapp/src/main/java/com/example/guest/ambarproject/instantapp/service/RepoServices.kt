package com.example.guest.ambarproject.instantapp.service

import com.example.guest.ambarproject.instantapp.model.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoService {
    @GET("repositories")
    fun repositories(): Call<List<Repository>>

    @GET("repos/{login}/{name}")
    fun repository(@Path(value = "login", encoded = true) login: String,
                   @Path(value = "name", encoded = true) name: String): Call<Repository>
}