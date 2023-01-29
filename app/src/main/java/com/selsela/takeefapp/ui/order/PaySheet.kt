package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.payments.Payment
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.common.AsyncImage
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12White
import com.selsela.takeefapp.ui.theme.text13
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.ui.theme.text20Meduim
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PaySheet(
    sheetState: ModalBottomSheetState,
    viewState: OrderUiState,
    onPay: (Int, Int,Int) -> Unit
) {
    Box() {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                PaySheetContent(
                    order = viewState.order,
                    viewState = viewState,
                    onPay = onPay
                )
            }) {

        }
    }

}

@Composable
private fun PaySheetContent(
    vm: HomeViewModel = hiltViewModel(),
    viewState: OrderUiState,
    order: Order?, onPay: (Int, Int,Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(
                horizontal = 24.dp,
                vertical = 10.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.bottomsheet),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.pay_lbl),
            style = text14,
            color = SecondaryColor,
            modifier = Modifier.paddingTop(37.5)
        )

        Text(
            text = stringResource(R.string.extra_cost_maintincance),
            style = text20,
            color = Color.White,
            modifier = Modifier.paddingTop(9.5)
        )
        Box(
            modifier = Modifier
                .paddingTop(25)
                .fillMaxWidth()
                .height(81.dp)
                .background(LightBlue.copy(.07f), RoundedCornerShape(11.dp))
                .padding(horizontal = 23.5.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.money),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterStart),
                colorFilter = ColorFilter.tint(SecondaryColor2.copy(0.33f))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.cost_1), style = text12,
                    color = SecondaryColor
                )
                Text(
                    text = "${order?.price?.additionalCost}",
                    style = text20Meduim,
                    color = Color.White,
                )
                Text(
                    text = stringResource(R.string.currency), style = text12,
                    color = SecondaryColor,
                )
            }

        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.payment_method),
                style = text14,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(24.5)
            )

        }

        var selectedPayment = -1
        if (LocalData.userWallet?.balance != 0.0)
            WalletItemView(vm,order)
        if (vm.isWalletEnough(order?.price?.additionalCost ?: 0.0).not()) {
            PaymentViewDark() {
                selectedPayment = it
            }
        }

        ElasticButton(
            onClick = { onPay(selectedPayment,order?.id ?: -1, vm.useWallet, ) },
            title = stringResource(R.string.pay_noew),
            modifier = Modifier
                .padding(top = 21.dp)
                .fillMaxWidth()
                .requiredHeight(48.dp),
            isLoading = viewState.state == State.LOADING
        )


    }
}

@Composable
fun WalletItemView(vm: HomeViewModel, order: Order?) {
    Row(
        modifier = Modifier
            .paddingTop(17)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.wallet_balance),
                style = text14,
                color = Color.White
            )
            if (vm.isWalletEnough(order?.price?.additionalCost ?: 0.0).not()) {
                Row(
                    Modifier.paddingTop(11),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.alert),
                        contentDescription = ""
                    )
                    androidx.compose.material3.Text(
                        text = stringResource(R.string.your_wallet_enough),
                        style = text12White,
                        color = Red,
                        modifier = Modifier.padding(start = 4.3.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            androidx.compose.material3.Text(
                text = "${LocalData.userWallet?.balance}",
                style = text14Meduim,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(5.dp))
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.currency_1, Extensions.getCurrency()),
                style = text13,
                color = SecondaryColor
            )
        }
    }

    if (vm.isWalletEnough(order?.price?.additionalCost ?: 0.0).not()) {
        Row(
            modifier = Modifier
                .paddingTop(23)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                androidx.compose.material3.Text(
                    text = stringResource(R.string.remian_to_pay),
                    style = text14,
                    color = Color.White
                )
                // todo: need to add visibilty here
                Row(
                    Modifier.paddingTop(6),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Text(
                        text = stringResource(R.string.please_select_payment_1),
                        style = text12,
                        color = Color.White
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                androidx.compose.material3.Text(
                    text = "${
                        (order?.price?.additionalCost ?: 0.0) - (LocalData.userWallet?.balance ?: 0.0)
                    }",
                    style = text14Meduim,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(5.dp))
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.currency_1),
                    style = text13,
                    color = SecondaryColor
                )
            }
        }
    }
}


@Composable
private fun PaymentItemDark(payment: Payment, isSelected: Boolean, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .requiredHeight(53.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            imageUrl = payment.iconUrl,
            modifier = Modifier.size(33.dp)
        )
        Spacer(modifier = Modifier.width(17.7.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(53.dp)
                .background(
                    color =
                    LightBlue.copy(0.07f),
                    RoundedCornerShape(11.dp)
                )
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = if (isSelected) Purple40 else LightBlue.copy(0.07f),
                    shape = RoundedCornerShape(11.dp)
                )
                .clip(shape = RoundedCornerShape(11.dp))
                .clickable {
                    onClick()
                }
                .padding(horizontal = 14.3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = payment.name, style = text14,
                color = Color.White
            )
            Image(
                painter =
                if (isSelected)
                    painterResource(id = R.drawable.checked)
                else painterResource(id = R.drawable.uncheckedrbwhitealpha),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun PaymentViewDark(
    onSelectPayment: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .paddingTop(18)
            .fillMaxWidth()
    ) {
        var selectedPayment by remember {
            mutableStateOf(0)
        }
        repeat(LocalData.paymentsType?.size ?: 0) {
            PaymentItemDark(
                LocalData.paymentsType!![it],
                selectedPayment == LocalData.paymentsType!![it].id
            ) {
                selectedPayment = LocalData.paymentsType!![it].id
                onSelectPayment(selectedPayment)
            }
        }

    }
}