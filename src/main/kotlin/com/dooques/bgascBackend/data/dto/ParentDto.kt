package com.dooques.bgascBackend.data.dto

data class ParentDto(
    val id: String? = null,
    val name: String,
    val email: String,
    val number: String,
    val age: String,
    val address: String,
    val children: List<String>
)