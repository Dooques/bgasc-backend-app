package com.dooques.bgascBackend.data.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate


data class HoursDto(
    val id: Long? = 0,
    val name: String,
    val type: String,
    @JsonFormat(pattern = "dd/MM/yyyy")
    val date: LocalDate,
    val hours: Int,
    val extraHours: Int? = null,
    val reasonForExtra: String? = null
)