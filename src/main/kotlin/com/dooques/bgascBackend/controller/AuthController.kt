package com.dooques.bgascBackend.controller

import com.dooques.bgascBackend.data.service.AuthService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    init {
        println("***************************************")
        println("***  Auth Controller Initialized   ***")
        println("***************************************")
    }

    @RequestMapping("/google")
    fun verifyGoogleUser(idToken: String) = authService.verifyGoogleUser(idToken)
}