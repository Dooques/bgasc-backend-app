package com.dooques.bgascBackend.data.service

import com.dooques.bgascBackend.data.dto.HoursDto
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.model.AddSheetRequest
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse
import com.google.api.services.sheets.v4.model.Request
import com.google.api.services.sheets.v4.model.SheetProperties
import com.google.api.services.sheets.v4.model.SpreadsheetProperties
import com.google.api.services.sheets.v4.model.ValueRange
import com.sun.jdi.InvalidTypeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.forEach
import kotlin.collections.listOf

@Service
class SheetsService(
    private val sheetsService: Sheets,
    @Value($$"${SHEETS_ID}") private val spreadsheetID: String
) {
    private lateinit var properties: SpreadsheetProperties

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
        val values = response.getValues()
        if (values.isEmpty()) {
            println("No data found.")
            return hoursList
        }

        println("Response: $response")
        println("Rows: $values")

        for (row in values ?: listOf<Any>()) {
            try {
                when (row) {
                    is List<*> -> {

                        println("Row: $row")
                        if (row.size < 8) {
                            println("Error: Incorrect number of columns")
                            continue
                        }

                        // Prepare values to be sent
                        val name = row.getOrNull(0).toString()
                        val type = row.getOrNull(1).toString()
                        val dateStr = row.getOrNull(2).toString()

                        val (day, month, year) = dateStr.split("/")
                            .let { Triple(it[0], it[1], it[2]) }
                        val date = LocalDate.of(year.toInt(), month.toInt(), day.toInt())

                        val hours = row.getOrNull(3).toString().toFloat()
                        val extraHours = row.getOrNull(4).toString().toFloatOrNull()
                        val reasonForExtraHours = row.getOrNull(5).toString()
                        val late = row.getOrNull(6).toString().toBoolean()
                        val reasonForBeingLate = row.getOrNull(7).toString()

                        hoursList.add(
                            HoursDto(
                                name = name,
                                type = type,
                                date = date,
                                late = late,
                                reasonForBeingLate = reasonForBeingLate,
                                hours = hours,
                                extraHours = extraHours,
                                reasonForExtra = reasonForExtraHours,
                            )
                        )
                    } else -> {
                        println("Error: Incorrect type: ${row.javaClass.name}")
                        throw InvalidTypeException("Invalid type: $row")
                    }
                }
            } catch (e: InvalidTypeException) {
                println("Error: $e")
            }
        }
        println("    Returned: $response")
        println("*********************************************")
        return hoursList
    }

    fun postLoggedHours(dateRange: String, cellRange: String, hoursDto: HoursDto): HoursDto {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateString = formatter.format(hoursDto.date)

        val hoursList = listOf<Any>(
            hoursDto.name ?: "",
            hoursDto.type ?: "",
            dateString,
            hoursDto.hours ?: 0f,
            hoursDto.extraHours ?: 0f,
            hoursDto.reasonForExtra ?: "",
            hoursDto.late ?: false,
            hoursDto.reasonForBeingLate ?: ""
        )
        val loggedHours = ValueRange().setValues(listOf(hoursList))

        return try {
            println("Posting hours to sheet: $dateRange")
            sheetsService.spreadsheets().values()
                .append(spreadsheetId, "$dateRange$cellRange", loggedHours)
                .setValueInputOption("RAW")
                .execute()
            println("Hours posted successfully")
            println("*********************************************")
            hoursDto
        } catch (e: Exception) {
            println("Error posting hours: $e")
            if (
                e is GoogleJsonResponseException
                && e.statusCode == 400
            ) {
                println("Creating new sheet tab: $dateRange")
                try {
                    val newSheet = createNewSheetsTab(dateRange)

                    println("New sheet created: $newSheet")

                    sheetsService.spreadsheets().values()
                        .append(spreadsheetId, dateRange, loggedHours)
                        .setValueInputOption("RAW")
                        .execute()
                    println("Hours posted successfully")
                    hoursDto
                } catch (e: Exception) {
                    println("Error creating sheet tab: $e")
                    throw e
                }
            } else {
                println("Error posting hours: $e")
                throw e
            }
        }
    }

    private fun createNewSheetsTab(dateRange: String): BatchUpdateSpreadsheetResponse {
        println("Creating new sheet tab: $dateRange")
        val addSheetsRequest = AddSheetRequest()
            .setProperties(SheetProperties().setTitle(dateRange))
        val request = Request()
            .setAddSheet(addSheetsRequest)
        val batchUpdateRequest = BatchUpdateSpreadsheetRequest()
            .setRequests(listOf(request))
        val newSheet = sheetsService.spreadsheets()
            .batchUpdate(spreadsheetId, batchUpdateRequest)
            .execute()
        println("New sheet created successfully")
        sheetsService.spreadsheets().values()
            .append(spreadsheetId, dateRange, ValueRange().setValues(listOf(
                listOf("Name", "Type", "Date", "Hours", "Extra Hours", "Reason for Extra Hours", "Late", "Reason for Being Late")
            )))
            .setValueInputOption("RAW")
            .execute()
        println("Sheet tab created successfully with headers")
        return newSheet
    }
}