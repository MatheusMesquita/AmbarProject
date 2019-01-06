package com.example.guest.ambarproject.instantapp.service

import retrofit2.http.GET

interface RepoService {
    @GET()
    fun repos()
}