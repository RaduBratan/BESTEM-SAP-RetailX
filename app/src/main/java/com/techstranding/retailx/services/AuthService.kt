package com.techstranding.retailx.services

import android.annotation.SuppressLint
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val userService: UserService,
) {
    private val auth = Firebase.auth

    @SuppressLint("RestrictedApi")
    suspend fun enterAccount(
        email: String,
        password: String,
    ): AuthResult {
        val authResult = auth.signInWithEmailAndPassword(
            email,
            password
        ).await()
        val userId = authResult.user?.uid

        userId?.let {
            userService.fetchUser()
        }

        return authResult
    }

    @SuppressLint("RestrictedApi")
    fun exitAccount() {
        auth.signOut()
        userService.removeUser()
    }
}