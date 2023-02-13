package com.selsela.takeefapp.ui.order.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.order.Address
import com.selsela.takeefapp.data.order.model.order.OrderService
import com.selsela.takeefapp.data.order.model.order.Payment
import com.selsela.takeefapp.data.order.model.order.Price
import com.selsela.takeefapp.data.order.model.order.WorkPeriod
import com.selsela.takeefapp.ui.common.AsyncImage
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.order.OrderUiState
import com.selsela.takeefapp.ui.theme.ColorAccent
import com.selsela.takeefapp.ui.theme.DividerColorBlue
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text11NoLines
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text13
import com.selsela.takeefapp.ui.theme.text13Strike
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.ui.theme.text16MediumStrike
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Constants.WALLET
import com.selsela.takeefapp.utils.DateHelper
import com.selsela.takeefapp.utils.Extensions.Companion.getCurrency
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterialApi::class)
fun AdditionalCostView(
    isVisible: Boolean,
    coroutineScope: CoroutineScope,
    paySheetState: ModalBottomSheetState,
    viewState: OrderUiState,
    onReject: () -> Unit
) {
    if (isVisible) {
        MaintenanceCostWarning()
        AcceptRejectButtons(viewState) {
            coroutineScope.launch {
                when (it) {
                    Constants.REJECT -> {
                        onReject()
                    }

                    Constants.ACCEPT -> {
                        if (paySheetState.isVisible)
                            paySheetState.hide()
                        else paySheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }

            }
        }
    }
}


@Composable
private fun AcceptRejectButtons(
    viewState: OrderUiState,
    onClick: (Int) -> Unit) {
    Row(
        Modifier
            .padding(top = 11.dp)
            .padding(horizontal = 21.dp)
            .fillMaxWidth()
    ) {

        ElasticButton(
            onClick = { onClick(Constants.REJECT) },
            title = stringResource(R.string.reject_1),
            colorBg = Red,
            modifier = Modifier
                .weight(1f)
                .requiredHeight(36.dp),
            isLoading = viewState.state == State.LOADING
        )
        Spacer(modifier = Modifier.width(18.dp))
        ElasticButton(
            onClick = {
                onClick(Constants.ACCEPT)
            },
            title = stringResource(R.string.acccept_pay),
            icon = R.drawable.pay,
            modifier = Modifier
                .weight(1f)
                .requiredHeight(36.dp),
            iconGravity = Constants.RIGHT,
        )
    }
}

@Composable
private fun MaintenanceCostWarning() {
    Row(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 12.dp)
            .fillMaxWidth()
            .requiredHeight(62.dp)
            .background(
                ColorAccent.copy(0.19f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.info),
            contentDescription = ""
        )

        Text(
            text = stringResource(R.string.extra_cost),
            style = text11NoLines,
            color = TextColor,
            modifier = Modifier.padding(start = 8.8.dp)
        )
    }
}

@Composable
fun SelectedAddressView(address: Address) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
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
            Text(
                text = stringResource(R.string.selected_address),
                style = text12Meduim,
                color = SecondaryColor
            )
            Text(
                text = address.note,
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(11)
            )
        }

    }
}

@Composable
fun VisitDateView(workPeriod: WorkPeriod,date: String = "") {
    val orderDate = if (date != "") DateHelper.getOrderDateNoraml(date) else listOf()

    Row(
        modifier = Modifier
            .padding(top = 13.dp)
            .padding(horizontal = 9.6.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.date_line),
                contentDescription = ""
            )
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.visit_date_1),
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.padding(start = 8.dp)
            )

        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.width(25.dp))
                androidx.compose.material3.Text(
                    text = workPeriod.name,
                    style = text14,
                    color = TextColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            Row(
                Modifier.paddingTop(
                    7
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (orderDate.isEmpty().not())
                        "${orderDate[0]}-${orderDate[1]}-${orderDate[2]}"
                    else "",
                    style = text12,
                    color = SecondaryColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = ".",
                    style = text12,
                    color = SecondaryColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = "${workPeriod.getTimeFromFormatted()} - ${workPeriod.getTimeToFormatted()}",
                    style = text12,
                    color = SecondaryColor,
                )

            }

        }
    }

}

