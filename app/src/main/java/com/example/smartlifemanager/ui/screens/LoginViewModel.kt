package com.example.smartlifemanager.ui.screens

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlifemanager.data.preferences.AuthPreferences
import com.example.smartlifemanager.domain.usecase.UseCaseResult
import com.example.smartlifemanager.domain.usecase.auth.SendPhoneOtpUseCase
import com.example.smartlifemanager.domain.usecase.auth.VerifyPhoneOtpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sendOtpUseCase: SendPhoneOtpUseCase = SendPhoneOtpUseCase(),
    private val verifyOtpUseCase: VerifyPhoneOtpUseCase = VerifyPhoneOtpUseCase(),
    private val authPreferences: AuthPreferences? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updatePhoneNumber(value: String) {
        _uiState.update { it.copy(phoneNumber = value, errorMessage = null) }
    }

    fun updateOtp(value: String) {
        _uiState.update { it.copy(otp = value, errorMessage = null) }
    }

    fun sendOtp(activity: Activity) {
        val phone = _uiState.value.phoneNumber
        if (phone.length != 10) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = sendOtpUseCase("+91$phone", activity)) {
                is UseCaseResult.Success -> {
                    if (result.data.isEmpty()) {
                        _uiState.update { it.copy(isLoading = false, navigateToMain = true) }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                otpSent = true,
                                verificationId = result.data
                            )
                        }
                    }
                }
                is UseCaseResult.Error -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }
        }
    }

    fun verifyOtp() {
        val state = _uiState.value
        if (state.otp.length != 6 || state.verificationId.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = verifyOtpUseCase(state.verificationId, state.otp)) {
                is UseCaseResult.Success -> _uiState.update {
                    it.copy(isLoading = false, navigateToMain = true)
                }
                is UseCaseResult.Error -> _uiState.update {
                    it.copy(isLoading = false, errorMessage = result.message)
                }
            }
        }
    }

    fun changeNumber() {
        _uiState.update {
            it.copy(otpSent = false, otp = "", verificationId = "", errorMessage = null)
        }
    }

    fun resendOtp(activity: Activity) {
        sendOtp(activity)
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun onNavigateToMainConsumed() {
        _uiState.update { it.copy(navigateToMain = false) }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            authPreferences?.setGuest(true)
            _uiState.update { it.copy(navigateToMain = true) }
        }
    }
}
