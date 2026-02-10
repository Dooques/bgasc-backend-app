package com.dooques.bgascBackend.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService {
    fun verifyGoogleUser(userToken: String): FirebaseToken {
        println("Verifying Google User: $userToken")
        return try {
            val idToken = FirebaseAuth.getInstance().verifyIdToken(userToken)
            println("Successfully verified ID token for ${idToken.uid}")
            idToken
        } catch (e: FirebaseAuthException) {
            println("Error verifying Firebase ID token: $e")
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid token: $e"
            )
        }
    }
}