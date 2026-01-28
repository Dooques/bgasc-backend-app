package com.dooques.bgascBackend.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService {
    fun verifyGoogleUser(idToken: String): FirebaseToken {
        return try {
            FirebaseAuth.getInstance().verifyIdToken(idToken)
        } catch (e: FirebaseAuthException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token: $e")
        }
    }
}