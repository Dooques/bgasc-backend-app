package com.dooques.bgascBackend.data.dto

data class ChildDto(
    val id: String? = null,
    val name: String,
    val age: String,
    val school: String,
    val guardians: List<String>,
    val allergies: String? = null,
    val keyChild: Boolean
)