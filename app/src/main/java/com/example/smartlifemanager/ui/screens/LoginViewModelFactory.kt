package com.example.smartlifemanager.ui.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartlifemanager.data.preferences.AuthPreferences
import com.example.smartlifemanager.domain.usecase.auth.SendPhoneOtpUseCase
import com.example.smartlifemanager.domain.usecase.auth.VerifyPhoneOtpUseCase

class LoginViewModelFactory(
    private val context: Context,
    private val sendOtpUseCase: SendPhoneOtpUseCase = SendPhoneOtpUseCase(),
    private val verifyOtpUseCase: VerifyPhoneOtpUseCase = VerifyPhoneOtpUseCase()
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val authPreferences = AuthPreferences(context.applicationContext)
            return LoginViewModel(sendOtpUseCase, verifyOtpUseCase, authPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
