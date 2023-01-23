package com.selsela.takeefapp.ui.order

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.jobsapp.utils.validateRequired
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.auth.model.auth.User
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.model.special.SpecificOrder
import com.selsela.takeefapp.data.order.repository.OrderRepository
import com.selsela.takeefapp.data.order.repository.SpecialOrderRepository
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.auth.NotificationUiState
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.Red
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
import androidx.compose.runtime.*
import com.selsela.takeefapp.ui.order.special.SpecialOrderUiState


/**
 * UiState for the Order
 */

enum class OrderState {
    IDLE,
    LOADING,
    PAGINATING,
    ERROR,
}

data class OrderUiState(
    val onSuccess: StateEvent = consumed,
    val orderState: OrderState,
    val isLoading: Boolean = false,
    val onFailure: StateEventWithContent<ErrorsData> = consumed(),
)


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    val newsList = mutableStateListOf<Order>()
    var isLoaded = false
    private var page by mutableStateOf(1)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(OrderState.IDLE)

    fun getNewOrders() = viewModelScope.launch {
        if (page == 1 || (page != 1 && canPaginate) && listState == OrderState.IDLE) {
            listState = if (page == 1) OrderState.LOADING else OrderState.PAGINATING
            repository.getOrders(page).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        isLoaded = true
                        canPaginate = result.data?.hasMorePage ?: false
                        if (page == 1) {
                            newsList.clear()
                            result.data?.orders?.let { newsList.addAll(it) }
                        } else {
                            result.data?.orders?.let { newsList.addAll(it) }
                        }
                        listState = OrderState.IDLE
                        if (canPaginate)
                            page++
                        OrderUiState(
                            onSuccess = triggered,
                            orderState = OrderState.PAGINATING,
                        )
                    }

                    Status.LOADING ->
                        OrderUiState(
                            isLoading = true,
                            orderState = OrderState.LOADING
                        )

                    Status.ERROR -> {
                         OrderUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                ),
                            ),
                            orderState = OrderState.ERROR
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        page = 1
        listState = OrderState.IDLE
        canPaginate = false
        super.onCleared()
    }


//    fun onSuccess() {
//        state = state.copy(onSuccess = consumed)
//    }
//
//    fun onFailure() {
//        state = state.copy(onFailure = consumed())
//    }
}