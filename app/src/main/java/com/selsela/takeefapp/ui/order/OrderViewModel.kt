package com.selsela.takeefapp.ui.order

import androidx.annotation.Keep
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.repository.OrderRepository
import com.selsela.takeefapp.utils.retrofit.model.ErrorsData
import com.selsela.takeefapp.utils.retrofit.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.*
import com.selsela.takeefapp.data.config.model.RateProperitiesSupervisor
import com.selsela.takeefapp.data.config.model.RateProperitiesUser
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.order.rate.Rate
import com.selsela.takeefapp.utils.Constants.FINISHED
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


/**
 * UiState for the Order
 */

enum class OrderState {
    IDLE,
    LOADING,
    PAGINATING,
}

@Keep
data class OrderUiState(
    val state: State = State.IDLE,
    var responseMessage: String? = "",
    var order: Order? = null,
    val onFailure: StateEventWithContent<ErrorsData> = consumed(),
)

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {


    /**
     * Order Pagination Variables
     */

    val orderList = mutableStateListOf<Order>()
    var rateArray = mutableStateListOf<List<Rate>>()
    var note by mutableStateOf("")
    var rateItemArray = mutableStateOf(listOf<RateProperitiesSupervisor>())
    var isDetailsLoaded = false
    var isLoaded = false
    private var page by mutableStateOf(1)
    var canPaginate by mutableStateOf(false)
    var listState by mutableStateOf(OrderState.IDLE)

    /**
     * State Subscribers
     */
    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()


    private var state: OrderUiState
        get() = _uiState.value
        set(newState) {
            _uiState.update { newState }
        }

    fun getRateItem(){
        rateItemArray.value = LocalData.rateItems!!
    }


    fun getNewOrders(caseID: Int) = viewModelScope.launch {
        if (page == 1 || (page != 1 && canPaginate) && listState == OrderState.IDLE) {
            listState = if (page == 1) OrderState.LOADING else OrderState.PAGINATING
            repository.getOrders(page, caseID).collect { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        isLoaded = true
                        canPaginate = result.data?.hasMorePage ?: false
                        if (page == 1) {
                            orderList.clear()
                            result.data?.orders?.let { orderList.addAll(it) }
                        } else {
                            result.data?.orders?.let { orderList.addAll(it) }
                        }
                        listState = OrderState.IDLE
                        if (canPaginate)
                            page++

                    }

                    Status.LOADING ->
                        listState = OrderState.LOADING


                    Status.ERROR -> {
                        OrderUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                ),
                            ),
                        )
                    }
                }
            }
        }
    }

    fun getOrderDetails(id: Int) {
        isDetailsLoaded = false
        viewModelScope.launch {
            state = state.copy(
                state = State.LOADING
            )
            repository.getOrderDetails(id)
                .collect { result ->
                    val orderStateUi = when (result.status) {
                        Status.SUCCESS -> {
                            isDetailsLoaded = true
                            isLoaded = true
                            OrderUiState(
                                order = result.data?.order,
                                state = State.SUCCESS
                            )
                        }

                        Status.LOADING ->
                            OrderUiState(
                                state = State.LOADING
                            )

                        Status.ERROR -> OrderUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            ),
                        )
                    }
                    state = orderStateUi
                }
        }
    }

    fun cancelOrder(id: Int) {
        viewModelScope.launch {
            state = state.copy(
                state = State.LOADING
            )
            repository.cancelOrder(id)
                .collect { result ->
                    val orderStateUi = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            OrderUiState(
                                order = result.data?.order,
                                state = State.SUCCESS
                            )
                        }

                        Status.LOADING ->
                            OrderUiState(
                                state = State.LOADING
                            )

                        Status.ERROR -> OrderUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            ),
                        )
                    }
                    state = orderStateUi
                }
        }
    }

    fun rateOrder(orderId: Int, rateList: List<List<Rate>>, note: String?) {
        rateList.log("rateArray")
        val ratedList = mutableListOf<Any>()
        rateList.map {
            it.map {
                it.id to it.rate
            }
        }.forEach {
            it.forEach {
                ratedList.add(
                    listOf(it.first, it.second)
                )
            }
        }
        rateList.log("ratedList")
        if (note.isNullOrEmpty().not()) {
            viewModelScope.launch {
                state = state.copy(
                    state = State.LOADING
                )
                repository.rateOrder(
                    orderId, ratedList, note
                )
                    .collect { result ->
                        val orderStateUi = when (result.status) {
                            Status.SUCCESS -> {
                                onRefresh(FINISHED)
                                OrderUiState(
                                    order = result.data?.order,
                                    state = State.SUCCESS,
                                    responseMessage = result.data?.responseMessage ?: result.message
                                )
                            }

                            Status.LOADING ->
                                OrderUiState(
                                    state = State.LOADING
                                )

                            Status.ERROR -> OrderUiState(
                                onFailure = triggered(
                                    ErrorsData(
                                        result.errors,
                                        result.message,
                                        result.statusCode
                                    )
                                ),
                            )
                        }
                        state = orderStateUi
                    }
            }
        }


    }
    fun rejectAdditionalCost(orderId: Int) {
        viewModelScope.launch {
            state = state.copy(
                state = State.LOADING
            )
            repository.rejectAdditionalCost(orderId)
                .collect { result ->
                    val orderStateUi = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            OrderUiState(
                                order = result.data?.order,
                                state = State.SUCCESS,
                                responseMessage = result.data?.responseMessage ?: result.message
                            )
                        }

                        Status.LOADING ->
                            OrderUiState(
                                state = State.LOADING
                            )

                        Status.ERROR -> OrderUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            ),
                        )
                    }
                    state = orderStateUi
                }
        }

    }
    fun acceptAdditionalCost(paymentId: Int,orderId: Int,useWallet: Int) {
        viewModelScope.launch {
            state = state.copy(
                state = State.LOADING
            )
            repository.acceptAdditionalCost(orderId,paymentId,useWallet)
                .collect { result ->
                    val orderStateUi = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded = true
                            OrderUiState(
                                order = result.data?.order,
                                state = State.SUCCESS,
                                responseMessage = result.data?.responseMessage ?: result.message
                            )
                        }

                        Status.LOADING ->
                            OrderUiState(
                                state = State.LOADING
                            )

                        Status.ERROR -> OrderUiState(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            ),
                        )
                    }
                    state = orderStateUi
                }
        }

    }

    override fun onCleared() {
        page = 1
        listState = OrderState.IDLE
        canPaginate = false
        super.onCleared()
    }


    fun onRefresh(caseID: Int) {
        page = 1
        listState = OrderState.IDLE
        canPaginate = false
        getNewOrders(caseID)
    }

    fun onSuccess() {
        state = state.copy()
    }

    fun onFailure() {
        state = state.copy(onFailure = consumed())
    }
}