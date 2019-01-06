package com.example.guest.ambarproject.instantapp.model

data class Repository(val id: Int, val name: String, val full_name: String, val owner: Owner, val description: String, val stargazers_url: String, val stars: Int, val forks: Int)
data class Owner(val login: String, val avatar_url: String, val html_url: String)

//https://api.github.com/search/repositories?q=user%3Astrongloop+repo%3Aexpress+express FORKS QUERY