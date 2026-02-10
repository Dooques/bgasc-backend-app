package com.dooques.bgascBackend.data.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class HoursDto(
    val id: Long? = 0,
    val name: String? = "",
    val type: String? = "",
    @JsonFormat(pattern = "d/M/yyyy")
    val date: LocalDate? = LocalDate.now(),
    val hours: Float? = 0f,
    val extraHours: Float? = 0f,
    val reasonForExtra: String? = "",
    val late: Boolean? = false,
    val reasonForBeingLate: String? = "",
)
