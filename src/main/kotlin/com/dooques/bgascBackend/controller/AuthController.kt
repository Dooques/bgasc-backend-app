package com.dooques.bgascBackend.controller

import com.dooques.bgascBackend.data.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    init {
        println("***************************************")
        println("***   Auth Controller Initialized   ***")
        println("***************************************")
    }

    @GetMapping
    fun verifyGoogleUser(
        @RequestParam userId: String
    ) = authService.verifyGoogleUser(userId)
}