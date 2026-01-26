package com.dooques.bgascBackend.data.service

import com.dooques.bgascBackend.data.dto.HoursDto
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.ValueRange
import com.sun.jdi.InvalidTypeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class SheetsService(
    private val sheetsService: Sheets,
    @Value($$"${SHEETS_ID}") private val spreadsheetID: String
) {
    /* This service will interact with a sheets document
    *  Get Current Hours - Return the hours from the current month or set a specific date.
    *  Post Logged Hours - Post your hours to the sheet using a DTO (Data Transfer Object)
    *  */
    val spreadsheetId: String = spreadsheetID

    fun getCurrentHours(
        range: String
    ): List<HoursDto> {

        val response = sheetsService.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute()
        val hoursList = mutableListOf<HoursDto>()

        println("Response: $response")

        response.values.map { value ->
            if (value is List<*>) {
                value.map { hoursObject ->
                    if (hoursObject !is List<*>) throw InvalidTypeException(hoursObject.toString())
                    else {
                        // Convert String date to LocalDate object
                        val day = hoursObject[2].toString().split("/")[0]
                        val month = hoursObject[2].toString().split("/")[1]
                        val year = hoursObject[2].toString().split("/")[2]

                        // Prepare values to be sent
                        val name = hoursObject[0].toString()
                        val type = hoursObject[1].toString()
                        val date = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
                        val hours = hoursObject[3].toString().toInt()
                        val extraHours = hoursObject[4].toString().toIntOrNull()
                        var reasonForExtraHours: String? = null
                        if (hoursObject.size > 5) {
                            reasonForExtraHours = hoursObject[5].toString()
                        }

                        hoursList.add(HoursDto(
                            name = name,
                            type = type,
                            date = date,
                            hours = hours,
                            extraHours = extraHours,
                            reasonForExtra = reasonForExtraHours,
                        ))
                    }
                }
            }
        }.toList()
        println("    Returned: $response")
        println("*********************************************")
        return hoursList
    }

    fun postLoggedHours(range: String, hoursDto: HoursDto): HoursDto {

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateString = formatter.format(hoursDto.date)

        val hoursList = listOf<Any>(
            hoursDto.name,
            hoursDto.type,
            dateString,
            hoursDto.hours,
            hoursDto.extraHours ?: 0,
            hoursDto.reasonForExtra ?: ""
        )
        val loggedHours = ValueRange().setValues(listOf(hoursList))

        println("    Posted $loggedHours succesfully")
        println("*********************************************")
        sheetsService.spreadsheets().values()
            .append(spreadsheetId, range, loggedHours)
            .setValueInputOption("RAW")
            .execute()
        return hoursDto
    }
}