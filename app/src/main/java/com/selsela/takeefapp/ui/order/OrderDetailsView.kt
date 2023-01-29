package com.selsela.takeefapp.ui.order

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
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
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.order.AcTypes
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.common.ElasticButton
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
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.DividerColor
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Constants.FINISHED
import com.selsela.takeefapp.utils.Constants.NEW_ORDER
import com.selsela.takeefapp.utils.Constants.ON_WAY
import com.selsela.takeefapp.utils.Constants.RECEIVED
import com.selsela.takeefapp.utils.Constants.UPCOMING_ORDERS
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import de.palm.composestateevents.EventEffect

@Composable
fun OrderDetailsView(
    orderId: Int,
    viewModel: OrderViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val viewState: OrderUiState by viewModel.uiState.collectAsStateLifecycleAware(
        OrderUiState()
    )

    when (viewState.state) {
        State.SUCCESS -> {
            viewState.order?.let {
                OrderDetailsContent(
                    onBack,
                    onCancel = viewModel::cancelOrder,
                    it
                )
            }
        }

        State.LOADING -> {
            LoadingView()
        }

        else -> {}
    }


    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.getOrderDetails(orderId)
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
        viewModel.getOrderDetails(orderId)
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun OrderDetailsContent(
    onBack: () -> Unit,
    onCancel: (Int) -> Unit,
    order: Order
) {
    Color.Transparent.ChangeStatusBarOnlyColor()
    val coroutineScope = rememberCoroutineScope()
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(bottom = 45.dp)
    ) {
        Column {
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
                    Spacer(modifier = Modifier.height(22.dp))
                    if (order.case.id != 6) {
                        StepperView(
                            isDetails = true,
                            items = LocalData.cases?.filter { it.id != 6 },
                            currentStep = order.logs.distinctBy { it.case.id }.lastIndex
                        )
                    }
                    AdditionalCostView(
                        isVisible = order.needAdditionalCost == 1,
                        coroutineScope,
                        rateSheetState,
                        paySheetState
                    )
                    if (order.case.canRate == 1 && order.isRated == 0) {
                        RateButton(
                            onClick = { /*TODO*/ },
                            title = stringResource(id = R.string.rate),
                            icon = R.drawable.star,
                            iconGravity = Constants.RIGHT,
                            modifier = Modifier
                                .paddingTop(13)
                                .requiredHeight(36.dp)
                                .fillMaxWidth(0.6f),
                            buttonBg = TextColor
                        )
                    }
                    Divider(
                        thickness = 1.dp,
                        color = DividerColor,
                        modifier = Modifier.padding(top = 27.6.dp)
                    )
                    VisitDateView(order.workPeriod)
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

    PaySheet(sheetState = paySheetState) {
    }
    RateSheet(rateSheetState) {

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


