package com.selsela.takeefapp.ui.order

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.notification.NotificationReceiver
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.common.RateButton
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.common.StepperView
import com.selsela.takeefapp.ui.common.components.LoadingView
import com.selsela.takeefapp.ui.order.componenets.AdditionalCostView
import com.selsela.takeefapp.ui.order.componenets.CostView
import com.selsela.takeefapp.ui.order.componenets.SelectedAddressView
import com.selsela.takeefapp.ui.order.componenets.ServiceItem
import com.selsela.takeefapp.ui.order.componenets.VisitDateView
import com.selsela.takeefapp.ui.order.item.DateView
import com.selsela.takeefapp.ui.order.rate.RateSheet
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.DividerColor
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Constants.FINISHED
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.showSuccess
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import de.palm.composestateevents.EventEffect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetailsView(
    orderId: Int,
    viewModel: OrderViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    Color.White.ChangeStatusBarColor(true)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewState: OrderUiState by viewModel.uiState.collectAsStateLifecycleAware(
        OrderUiState()
    )
    val paySheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val rateSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    BackHandler {
        onBack()
    }

    when (viewState.state) {
        State.SUCCESS -> {
            viewState.order?.let {
                OrderDetailsContent(
                    onBack,
                    onCancel = viewModel::cancelOrder,
                    it,
                    rateSheetState,
                    paySheetState,
                    viewState,
                    onReject = viewModel::rejectAdditionalCost
                )
            }
            if (viewState.responseMessage.isNullOrEmpty().not()) {
                LocalContext.current.showSuccess(
                    viewState.responseMessage ?: ""
                )
                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch {
                        if (rateSheetState.isVisible)
                            rateSheetState.hide()

                        if (paySheetState.isVisible)
                            paySheetState.hide()
                    }
                }
                viewState.responseMessage = ""
            }
        }

        State.LOADING -> {
            if (!viewModel.isDetailsLoaded)
                LoadingView()
            else {
                viewState.order?.let {
                    OrderDetailsContent(
                        onBack,
                        onCancel = viewModel::cancelOrder,
                        it,
                        rateSheetState,
                        paySheetState,
                        viewState,
                        onReject = viewModel::rejectAdditionalCost
                    )
                }
            }
        }

        else -> {}
    }


    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
            viewModel.getOrderDetails(orderId)
    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = viewModel::onFailure
    ) { error ->
        if (error.status == 403) {
            LocalData.clearData()
        }
        Common.handleErrors(
            error.responseMessage,
            error.errors,
            context
        )
        viewModel.getOrderDetails(orderId)
    }

    viewModel.getRateItem()
    PaySheet(
        sheetState = paySheetState,
        viewState,
        onPay = viewModel::acceptAdditionalCost
    )
    RateSheet(
        viewModel = viewModel,
        rateSheetState,
        viewState,
        onConfirm = viewModel::rateOrder
    )

    BrodcastRevicer(context = context){
        viewModel.getOrderDetails(orderId)
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun OrderDetailsContent(
    onBack: () -> Unit,
    onCancel: (Int) -> Unit,
    order: Order,
    rateSheetState: ModalBottomSheetState,
    paySheetState: ModalBottomSheetState,
    viewState: OrderUiState,
    onReject: (Int) -> Unit
) {
    Color.Transparent.ChangeStatusBarOnlyColor(true)
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(bottom = 45.dp)
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Header(order, onCancel = {
                onCancel(it)
            }) {
                onBack()
            }
            Card(
                modifier = Modifier
                    .padding(
                        vertical = 14.2.dp,
                        horizontal = 19.dp
                    )
                    .fillMaxSize()
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = stringResource(id = R.string.order_number),
                                style = text11,
                                color = SecondaryColor
                            )
                            Text(
                                text = "#${order.number}",
                                style = text16Bold,
                                color = TextColor
                            )
                        }
                        DateView(order.createdAt)
                    }
                    if (order.case.id != 6) {
                        Spacer(modifier = Modifier.height(22.dp))
                        StepperView(
                            Modifier
                                .fillMaxWidth(),
                            items = LocalData.cases?.filter { it.id != 6 },
                            currentStep = order.logs.distinctBy { it.case.id }.lastIndex
                        )
                    } else {
                        Row(
                            Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End) {
                            Box(
                                modifier = Modifier
                                    .size(width = 80.dp, height = 32.dp)
                                    .background(Red.copy(.10f), RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.canceled),
                                    style = text12,
                                    color = Red
                                )
                            }
                        }

                    }
                    AdditionalCostView(
                        isVisible = order.case.id != FINISHED && order.needAdditionalCost == 1 && order.additional_cost_status == "waiting",
                        coroutineScope,
                        paySheetState,
                        viewState,
                    ) {
                        onReject(order.id)
                    }

                    if (order.case.canRate == 1 && order.isRated == 0) {
                        Spacer(modifier = Modifier.height(24.dp))
                        RateButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (rateSheetState.isVisible)
                                        rateSheetState.hide()
                                    else rateSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            },
                            title = stringResource(id = R.string.rate),
                            icon = R.drawable.star,
                            iconGravity = Constants.RIGHT,
                            modifier = Modifier
                                .requiredHeight(36.dp)
                                .fillMaxWidth(0.8f),
                            buttonBg = TextColor
                        )
                    }

                    Divider(
                        thickness = 1.dp,
                        color = DividerColor,
                        modifier = Modifier.padding(top = 27.6.dp)
                    )
                    order.orderDate.log("order.orderDate")
                    VisitDateView(order.workPeriod,order.orderDate)
                    Divider(
                        thickness = 1.dp,
                        color = DividerColor,
                        modifier = Modifier.padding(top = 8.6.dp)
                    )
                    SelectedAddressView(order.address)
                    CostView(order.price, order.payment, order.useWallet)
                    Box(
                        modifier = Modifier
                            .padding(top = 11.2.dp)
                            .padding(horizontal = 7.dp)
                            .fillMaxWidth()
                            .requiredHeight(39.dp)
                            .background(SecondaryColor2, shape = RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.service_details),
                            style = text14,
                        )
                    }

                    Column(
                        Modifier
                            .padding(horizontal = 7.dp)
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {

                        repeat(order.orderService.size) {
                            ServiceItem(order.orderService[it])
                        }
                    }
                }
            }
        }

    }

}

