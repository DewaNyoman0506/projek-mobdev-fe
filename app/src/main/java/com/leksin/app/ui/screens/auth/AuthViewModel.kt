package com.leksin.app.ui.screens.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leksin.app.data.remote.NetworkResult
import com.leksin.app.data.repository.LeksinRepository
import kotlinx.coroutines.launch

sealed class AuthScreenState {
    object RoleSelection : AuthScreenState()
    object ParentLogin : AuthScreenState()
    object ParentRegister : AuthScreenState()
    object ChildOtp : AuthScreenState()
    data class Success(val role: String, val username: String) : AuthScreenState()
}

class AuthViewModel(context: Context) : ViewModel() {

    private val repository = LeksinRepository(context)

    var currentScreen by mutableStateOf<AuthScreenState>(AuthScreenState.RoleSelection)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var otpSent by mutableStateOf(false)
        private set

    fun navigateTo(screen: AuthScreenState) {
        errorMessage = null
        currentScreen = screen
    }

    fun loginParent(username: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val result = repository.loginParent(username, password)) {
                is NetworkResult.Success -> {
                    isLoading = false
                    currentScreen = AuthScreenState.Success(
                        role = result.data.role,
                        username = result.data.username
                    )
                }
                is NetworkResult.Error -> {
                    isLoading = false
                    errorMessage = result.problemDetail?.detail ?: result.message
                }
                is NetworkResult.Exception -> {
                    isLoading = false
                    errorMessage = result.throwable.message ?: "Terjadi kesalahan jaringan"
                }
            }
        }
    }

    fun registerParent(username: String, email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val result = repository.registerParent(username, password, email)) {
                is NetworkResult.Success -> {
                    isLoading = false
                    // Auto login after successful registration
                    loginParent(username, password)
                }
                is NetworkResult.Error -> {
                    isLoading = false
                    errorMessage = result.problemDetail?.detail ?: result.message
                }
                is NetworkResult.Exception -> {
                    isLoading = false
                    errorMessage = result.throwable.message ?: "Terjadi kesalahan jaringan"
                }
            }
        }
    }

    fun requestChildOtp(parentUsername: String, nickname: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val result = repository.requestChildOtp(parentUsername, nickname)) {
                is NetworkResult.Success -> {
                    isLoading = false
                    otpSent = true
                }
                is NetworkResult.Error -> {
                    isLoading = false
                    errorMessage = result.problemDetail?.detail ?: result.message
                }
                is NetworkResult.Exception -> {
                    isLoading = false
                    errorMessage = result.throwable.message ?: "Terjadi kesalahan jaringan"
                }
            }
        }
    }

    fun verifyChildOtp(parentUsername: String, nickname: String, code: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val result = repository.verifyChildOtp(parentUsername, nickname, code)) {
                is NetworkResult.Success -> {
                    isLoading = false
                    currentScreen = AuthScreenState.Success(
                        role = result.data.role,
                        username = result.data.username
                    )
                }
                is NetworkResult.Error -> {
                    isLoading = false
                    errorMessage = result.problemDetail?.detail ?: result.message
                }
                is NetworkResult.Exception -> {
                    isLoading = false
                    errorMessage = result.throwable.message ?: "Terjadi kesalahan jaringan"
                }
            }
        }
    }

    fun clearError() {
        errorMessage = null
    }

    fun logout() {
        repository.logout()
    }
}
