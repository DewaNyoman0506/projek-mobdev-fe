package com.leksin.app.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leksin.app.ui.components.LeksinPillButton
import com.leksin.app.ui.screens.child.ChildMainScreen
import com.leksin.app.ui.screens.parent.ParentMainScreen
import com.leksin.app.ui.theme.LeksinPrimary
import com.leksin.app.ui.theme.Spacing

@Composable
fun AuthFlow(
    modifier: Modifier = Modifier,
    onAuthSuccess: ((role: String, username: String) -> Unit)? = null
) {
    val context = LocalContext.current.applicationContext
    val viewModel: AuthViewModel = viewModel(
        factory = remember {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return AuthViewModel(context) as T
                }
            }
        }
    )

    when (val state = viewModel.currentScreen) {
        is AuthScreenState.RoleSelection -> {
            RoleSelectionScreen(
                modifier = modifier,
                onRoleSelected = { role ->
                    when (role) {
                        UserRole.PARENT -> viewModel.navigateTo(AuthScreenState.ParentLogin)
                        UserRole.CHILD -> viewModel.navigateTo(AuthScreenState.ChildOtp)
                    }
                }
            )
        }

        is AuthScreenState.ParentLogin -> {
            ParentLoginScreen(
                modifier = modifier,
                isLoading = viewModel.isLoading,
                errorMessage = viewModel.errorMessage,
                onBackClick = { viewModel.navigateTo(AuthScreenState.RoleSelection) },
                onNavigateToRegister = { viewModel.navigateTo(AuthScreenState.ParentRegister) },
                onLoginClick = { username, password ->
                    viewModel.loginParent(username, password)
                }
            )
        }

        is AuthScreenState.ParentRegister -> {
            ParentRegisterScreen(
                modifier = modifier,
                isLoading = viewModel.isLoading,
                errorMessage = viewModel.errorMessage,
                onBackClick = { viewModel.navigateTo(AuthScreenState.ParentLogin) },
                onNavigateToLogin = { viewModel.navigateTo(AuthScreenState.ParentLogin) },
                onRegisterClick = { username, email, password ->
                    viewModel.registerParent(username, email, password)
                }
            )
        }

        is AuthScreenState.ChildOtp -> {
            ChildOtpScreen(
                modifier = modifier,
                isLoading = viewModel.isLoading,
                otpSent = viewModel.otpSent,
                errorMessage = viewModel.errorMessage,
                onBackClick = { viewModel.navigateTo(AuthScreenState.RoleSelection) },
                onRequestOtpClick = { parentUsername, nickname ->
                    viewModel.requestChildOtp(parentUsername, nickname)
                },
                onVerifyOtpClick = { parentUsername, nickname, code ->
                    viewModel.verifyChildOtp(parentUsername, nickname, code)
                }
            )
        }

        is AuthScreenState.Success -> {
            onAuthSuccess?.invoke(state.role, state.username)

            if (state.role.equals("CHILD", ignoreCase = true)) {
                ChildMainScreen(
                    modifier = modifier,
                    onStartGameSession = {
                        // TODO: Handle start game session
                    }
                )
            } else {
                ParentMainScreen(modifier = modifier)
            }
        }
    }
}
