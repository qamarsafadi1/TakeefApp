package com.selsela.takeefapp.ui.auth

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.jobsapp.utils.validatePhone
import com.selsela.takeefapp.data.auth.model.auth.User
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.utils.retrofit.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UiState for the Login
 */
data class LoginUiState(
    val responseMessage: String = "",
    val user: User? = null,
    val onSuccess: StateEvent = consumed,
    val isLoading: Boolean = false,
    val onFailure: StateEventWithContent<String> = consumed(),
)


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application,
    private val repository: AuthRepository
) : ViewModel() {
    var mobile: MutableState<String> = mutableStateOf("")
    var errorMessage: MutableState<String> = mutableStateOf("")
    var isValid: MutableState<Boolean> = mutableStateOf(true)
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private var state: LoginUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }

    /**
     * Form Validation
     */
    private fun isMobileValid(): Boolean {
        val message = mobile.value.validatePhone(application.applicationContext)
        if (message == "") {
            isValid.value = true
        } else {
            isValid.value = false
            errorMessage.value = message
        }
        return isValid.value
    }

    fun validateBorderColor(): Color {
        return if (errorMessage.value.isNotEmpty() && isValid.value.not())
            Red
        else BorderColor
    }

    fun auth() {
        if (isMobileValid()) {
            viewModelScope.launch {
                state = state.copy(
                    isLoading = true
                )
                repository.auth(mobile.value)
                    .collect { result ->
                        val loginUiState = when (result.status) {
                            Status.SUCCESS -> {
                                LoginUiState(
                                    responseMessage = result.message ?: "",
                                    onSuccess = triggered,
                                )
                            }

                            Status.LOADING ->
                                LoginUiState(
                                    isLoading = true
                                )

                            Status.ERROR -> LoginUiState(
                                onFailure = triggered(result.message ?: ""),
                                responseMessage = result.message ?: "",
                            )
                        }
                        state = loginUiState
                    }
            }
        }
    }

    fun updateFcm() {
        viewModelScope.launch {
            repository.updateFCM()
        }
    }


    /**
     * reset handlers
     */
    fun onSuccess() {
        state = state.copy(onSuccess = consumed)
    }

    fun onFailure() {
        state = state.copy(onFailure = consumed())
    }

}
