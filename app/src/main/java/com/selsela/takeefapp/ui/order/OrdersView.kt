package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.order.item.OrderItem
import com.selsela.takeefapp.ui.theme.Bg

@Composable
fun OrdersView(
    goToDetails: () -> Unit,
    goToOrderRoute: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(horizontal = 19.dp)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            items(2) {
                OrderItem(
                    onClick = { goToDetails() }
                ) {
                    goToOrderRoute()
                }
            }
        }
    }
}
