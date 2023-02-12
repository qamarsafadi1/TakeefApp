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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.DividerColor
import com.selsela.takeefapp.ui.theme.DividerColorAlpha
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.SecondaryColorAlpha25
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text13
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text20Meduim
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import com.selsela.takeefapp.data.config.model.WorkPeriod
import com.selsela.takeefapp.data.config.model.payments.Payment
import com.selsela.takeefapp.data.order.model.order.SelectedService
import com.selsela.takeefapp.ui.common.AsyncImage
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.home.OrderUiState
import com.selsela.takeefapp.ui.theme.text14Strike
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants.CLEANING
import com.selsela.takeefapp.utils.Constants.INSTALLATION
import com.selsela.takeefapp.utils.Constants.MAINTENANCE
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.getCurrency
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import de.palm.composestateevents.EventEffect

@Composable
fun ReviewOrderView(
    vm: HomeViewModel,
    goTo: () -> Unit
) {
    val viewState: OrderUiState by vm.uiState.collectAsStateLifecycleAware(
        OrderUiState()
    )
    val context = LocalContext.current
    ReviewOrderContent(
        viewState,
        vm,
        placeOrder = vm::placeOrder
    )

    /**
     * Handle Ui state from flow
     */

    EventEffect(
        event = viewState.onSuccess,
        onConsumed = vm::onSuccess
    ) {
        goTo()
    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = vm::onFailure
    ) { error ->
        Common.handleErrors(
            error.responseMessage,
            error.errors,
            context
        )
    }

}

@Composable
private fun ReviewOrderContent(viewState: OrderUiState, vm: HomeViewModel, placeOrder: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.please_select_payment),
                style = text12,
                color = SecondaryColor,
            )
            SelectedAddressView(vm.selectedAddress.value)
            Divider(
                modifier = Modifier
                    .padding(vertical = 18.6.dp)
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = DividerColor
            )

            VisitDateView(vm.selectedPeriodId)
            Divider(
                modifier = Modifier
                    .padding(vertical = 18.6.dp)
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = DividerColor
            )

            Row(
                modifier = Modifier
                    .paddingTop(15.7)
                    .requiredHeight(39.dp)
                    .fillMaxWidth()
                    .background(color = SecondaryColor2, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.required_services),
                    style = text14,
                    color = TextColor,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(35.dp))
                Text(
                    text = stringResource(R.string.cost),
                    style = text14,
                    color = TextColor,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )


            }


            Column(
                modifier = Modifier
                    .paddingTop(4)
                    .fillMaxWidth()
            ) {
                vm.selectedOrderService.value.services.groupBy { it.serviceId }.log("groupBy")
                vm.selectedOrderService.value.services.log("groupBy!")
                val services = vm.selectedOrderService.value.services.groupBy { it.serviceId }

                val isNotMaintinance = vm.selectedOrderService.value.services.any {
                    it.serviceId == INSTALLATION || it.serviceId == CLEANING
                }
                isNotMaintinance.log("IsStrike")
                repeat(services.keys.size) {
                    val itemIndex = services.keys.elementAt(it)
                    val item = services.getValue(itemIndex)
                    ServiceItem(item, isNotMaintinance, vm)
                }

            }
            if (vm.calculateTax() != "0.0")
                TaxItem(vm.calculateTax())

            Box(
                modifier = Modifier
                    .paddingTop(7)
                    .fillMaxWidth()
                    .height(81.dp)
                    .background(LightBlue.copy(.20f), RoundedCornerShape(11.dp))
                    .padding(horizontal = 23.5.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.money),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterStart)
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
                        text = vm.getTotalPrice(),
                        style = text20Meduim,
                        color = TextColor,
                    )
                    Text(
                        text = stringResource(R.string.currency, getCurrency()), style = text12,
                        color = SecondaryColor,
                    )
                }

            }



            if (LocalData.userWallet?.balance != 0.0)
                WalletItemView(vm)
            if (vm.isWalletEnough().not()) {
                PaymentView(vm)
            }

            ElasticButton(
                onClick = { placeOrder() }, title = stringResource(R.string.pay_noew),
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .fillMaxWidth()
                    .requiredHeight(48.dp),
                isLoading = viewState.isLoading
            )

        }
    }
}

@Composable
private fun WalletItemView(vm: HomeViewModel) {
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
            Text(
                text = stringResource(R.string.wallet_balance),
                style = text14,
                color = TextColor
            )
            if (vm.isWalletEnough().not()) {
                Row(
                    Modifier.paddingTop(11),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.alert),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(R.string.your_wallet_enough),
                        style = text12,
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
            Text(
                text = "${LocalData.userWallet?.balance}",
                style = text14Meduim,
                color = TextColor
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.currency_1, getCurrency()),
                style = text13,
                color = SecondaryColor
            )
        }
    }

    if (vm.isWalletEnough().not()) {
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
                Text(
                    text = stringResource(R.string.remian_to_pay),
                    style = text14,
                    color = TextColor
                )
                // todo: need to add visibilty here
                Row(
                    Modifier.paddingTop(6),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.please_select_payment_1),
                        style = text12,
                        color = SecondaryColor
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
                Text(
                    text = "${
                        vm.getTotalPrice().toDouble() - (LocalData.userWallet?.balance ?: 0.0)
                    }",
                    style = text14Meduim,
                    color = TextColor
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(id = R.string.currency_1, getCurrency()),
                    style = text13,
                    color = SecondaryColor
                )
            }
        }
    }
}

