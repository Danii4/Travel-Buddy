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

data class LoginState(
    val loading: Boolean = false,
    val success: String? = null,
    val error: String? = null
)

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {

    private val _logInState = Channel<LoginState>()
    val logInState = _logInState.receiveAsFlow()
    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _logInState.send(LoginState(loading = true))
        repo.login(email, password).collect {
            when(it) {
                is Response.Failure -> _logInState.send(
                    LoginState(error = it.msg.toString(), loading = false, success = null)
                )
                is Response.Loading -> _logInState.send(
                    LoginState(error = null, loading = true, success = null)
                )
                is Response.Success -> _logInState.send(
                    LoginState(error = null, loading = false, success = "Log-in Successfully")
                )
            }
        }
    }
}
