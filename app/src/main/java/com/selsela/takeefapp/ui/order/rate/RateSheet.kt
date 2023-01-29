package com.selsela.takeefapp.ui.order.rate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.theme.TextColor
import androidx.compose.runtime.*
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.order.OrderUiState
import com.selsela.takeefapp.ui.order.OrderViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RateSheet(
    sheetState: ModalBottomSheetState,
    viewState: OrderUiState,
    onConfirm: (Int, List<List<Rate>>, String?) -> Unit
) {
    Box() {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                RateSheetContent(
                    viewState,
                    onConfirm
                )
            }) {
        }
    }
}

