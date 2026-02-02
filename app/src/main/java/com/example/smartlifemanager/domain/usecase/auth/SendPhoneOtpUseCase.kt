package com.example.smartlifemanager.domain.usecase.auth

import android.app.Activity
import com.example.smartlifemanager.domain.usecase.UseCaseResult
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume

class SendPhoneOtpUseCase(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    suspend operator fun invoke(
        fullPhoneNumber: String,
        activity: Activity
    ): UseCaseResult<String> = suspendCancellableCoroutine { cont ->
        val resumed = AtomicBoolean(false)
        fun tryResume(result: UseCaseResult<String>) {
            if (resumed.compareAndSet(false, true)) cont.resume(result)
        }

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential)
                    .addOnSuccessListener { tryResume(UseCaseResult.Success("")) }
                    .addOnFailureListener { e ->
                        tryResume(UseCaseResult.Error(e.message ?: "Verification failed"))
                    }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                tryResume(UseCaseResult.Error(e.message ?: "Verification failed"))
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                tryResume(UseCaseResult.Success(verificationId))
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(fullPhoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
