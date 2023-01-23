package com.selsela.takeefapp.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.takeefapp.data.config.repository.ConfigurationsRepository
import com.selsela.takeefapp.utils.LocalData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val acTypes = LocalData.acTypes
    val services = LocalData.services
    var count = mutableStateOf(0)

    fun increment() {
        count.value++
    }

    fun decrement() {
        if (count.value != 0)
            count.value--
    }

}
