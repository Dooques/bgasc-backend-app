package com.dooques.bgascBackend

import com.dooques.bgascBackend.config.GoogleSheetsConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BgascBackendApplication

fun main(args: Array<String>) {
	runApplication<BgascBackendApplication>(*args)
}
