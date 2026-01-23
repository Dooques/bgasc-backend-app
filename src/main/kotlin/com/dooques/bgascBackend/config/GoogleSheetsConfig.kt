package com.dooques.bgascBackend.config

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.FileNotFoundException


@Configuration
class GoogleSheetsConfig {

    @Value($$"${google.spreadsheet.application-name:bgasc}")
    private lateinit var applicationName: String

    @Bean
    fun googleSheetsService(): Sheets {
        val resource = ClassPathResource("credentials.json")

        if (!resource.exists()) {
            throw IllegalStateException("credentials.json not found in resource folder")
        }

        val credentials = GoogleCredentials.fromStream(resource.inputStream)
            .createScoped(listOf(SheetsScopes.SPREADSHEETS))

        return Sheets.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            HttpCredentialsAdapter(credentials)
        )
            .setApplicationName(applicationName)
            .build()
    }
}