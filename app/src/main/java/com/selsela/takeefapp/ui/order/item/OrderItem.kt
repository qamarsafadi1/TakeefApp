package com.selsela.takeefapp.ui.order.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.model.order.WorkPeriod
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.SelectedServicesView
import com.selsela.takeefapp.ui.common.StepperView
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text10
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Bold
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Constants.FINISHED
import com.selsela.takeefapp.utils.Constants.ON_WAY
import com.selsela.takeefapp.utils.Constants.RECEIVED
import com.selsela.takeefapp.utils.DateHelper.Companion.getOrderDate
import com.selsela.takeefapp.utils.Extensions.Companion.getCurrency
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun OrderItem(
    order: Order,
    onClick: (Int) -> Unit,
    onRateClick: (Int) -> Unit,
    onRouteClick: (LatLng, LatLng) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = 11.dp, top = 11.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 191.dp)
            .clickable {
                onClick(order.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 191.dp)
                .padding(
                    horizontal = 10.dp,
                    vertical = 21.dp
                )
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.order_number),
                        style = text11,
                        color = SecondaryColor
                    )
                    Text(
                        text = "#${order.number}",
                        style = text16Bold,
                        color = TextColor
                    )
                    DateView(order.createdAt)
                }

                if (order.case.id != 6) {
                    StepperView(
                        Modifier
                            .fillMaxWidth()
                            .weight(1.5f),
                        items = LocalData.cases?.filter { it.id != 6 },
                        currentStep = order.logs.distinctBy { it.case.id }.lastIndex
                    )
                } else {
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

            Column(
                modifier = Modifier
                    .paddingTop(10)
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 63.dp)
                    .background(
                        LightBlue.copy(0.10f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 6.3.dp, vertical = 11.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.date_line),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(SecondaryColor)
                        )
                        Text(
                            text = stringResource(id = R.string.visit_date_1),
                            style = text11,
                            color = SecondaryColor,
                            modifier = Modifier.padding(start = 6.4.dp)
                        )

                    }
                    DateTimeView(order.orderDate, order.workPeriod)
                }

                Row(
                    Modifier
                        .paddingTop(10)
                        .fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.moneyicon),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(id = R.string.cost_dot),
                        style = text11,
                        color = TextColor,
                        modifier = Modifier.padding(start = 9.dp)
                    )

                    Row {
                        Text(
                            text = "${order.grandTotal}",
                            style = text12Bold,
                            color = TextColor,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.currency_1, getCurrency()),
                            style = text10,
                            color = TextColor,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                    }

                }
            }

            SelectedServicesView(orderServices = order.orderService)
            if (order.case.id != RECEIVED) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    when (order.case.id) {
                        ON_WAY -> {
                            ElasticButton(
                                onClick = {
                                    onRouteClick(
                                        LatLng(order.address.latitude, order.address.longitude),
                                        LatLng(
                                            order.supervisor?.latitude ?: 0.0,
                                            order.supervisor?.longitude ?: 0.0
                                        )
                                    )
                                },
                                title = stringResource(id = R.string.follow_route),
                                icon = R.drawable.map,
                                iconGravity = Constants.RIGHT,
                                modifier = Modifier
                                    .paddingTop(13)
                                    .requiredHeight(36.dp)
                                    .defaultMinSize(minWidth = if (LocalData.appLocal == "ar") 137.dp else 157.dp),
                                buttonBg = LightBlue
                            )
                        }

                        FINISHED -> {
                            if (order.case.canRate == 1 && order.isRated == 0) {
                                ElasticButton(
                                    onClick = { onRateClick(order.id) },
                                    title = stringResource(id = R.string.rate),
                                    icon = R.drawable.star,
                                    iconGravity = Constants.RIGHT,
                                    modifier = Modifier
                                        .paddingTop(13)
                                        .requiredHeight(36.dp)
                                        .width(137.dp),
                                    buttonBg = TextColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateView(
    date: String = ""
) {
    var orderDate = listOf<String>()
    orderDate = if (date != "") getOrderDate(date) else listOf()
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text =
            if (orderDate.isEmpty().not())
                orderDate.last()
            else "AM",
            style = text11,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text =
            if (orderDate.isEmpty().not())
                "${orderDate[3]}:${orderDate[4]}"
            else
                "08:30",
            style = text11,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = ".",
            style = text11,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = if (orderDate.isEmpty().not())
                "${orderDate[0]}-${orderDate[1]}-${orderDate[2]}"
            else
                "29-11-2022",
            style = text11,
            color = SecondaryColor
        )
    }
}

@Composable
private fun DateTimeView(orderDate: String, fromTo: WorkPeriod) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = fromTo.getTimeFromFormatted(),
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "-",
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = fromTo.getTimeToFormatted(),
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = ".",
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = orderDate,
            style = text12,
            color = TextColor
        )
    }
}

