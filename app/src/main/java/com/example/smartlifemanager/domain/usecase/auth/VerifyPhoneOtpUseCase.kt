package com.example.smartlifemanager.domain.usecase.auth

import com.example.smartlifemanager.domain.usecase.UseCaseResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.tasks.await

class VerifyPhoneOtpUseCase(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    suspend operator fun invoke(verificationId: String, otp: String): UseCaseResult<Unit> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            auth.signInWithCredential(credential).await()
            UseCaseResult.Success(Unit)
        } catch (e: Exception) {
            UseCaseResult.Error(e.message ?: "Verification failed")
        }
    }
}
