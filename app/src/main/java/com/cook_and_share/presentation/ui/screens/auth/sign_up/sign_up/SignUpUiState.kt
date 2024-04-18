package com.cook_and_share.presentation.ui.screens.auth.sign_up.sign_up

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)