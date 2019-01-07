package com.example.guest.ambarproject.instantapp.model

data class Repository(val id: Int, val name: String, val full_name: String, val owner: Owner, val html_url: String, val description: String, val stargazers_count: Int, val forks_count: Int)
data class Owner(val login: String, val avatar_url: String)