@Composable
private fun Header(
    order: Order,
    onCancel: (Int) -> Unit,
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
            text = stringResource(id = R.string.order_details),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = text14Meduim
        )

        val openDialog = remember { mutableStateOf(false) }

        if (order.case.canCancel == 1) {
            IconButton(
                onClick = {
                    openDialog.value = !openDialog.value
                },
                modifier = Modifier
                    .padding(end = 18.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Text(
                    text = stringResource(id = R.string.cancel_order),
                    style = text14Meduim
                )
            }
        }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        onCancel(order.id)
                        openDialog.value = false
                    })
                    {
                        Text(
                            text = stringResource(id = R.string.yes),
                            style = text14Meduim,
                            color = Purple40
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                    })
                    {
                        Text(
                            text = stringResource(id = R.string.no),
                            style = text14Meduim,
                            color = Purple40
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.cancel_order),
                        style = text16Bold
                    )
                },
                text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = stringResource(R.string.cancel_order_lbl),
                        style = text14
                    )
                }
            )
        }
    }
}

@Composable
private fun BrodcastRevicer(
    context: Context,
    onReceived: () -> Unit
) {
    val receiver: NotificationReceiver = object : NotificationReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onReceived()
        }
    }
    LocalBroadcastManager.getInstance(context).registerReceiver(
        receiver, IntentFilter(Constants.ORDER_STATUS_CHANGED)
    )
    LocalBroadcastManager.getInstance(context).registerReceiver(
        receiver, IntentFilter(Constants.ORDER_ADDITIONAL_COST)
    )
}
