package com.example.droid.data

data class Droid(
    val id: Int,
    val name: String,
    val model: String,
    val status: String,
    val description: String,
    val isFavorite: Boolean = false
)
