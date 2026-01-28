package com.dooques.bgascBackend.data.dto

data class StaffDto(
    val id: String? = null,
    val name: String = "",
    val email: String = "",
    val number: String = "",
    val role: String = "",
    val address: Map<String, String> = mapOf(),
    val nextOfKin: Map<String, String> = mapOf()
)