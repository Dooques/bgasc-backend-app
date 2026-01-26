package com.dooques.bgascBackend.controller

import com.dooques.bgascBackend.data.dto.HoursDto
import com.dooques.bgascBackend.data.service.SheetsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/hours")
class LoggedHoursController(
    private val sheetsService: SheetsService,
) {

    init {
        println("*********************************************")
        println("***  Logged Hours Controller Initialized  ***")
        println("*********************************************")

    }
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yy")
    val dateFormat: String? = formatter.format(LocalDate.now())

    val currentDate: Instant = Instant.now()

    @GetMapping
    fun getLoggedHours(
        @RequestParam(required = false) month: String?,
        @RequestParam(required = false) year: Int?
    ): List<HoursDto> {
        println("*********************************************")
        return if (month != null && year != null) {
            sheetsService.getCurrentHours("$month $year!A2:F")
        } else {
            sheetsService.getCurrentHours("$dateFormat!A2:F")
        }
    }

    @PostMapping
    fun postLoggedHours(
        @RequestParam(required = false) month: String?,
        @RequestParam(required = false) year: Int?,
        @RequestBody hoursDto: HoursDto
    ): HoursDto {
        return sheetsService.postLoggedHours("$dateFormat!A1:F", hoursDto)
    }
}