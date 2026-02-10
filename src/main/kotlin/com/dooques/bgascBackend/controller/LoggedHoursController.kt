package com.dooques.bgascBackend.controller

import com.dooques.bgascBackend.data.dto.HoursDto
import com.dooques.bgascBackend.data.service.SheetsService
import org.springframework.beans.factory.ObjectFactory
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
    @RequestMapping("/get")
    fun getLoggedHours(
        @RequestParam(required = false) month: String?,
        @RequestParam(required = false) year: Int?
    ): List<HoursDto> {
        println("*********************************************")
        println("Attempting to get logged hours for: ${
            if (month != null ) "$month $year" else dateFormat
        }")
        return if (month != null && year != null) {
            sheetsService.getCurrentHours("$month $year!A2:H")
        } else {
            sheetsService.getCurrentHours("$dateFormat!A2:H")
        }
    }

    @PostMapping
    @RequestMapping("/post")
    fun postLoggedHours(
        @RequestParam(required = false) month: String?,
        @RequestParam(required = false) year: Int?,
        @RequestBody hoursDto: HoursDto
    ): HoursDto {
        println("*********************************************")
        println("Attempting to post hours: $hoursDto")
        return sheetsService.postLoggedHours(
            dateRange = "$month $year",
            cellRange = "!A2:H",
            hoursDto =  hoursDto
        )
    }
}