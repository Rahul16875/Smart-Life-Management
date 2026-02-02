package com.example.smartlifemanager.ui.screens

data class LoginUiState(
    val phoneNumber: String = "",
    val otp: String = "",
    val otpSent: Boolean = false,
    val verificationId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToMain: Boolean = false
)
