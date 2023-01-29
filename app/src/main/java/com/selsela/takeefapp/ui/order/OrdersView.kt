package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.annotations.Until
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.common.components.EmptyView
import com.selsela.takeefapp.ui.common.components.LoadingView
import com.selsela.takeefapp.ui.order.item.OrderItem
import com.selsela.takeefapp.ui.order.rate.RateSheet
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.showSuccess
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrdersView(
    caseID: Int,
    viewModel: OrderViewModel = hiltViewModel(),
    onBack: () -> Unit,
    goToDetails: (Int) -> Unit,
    goToOrderRoute: () -> Unit
) {
    val lazyColumnListState = rememberLazyListState()
    val rateSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val viewState: OrderUiState by viewModel.uiState.collectAsStateLifecycleAware(
        OrderUiState()
    )
    val coroutineScope = rememberCoroutineScope()
    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }
    val orders = viewModel.orderList

    OrderListContent(
        caseID,
        viewModel,
        lazyColumnListState = lazyColumnListState,
        orders,
        onBack,
        goToDetails, goToOrderRoute
    ) {
        viewState.order = Order(id = it)
        coroutineScope.launch {
            if (rateSheetState.isVisible)
                rateSheetState.hide()
            else rateSheetState.animateTo(ModalBottomSheetValue.Expanded)
        }
    }

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.getNewOrders(caseID)
    }
    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == OrderState.IDLE)
            viewModel.getNewOrders(caseID)
    }
    RateSheet(
        rateSheetState,
        viewState,
        onConfirm = viewModel::rateOrder
    )
    when (viewState.state) {
        State.SUCCESS -> {
            if (viewState.responseMessage.isNullOrEmpty().not()) {
                LocalContext.current.showSuccess(
                    viewState.responseMessage ?: ""
                )
                LaunchedEffect(key1 = Unit){
                    coroutineScope.launch {
                        if (rateSheetState.isVisible)
                            rateSheetState.hide()
                        else rateSheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }
                viewState.responseMessage = ""
                viewModel.onRefresh(caseID)
            }
        }
        else -> {}
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OrderListContent(
    caseID: Int,
    viewModel: OrderViewModel,
    lazyColumnListState: LazyListState,
    orders: List<Order>,
    onBack: () -> Unit,
    goToDetails: (Int) -> Unit,
    goToOrderRoute: () -> Unit,
    onRateClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
      Column(Modifier.fillMaxSize()) {
          Header(caseID) {
              onBack()
          }
          Box(modifier = Modifier.fillMaxSize()
              .padding(horizontal = 19.dp)
          ){
              when (viewModel.listState) {
                  OrderState.LOADING -> {
                      LoadingView()
                  }

                  OrderState.IDLE, OrderState.PAGINATING -> {
                      OrderList(
                          lazyColumnListState, orders, goToDetails, goToOrderRoute,
                          onRateClick
                      )
                  }

                  else -> {}
              }
          }
      }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OrderList(
    lazyColumnListState: LazyListState,
    orders: List<Order>,
    goToDetails: (Int) -> Unit,
    goToOrderRoute: () -> Unit,
    onRateClick: (Int) -> Unit
) {
    if (orders.isEmpty().not()) {
        LazyColumn(
            state = lazyColumnListState,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            items(orders) {
                OrderItem(
                    it,
                    onClick = { goToDetails(it) },
                    onRateClick
                ) {
                    goToOrderRoute()
                }
            }
        }
    } else {
        EmptyView(
            stringResource(R.string.no_orders),
            stringResource(R.string.no_orders_lbl),
            backgroundColor = Bg
        )
    }
}


@Composable
private fun Header(
    caseID: Int,
    onBack: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .requiredHeight(88.dp)
            .background(Color.White)
            .padding(top = 30.dp)
            .padding(horizontal = 6.dp),
    ) {

        IconButton(
            onClick = { onBack() },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backbutton),
                contentDescription = ""
            )
        }
        Text(
            text =
            when (caseID) {
                Constants.NEW_ORDER ->
                    stringResource(id = R.string.new_orders)
                Constants.UPCOMING_ORDERS -> stringResource(R.string.ongoing_order)
                else -> stringResource(R.string.archive)
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = text14Meduim
        )
    }
}