@Composable
fun PaymentView(vm: HomeViewModel) {
    Column(
        modifier = Modifier
            .paddingTop(18)
            .fillMaxWidth()
    ) {
        repeat(LocalData.paymentsType?.size ?: 0) {
            PaymentItem(
                vm,
                LocalData.paymentsType?.get(it)!!,
                onClick = vm::selectPayment
            )

        }
    }
}


@Composable
private fun PaymentItem(
    vm: HomeViewModel,
    item: Payment, onClick: (Int) -> Unit
) {

    val isSelected = item.id == vm.selectedPaymentId.value
    Row(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .requiredHeight(53.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            imageUrl = item.iconUrl,
            modifier = Modifier.size(33.dp)
        )

        Spacer(modifier = Modifier.width(17.7.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(53.dp)
                .background(
                    color = if (isSelected.not())
                        SecondaryColorAlpha25
                    else Color.White,
                    shape = RoundedCornerShape(11.dp)
                )
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = if (isSelected) Purple40 else SecondaryColorAlpha25,
                    shape = RoundedCornerShape(11.dp)
                )
                .clip(shape = RoundedCornerShape(11.dp))
                .clickable {
                    onClick(item.id)
                }
                .padding(horizontal = 14.3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.name, style = text14,
                color = TextColor
            )
            Image(
                painter =
                if (isSelected)
                    painterResource(id = R.drawable.checked)
                else painterResource(id = R.drawable.uncheckedrbwhite),
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun ServiceItem(
    selectedService: List<SelectedService>,
    isNotMaintinance: Boolean = false,
    vm: HomeViewModel,
) {
    selectedService.find { it.serviceId == MAINTENANCE }?.servicePrice?.log("servicePrice")
    Row(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
            .requiredHeight(39.dp)
            .background(Color.White, RoundedCornerShape(6.dp))
            .border(
                width = 1.dp,
                color = DividerColorAlpha,
                RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = stringResource(
                    id = R.string.maintinance_serivce,
                    LocalData.services?.find {
                        selectedService.last().serviceId == it.id
                    }?.name.toString()
                ),
                style = text12,
                color = SecondaryColor
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${vm.getCount(selectedService.first().serviceId)}", style = text14,
                    color = TextColor,
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(id = R.string.device),
                    style = text12,
                    color = SecondaryColor
                )
                Spacer(modifier = Modifier.width(5.dp))
            }

        }

        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .width(1.dp)
                .height(38.dp),
            color = DividerColor
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${
                    if (isNotMaintinance) {
                        if (selectedService.first().serviceId == MAINTENANCE) selectedService.find { it.serviceId == MAINTENANCE }?.servicePrice ?: 0.0
                        else {
                            selectedService.sumOf {
                                if (it.serviceId != MAINTENANCE)
                                    it.servicePrice.times(it.count)
                                else 0.0
                            }
                        }
                    } else selectedService.find { it.serviceId == MAINTENANCE }?.servicePrice ?: 0.0
                }",
                style = if (isNotMaintinance && selectedService.first().serviceId == MAINTENANCE) text14Strike else text14,
                color = TextColor,
            )
            Text(
                text = stringResource(id = R.string.currency_1, getCurrency()),
                style = if (isNotMaintinance && selectedService.first().serviceId == MAINTENANCE) text14Strike else text14,
                color = TextColor
            )

        }

    }
}

@Composable
private fun TaxItem(tax: String) {
    Row(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth()
            .requiredHeight(39.dp)
            .background(Color.White, RoundedCornerShape(6.dp))
            .border(
                width = 1.dp,
                color = DividerColorAlpha,
                RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(
                    id = R.string.tax
                ),
                style = text12,
                color = SecondaryColor
            )
            Spacer(modifier = Modifier.width(28.dp))

            Text(
                text = "${LocalData.configurations?.taxPercent}%", style = text14,
                color = TextColor,
                modifier = Modifier.padding(end = 7.dp)
            )
        }

        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .width(1.dp)
                .height(38.dp),
            color = DividerColor
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = tax,
                style = text14,
                color = TextColor
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = stringResource(id = R.string.currency_1, getCurrency()),
                style = text14,
                color = TextColor
            )

        }

    }
}

@Composable
private fun VisitDateView(selectedPeriodId: MutableState<WorkPeriod>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.date_line),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.visit_date_1),
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.padding(start = 8.dp)
            )

        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = selectedPeriodId.value.name,
                    style = text14,
                    color = TextColor
                )
            }
            Text(
                text = "${selectedPeriodId.value.getTimeFromFormatted()} - ${selectedPeriodId.value.getTimeToFormatted()}",
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(
                    7
                )
            )
        }
    }
}

@Composable
private fun SelectedAddressView(address: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = "",
            modifier = Modifier.padding(end = 6.6.dp, top = 20.dp)
        )
        Column(
            modifier = Modifier
                .paddingTop(20)
                .weight(1f)
        )
        {
            androidx.compose.material.Text(
                text = stringResource(R.string.selected_address),
                style = text12Meduim,
                color = SecondaryColor
            )
            androidx.compose.material.Text(
                text = address,
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(11)
            )
        }

    }
}