@Composable
fun ServiceItem(orderService: OrderService) {
    Column(
        Modifier
            .paddingTop(11)
            .background(Color.White, shape = RoundedCornerShape(6.dp))
            .border(
                width = 1.dp, color = SecondaryColor2,
                RoundedCornerShape(6.dp)
            )
            .padding(bottom = 13.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 7.dp)
                .fillMaxWidth()
                .padding(top = 23.2.dp)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text = stringResource(R.string.maintinance_serivce, orderService.service.name),
                    style = text12,
                    color = TextColor,
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                androidx.compose.material3.Text(
                    text = "${orderService.totalServicePrice}",
                    style = if (orderService.isCalculatedInTotal == 1) text16Medium else text16MediumStrike,
                    color = TextColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.currency_1, getCurrency()),
                    style = if (orderService.isCalculatedInTotal == 1) text13 else text13Strike,
                    color = SecondaryColor
                )

            }
        }
        repeat(orderService.acType.size) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = orderService.acType[it].acType.name,
                        style = text12,
                        color = SecondaryColor,
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    androidx.compose.material3.Text(
                        text = "${orderService.acType[it].count}",
                        style = text16Medium,
                        color = TextColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    androidx.compose.material3.Text(
                        text = stringResource(R.string.device),
                        style = text13,
                        color = SecondaryColor
                    )
                }
            }
        }
    }
}

@Composable
fun CostView(price: Price, payment: Payment, useWallet: Int) {
    Column(
        Modifier
            .padding(top = 21.dp)
            .padding(horizontal = 7.3.dp)
            .fillMaxWidth()
            .background(LightBlue.copy(.20f), shape = RoundedCornerShape(11.dp))
            .padding(bottom = 23.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 23.2.dp)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.moneyicon),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(LightBlue.copy(0.30f))
                )
                Text(
                    text = stringResource(id = R.string.cost_1),
                    style = text14Meduim,
                    color = TextColor,
                    modifier = Modifier.padding(start = 13.3.dp)
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                androidx.compose.material3.Text(
                    text = "${price.grandTotal}",
                    style = text16Medium,
                    color = TextColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.currency_1, getCurrency()),
                    style = text13,
                    color = SecondaryColor
                )

            }
        }

        Divider(
            thickness = 1.dp,
            color = DividerColorBlue,
            modifier = Modifier.paddingTop(15)
        )
        when (payment.id) {
            WALLET -> {
                WalletSection(false, paidWallet = price.paidWallet, price = 0.0)
            }
            else -> {
                if (useWallet == 1) {
                    WalletSection(
                        true,
                        price = price.grandTotal - price.paidWallet,
                        payment = payment,
                        paidWallet = price.paidWallet
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp)
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.payment_method),
                                style = text12,
                                color = SecondaryColor,
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                        ) {
                            AsyncImage(
                                imageUrl = payment.iconUrl,
                                modifier = Modifier.size(33.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            androidx.compose.material3.Text(
                                text = "${price.grandTotal}",
                                style = text16Medium,
                                color = TextColor,
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            androidx.compose.material3.Text(
                                text = stringResource(id = R.string.currency_1, getCurrency()),
                                style = text13,
                                color = SecondaryColor
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WalletSection(
    isPartition: Boolean = false, price: Double,
    payment: Payment? = null,
    paidWallet: Double? = 0.0
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.2.dp)
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.wallet_balance),
                style = text12,
                color = SecondaryColor,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            androidx.compose.material3.Text(
                text = "${paidWallet}",
                style = text16Medium,
                color = TextColor
            )
            Spacer(modifier = Modifier.width(3.dp))
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.currency_1, getCurrency()),
                style = text13,
                color = SecondaryColor
            )

        }
    }
    if (isPartition) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.other_pay),
                    style = text12,
                    color = SecondaryColor,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                AsyncImage(
                    imageUrl = payment?.iconUrl ?: "",
                    modifier = Modifier.size(33.dp)

                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Text(
                    text = "${price}",
                    style = text16Medium,
                    color = TextColor,
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = stringResource(id = R.string.currency_1, getCurrency()),
                    style = text13,
                    color = SecondaryColor
                )

            }
        }
    }
}