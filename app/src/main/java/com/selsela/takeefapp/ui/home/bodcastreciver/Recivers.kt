package com.selsela.takeefapp.ui.home.bodcastreciver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.selsela.takeefapp.data.notification.NotificationReceiver
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.model.order.Price
import com.selsela.takeefapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
 fun WalletBroadcastReceiver(
    context: Context,
    onReceived: () -> Unit
) {
    val receiver: NotificationReceiver = object : NotificationReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onReceived()
        }
    }
    LocalBroadcastManager.getInstance(context).registerReceiver(
        receiver, IntentFilter(Constants.WALLET_CHANGED)
    )
}


@Composable
@OptIn(ExperimentalMaterialApi::class)
 fun BroadcastReceiver(
    coroutineScope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    context: Context,
    onReceived: (Order) -> Unit
) {
    val receiver: NotificationReceiver = object : NotificationReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val cost = intent.getStringExtra("additional_cost")
            val order = Order(
                price = Price(additionalCost = cost?.toDouble()!!),
                id = intent.getStringExtra("orderId")?.toInt() ?: -1
            )
            onReceived(order)
            coroutineScope.launch {
                if (sheetState.isVisible)
                    sheetState.hide()
                else {
                    sheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        }
    }
    LocalBroadcastManager.getInstance(context).registerReceiver(
        receiver, IntentFilter(Constants.ORDER_ADDITIONAL_COST)
    )
}


