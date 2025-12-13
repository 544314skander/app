package com.example.cat.data

data class Cat(
    val id: Int,
    val name: String,
    val model: String,
    val status: String,
    val description: String,
    val isFavorite: Boolean = false
)
