package com.selsela.takeefapp.ui.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.takeefapp.data.config.repository.ConfigurationsRepository
import com.selsela.takeefapp.utils.LocalData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ConfigurationsRepository
) : ViewModel() {

    fun getConfig() {
        viewModelScope.launch { repository.getConfigurations() }
    }

}
