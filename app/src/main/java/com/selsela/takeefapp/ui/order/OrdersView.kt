package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.common.components.EmptyView
import com.selsela.takeefapp.ui.order.item.OrderItem
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.Shimmer

@Composable
fun OrdersView(
    viewModel: OrderViewModel = hiltViewModel(),
    goToDetails: () -> Unit,
    goToOrderRoute: () -> Unit
) {
    val lazyColumnListState = rememberLazyListState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            viewModel.canPaginate && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }
    val orders = viewModel.newsList

    OrderListContent(
        viewModel,
        lazyColumnListState = lazyColumnListState,
        orders,
        goToDetails, goToOrderRoute
    )

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.getNewOrders()
    }
    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value && viewModel.listState == OrderState.IDLE)
            viewModel.getNewOrders()
    }
}

@Composable
private fun OrderListContent(
    viewModel: OrderViewModel,
    lazyColumnListState: LazyListState,
    orders: List<Order>,
    goToDetails: () -> Unit,
    goToOrderRoute: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(horizontal = 19.dp)
    ) {
        when (viewModel.listState) {
            OrderState.LOADING -> {
                LoadingView()
            }
            OrderState.IDLE, OrderState.PAGINATING -> {
                OrderList(lazyColumnListState, orders, goToDetails, goToOrderRoute)
            }

            else -> {}
        }
    }
}

@Composable
private fun OrderList(
    lazyColumnListState: LazyListState,
    orders: List<Order>,
    goToDetails: () -> Unit,
    goToOrderRoute: () -> Unit
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
                    onClick = { goToDetails() }
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
private fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(50.dp)
                .background(Shimmer, RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimationView(raw = R.raw.whiteloading)
        }
    }
}
