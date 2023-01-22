package com.selsela.takeefapp.ui.auth

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.jobsapp.utils.validatePhone
import com.selsela.jobsapp.utils.validateRequired
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.auth.model.auth.User
import com.selsela.takeefapp.data.auth.model.wallet.WalletResponse
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.retrofit.model.ErrorsData
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
import java.io.File
import javax.inject.Inject

/**
 * UiState for the Auth
 */
data class AuthUiState(
    val responseMessage: String = "",
    val user: User? = LocalData.user,
    val onSuccess: StateEventWithContent<String> = consumed(),
    val isLoading: Boolean = false,
    val onFailure: StateEventWithContent<ErrorsData> = consumed(),
)

data class WalletUiState(
    val responseMessage: String = "",
    val wallet: WalletResponse? = LocalData.userWallet,
    val onSuccess: StateEvent = consumed,
    val isLoading: Boolean = false,
    val onFailure: StateEventWithContent<ErrorsData> = consumed(),
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application,
    private val repository: AuthRepository
) : ViewModel() {

    /**
     * Validation Variables
     */
    var mobile: MutableState<String> = mutableStateOf(LocalData.user?.mobile ?: "")
    var name: MutableState<String> = mutableStateOf(LocalData.user?.name ?: "")
    var email: MutableState<String> = mutableStateOf(LocalData.user?.email ?: "")
    var code: MutableState<String> = mutableStateOf("")
    var errorMessage: MutableState<String> = mutableStateOf("")
    var errorMessageName: MutableState<String> = mutableStateOf("")
    var isValid: MutableState<Boolean> = mutableStateOf(true)
    var isNameValid: MutableState<Boolean> = mutableStateOf(true)
    var avatar: File? = null
    var isLoaded = false
    val userLoggedIn = mutableStateOf(LocalData.accessToken.isEmpty().not())

    /**
     * State Subscribers
     */
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    private val _walletUiState = MutableStateFlow(WalletUiState())
    val walletUiState: StateFlow<WalletUiState> = _walletUiState.asStateFlow()

    private var state: AuthUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }
    private var walletState: WalletUiState
        get() = _walletUiState.value
        set(newState) {
            _walletUiState.update { newState }
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

    private fun isCodeValid(): Boolean {
        code.value.log("code.value")
        val message = code.value.validateRequired(
            application.applicationContext, application.getString(
                R.string.verify_code
            )
        )
        if (message == "") {
            if (code.value != LocalData.user?.activationCode) {
                isValid.value = false
                errorMessage.value = application.getString(
                    R.string.unvalid_code
                )
            } else isValid.value = true
        } else {
            isValid.value = false
            errorMessage.value = message
        }
        return isValid.value
    }

    private fun profileInfoIsValid(): Boolean {
        code.value.log("code.value")
        val nameValidationMessage = name.value.validateRequired(
            application.applicationContext, application.getString(
                R.string.name
            )
        )
        val mobileValidationMessage = mobile.value.validateRequired(
            application.applicationContext, application.getString(
                R.string.mobile
            )
        )
        if (nameValidationMessage == "" && mobileValidationMessage == "") {
            isNameValid.value = true
            isValid.value = true
        } else {
            if (nameValidationMessage != "" && mobileValidationMessage != "") {
                isValid.value = false
                isNameValid.value = false
                errorMessage.value = "${mobileValidationMessage}"
                errorMessageName.value = "${nameValidationMessage}"
            } else {
                if (nameValidationMessage != "") {
                    errorMessageName.value = nameValidationMessage
                    errorMessage.value = ""
                    isValid.value = true
                    isNameValid.value = false
                } else {
                    errorMessage.value = mobileValidationMessage
                    errorMessageName.value = ""
                    isValid.value = false
                    isNameValid.value = true
                }
            }
        }
        return isValid.value && isNameValid.value
    }

    fun validateBorderColor(): Color {
        return if (errorMessage.value.isNotEmpty() && isValid.value.not())
            Red
        else BorderColor
    }

    fun validateNameBorderColor(): Color {
        return if (errorMessage.value.isNotEmpty() && isNameValid.value.not())
            Red
        else BorderColor
    }

    /////////////////////////////////////////  API REQUESTS  ///////////////////////////////////////

    /**
     * API Requests
     */
    fun auth() {
        if (isMobileValid()) {
            viewModelScope.launch {
                state = state.copy(
                    isLoading = true
                )
                repository.auth(mobile.value)
                    .collect { result ->
                        val authUiState = when (result.status) {
                            Status.SUCCESS -> {
                                AuthUiState(
                                    responseMessage = result.message ?: "",
                                    onSuccess = triggered(result.data?.status ?: ""),
                                )
                            }

                            Status.LOADING ->
                                AuthUiState(
                                    isLoading = true
                                )

                            Status.ERROR -> AuthUiState(
                                onFailure = triggered(
                                    ErrorsData(
                                        result.errors,
                                        result.message,
                                    )
                                ),
                                responseMessage = result.message ?: "",
                            )
                        }
                        state = authUiState
                    }
            }
        }
    }

    fun verifyCode() {
        if (isCodeValid()) {
            viewModelScope.launch {
                state = state.copy(
                    isLoading = true
                )
                repository.verifyCode(code = code.value)
                    .collect { result ->
                        val authUiState = when (result.status) {
                            Status.SUCCESS -> {
                                AuthUiState(
                                    responseMessage = result.message ?: "",
                                    onSuccess = triggered(result.data?.status ?: ""),
                                )
                            }

                            Status.LOADING ->
                                AuthUiState(
                                    isLoading = true
                                )

                            Status.ERROR -> AuthUiState(
                                onFailure = triggered(
                                    ErrorsData(
                                        result.errors,
                                        result.message,
                                    )
                                ),
                                responseMessage = result.message ?: "",
                            )
                        }
                        state = authUiState
                    }
            }
        }
    }

    fun me() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.me()
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            AuthUiState(
                                responseMessage = result.message ?: "",
                                onSuccess = triggered(result.data?.status ?: ""),
                                user = result.data
                            )
                        }

                        Status.LOADING ->
                            AuthUiState(
                                isLoading = true
                            )

                        Status.ERROR -> AuthUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                )
                            ),
                            responseMessage = result.message ?: "",
                        )
                    }
                    state = authUiState
                }
        }
    }

    fun updateFcm() {
        viewModelScope.launch {
            repository.updateFCM()
        }
    }

    fun updateProfile() {
        if (profileInfoIsValid()) {
            viewModelScope.launch {
                state = state.copy(
                    isLoading = true
                )
                repository.updateProfile(
                    avatar = avatar,
                    name   = name.value,
                    email  = email.value,
                    mobile = mobile.value
                )
                    .collect { result ->
                        val authUiState = when (result.status) {
                            Status.SUCCESS -> {
                                AuthUiState(
                                    responseMessage = result.message ?: "",
                                    onSuccess = triggered(result.message ?: ""),
                                )
                            }

                            Status.LOADING ->
                                AuthUiState(
                                    isLoading = true
                                )

                            Status.ERROR -> AuthUiState(
                                onFailure = triggered(
                                    ErrorsData(
                                        result.errors,
                                        result.message,
                                    )
                                ),
                                responseMessage = result.message ?: "",
                            )
                        }
                        state = authUiState
                    }
            }
        }
    }

    fun wallet() {
        viewModelScope.launch {
            walletState = walletState.copy(
                isLoading = true
            )
            repository.getWallet()
                .collect { result ->
                    val walletUiState = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            WalletUiState(
                                responseMessage = result.message ?: "",
                                onSuccess = triggered,
                                wallet = result.data
                            )
                        }

                        Status.LOADING ->
                            WalletUiState(
                                isLoading = true
                            )

                        Status.ERROR ->
                            WalletUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                )
                            ),
                            responseMessage = result.message ?: "",
                        )
                    }
                    walletState = walletUiState
                }
        }
    }

    /**
     * reset handlers
     */
    fun onSuccess() {
        state = state.copy(onSuccess = consumed())
        walletState = walletState.copy(onSuccess = consumed)
    }

    fun onFailure() {
        state = state.copy(onFailure = consumed())
        walletState = walletState.copy(onFailure = consumed())
    }

}
