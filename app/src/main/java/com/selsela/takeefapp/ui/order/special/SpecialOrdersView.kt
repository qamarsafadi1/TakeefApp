package com.selsela.takeefapp.ui.order.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.ui.common.components.EmptyView
import com.selsela.takeefapp.ui.order.item.SpecialOrderItem
import com.selsela.takeefapp.ui.order.loading.SpecialOrderLoadingView
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import de.palm.composestateevents.EventEffect

@Preview
@Composable
fun SpecialOrders(
    viewModel: SpecialOrderViewModel = hiltViewModel(),
    goToDetails: () -> Unit
) {
    val context = LocalContext.current
    val viewState: SpecialOrderUiState by viewModel.uiState.collectAsStateLifecycleAware(
        SpecialOrderUiState()
    )
    SpecialOrdersContent(
        viewState,
        goToDetails
    )

    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.getSpecialOrders()
    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = viewModel::onFailure
    ) { error ->
        Common.handleErrors(
            error.responseMessage,
            error.errors,
            context
        )
    }
}

@Composable
private fun SpecialOrdersContent(viewState: SpecialOrderUiState, goToDetails: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(horizontal = 19.dp)
    ) {
        when (viewState.isLoading) {
            true -> SpecialOrderLoadingView()
            false -> SpecialOrderList(viewState, goToDetails)
        }
    }
}

@Composable
private fun SpecialOrderList(
    viewState: SpecialOrderUiState,
    goToDetails: () -> Unit
) {
    if (viewState.orders.isNullOrEmpty().not()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 18.dp,
                )
        ) {
            items(viewState.orders ?: listOf()) {
                SpecialOrderItem(it) {
                    goToDetails()
                }
            }
        }
    } else {
        EmptyView(
            "لا يوجد طلبات خاصة",
            " لا يوجد طلبات خاصة حاليًا",
            backgroundColor = Bg
        )
    }
}