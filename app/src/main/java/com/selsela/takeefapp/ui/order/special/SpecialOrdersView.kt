package com.selsela.takeefapp.ui.order.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.order.cell.SpecialOrderItem
import com.selsela.takeefapp.ui.theme.Bg

@Preview
@Composable
fun SpecialOrders(
    goToDetails: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(horizontal = 19.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                vertical = 18.dp,
            )
        ) {
            items(3) {
                SpecialOrderItem(){
                    goToDetails()
                }
            }
        }
    }
}