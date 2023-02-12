package com.selsela.takeefapp.ui.home

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.home.bodcastreciver.BroadcastReceiver
import com.selsela.takeefapp.ui.home.bodcastreciver.WalletBroadcastReceiver
import com.selsela.takeefapp.ui.home.component.AnimContent
import com.selsela.takeefapp.ui.home.component.BottomCostCard
import com.selsela.takeefapp.ui.home.component.Header
import com.selsela.takeefapp.ui.home.component.TitleView
import com.selsela.takeefapp.ui.order.AddedCostSheet
import com.selsela.takeefapp.ui.order.OrderUiState
import com.selsela.takeefapp.ui.order.OrderViewModel
import com.selsela.takeefapp.ui.splash.ChangeNavigationBarColor
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.NoRippleTheme
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.showSuccess
import com.selsela.takeefapp.utils.GetLocationDetail
import com.selsela.takeefapp.utils.LocalData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel,
    orderViewModel: OrderViewModel = hiltViewModel(),
    walletViewModel: AuthViewModel = hiltViewModel(),
    goToSpecialOrder: () -> Unit,
    goToMyAccount: () -> Unit,
    goToNotification: () -> Unit,
    goToLogin: () -> Unit,
) {
    Color.Transparent.ChangeStatusBarColor(true)
    Color.White.ChangeNavigationBarColor(true)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val viewState: OrderUiState by orderViewModel.uiState.collectAsStateLifecycleAware(
        OrderUiState()
    )

    HomeContent(
        viewModel,
        goToNotification,
        goToMyAccount,
        goToSpecialOrder,
        goToLogin,
        sheetState,
        viewState,
        orderViewModel,
        coroutineScope,
        context,
        walletViewModel
    )

    when (viewState.state) {
        State.SUCCESS -> {
            if (viewState.responseMessage.isNullOrEmpty().not()) {
                LocalContext.current.showSuccess(
                    viewState.responseMessage ?: ""
                )
                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch {
                        if (sheetState.isVisible)
                            sheetState.hide()
                    }
                }
                viewState.responseMessage = ""
            }
        }
        else -> {}
    }

    Extensions.getMyLocation(context = context) {
        viewModel.selectedAddress.value =
            GetLocationDetail(context).getCurrentAddress(it.latitude, it.longitude) ?: ""
        viewModel.currentLocation.value = it
        "getMyLocation:${viewModel.selectedAddress.value}".log()
    }

}

@Composable
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
private fun HomeContent(
    viewModel: HomeViewModel,
    goToNotification: () -> Unit,
    goToMyAccount: () -> Unit,
    goToSpecialOrder: () -> Unit,
    goToLogin: () -> Unit,
    sheetState: ModalBottomSheetState,
    viewState: OrderUiState,
    orderViewModel: OrderViewModel,
    coroutineScope: CoroutineScope,
    context: Context,
    walletViewModel: AuthViewModel
) {
    // hide ripple effect
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            var costVisible by remember {
                mutableStateOf(viewModel.selectedOrderService.value.services.isEmpty().not())
            }
            val service by remember {
                mutableStateOf(viewModel.selectedOrderService.value)
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 45.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(
                    goToNotification,
                    goToMyAccount
                )
                TitleView()
                // Services List
                LazyColumn(modifier = Modifier.padding(top = 24.dp)) {
                    items(
                        viewModel.getServicesList().value ?: listOf(),
                        key = { it.id }) {
                        AnimContent(
                            it,
                            viewModel = viewModel,
                            onSelect = {
                                costVisible =
                                    viewModel.selectedOrderService.value.services.isEmpty().not()
                            }
                        ) {
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 25.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    ElasticButton(
                        onClick = {
                            if (LocalData.accessToken.isNullOrEmpty().not())
                                goToSpecialOrder()
                            else goToLogin()
                        },
                        title = stringResource(R.string.special_order_add),
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Bottom Cost Card
            BottomCostCard(
                modifier = Modifier.align(Alignment.BottomCenter),
                costVisible, service, goToLogin, viewModel
            )
        }
    }

    var order by remember {
        mutableStateOf(Order())
    }
    AddedCostSheet(
        sheetState = sheetState,
        order,
        viewState,
        onReject = orderViewModel::rejectAdditionalCost,
        onPay = orderViewModel::acceptAdditionalCost
    )
    BroadcastReceiver(coroutineScope, sheetState, context) {
        order = it
    }

    WalletBroadcastReceiver(context = context) {
        walletViewModel.wallet()
    }
}


