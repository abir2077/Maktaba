package com.ElOuedUniv.maktaba.data.model

data class Book(
    val id: Int,
    val title: String,
    val isbn: String,
    val pages: Int,
    val imageUrl: String? = null,
    val status: String = "Reading"
)
