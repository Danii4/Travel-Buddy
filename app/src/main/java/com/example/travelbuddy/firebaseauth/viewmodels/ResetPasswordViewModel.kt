package com.example.travelbuddy.firebaseauth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddy.domain.model.Response
import com.example.travelbuddy.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ResetPasswordState(
    val loading: Boolean = false,
    val success: String? = "",
    val error: String? = ""
)

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {

    private val _resetPasswordState = Channel<ResetPasswordState>()
    val resetPasswordState = _resetPasswordState.receiveAsFlow()
    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        _resetPasswordState.send(ResetPasswordState(loading = true))
        repo.sendPasswordResetEmail(email).collect {
            when(it) {
                is Response.Failure -> _resetPasswordState.send(
                    ResetPasswordState(error = it.msg, loading = false, success = "")
                )
                is Response.Loading -> _resetPasswordState.send(
                    ResetPasswordState(error = "", loading = true, success = "")
                )
                is Response.Success -> _resetPasswordState.send(
                    ResetPasswordState(error = "", loading = false, success = "Sent email Successfully")
                )
            }
        }
    }
}
