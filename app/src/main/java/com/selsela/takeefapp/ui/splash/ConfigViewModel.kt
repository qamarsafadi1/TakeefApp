package com.selsela.takeefapp.ui.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.data.config.model.page.Page
import com.selsela.takeefapp.data.config.repository.ConfigurationsRepository
import com.selsela.takeefapp.ui.auth.AuthUiState
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
import javax.inject.Inject


data class GeneralUiState(
    val responseMessage: String = "",
    val term: Page? = LocalData.terms,
    val aboutApp: Page? = LocalData.aboutApp,
    val onSuccess: StateEvent = consumed,
    val isLoading: Boolean = false,
    val onFailure: StateEventWithContent<ErrorsData> = consumed(),
)

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ConfigurationsRepository,
    private val repositoryAuth: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GeneralUiState())
    val uiState: StateFlow<GeneralUiState> = _uiState.asStateFlow()
    var isLoaded = false
    private var state: GeneralUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }

    fun getConfig() {
        viewModelScope.launch {
            repository.getConfigurations()
            repository.getTerms()
            repository.getAboutApp()
            repository.getCities()
            if (LocalData.accessToken.isNullOrEmpty().not())
                repositoryAuth.getWallet()
        }
    }

    fun getCities() {
        viewModelScope.launch {
            repository.getCities()
        }
    }

    fun getAboutApp() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.getAboutApp()
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            GeneralUiState(
                                responseMessage = result.message ?: "",
                                onSuccess = triggered,
                                aboutApp = result.data
                            )
                        }

                        Status.LOADING ->
                            GeneralUiState(
                                isLoading = true
                            )

                        Status.ERROR -> GeneralUiState(
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

    fun getTerms() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.getTerms()
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            GeneralUiState(
                                responseMessage = result.message ?: "",
                                onSuccess = triggered,
                                term = result.data
                            )
                        }

                        Status.LOADING ->
                            GeneralUiState(
                                isLoading = true
                            )

                        Status.ERROR -> GeneralUiState(
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
