package com.selsela.takeefapp.ui.order.cell

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.SelectedServicesView
import com.selsela.takeefapp.ui.common.StepperView
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text10
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Bold
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun OrderItem(
    onClick: () -> Unit,
    onRouteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = 11.dp, top = 11.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 191.dp)
            .clickable {
                onClick()
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
                Column(Modifier.fillMaxWidth()
                    .weight(1f)) {
                    Text(
                        text = stringResource(R.string.order_number),
                        style = text11,
                        color = SecondaryColor
                    )
                    Text(
                        text = "#12342",
                        style = text16Bold,
                        color = TextColor
                    )
                    DateView()

                }

                StepperView(
                    Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                    items = listOf(
                        stringResource(R.string.recived_order),
                        stringResource(R.string.on_way),
                        stringResource(R.string.on_progress),
                        stringResource(R.string.done_order)
                    )
                )
            }

            Column(
                modifier = Modifier
                    .paddingTop(10)
                    .fillMaxWidth()
                    .requiredHeight(63.dp)
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
                    DateTimeView()
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
                            text = "300",
                            style = text12Bold,
                            color = TextColor,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.currency_1),
                            style = text10,
                            color = TextColor,
                            modifier = Modifier.padding(start = 5.dp)
                        )

                    }

                }
            }

            SelectedServicesView()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ElasticButton(
                    onClick = {onRouteClick() }, title = stringResource(id = R.string.follow_route),
                    icon = R.drawable.map,
                    iconGravity = Constants.RIGHT ,
                    modifier = Modifier
                        .paddingTop(13)
                        .requiredHeight(36.dp)
                        .width(if (LocalData.appLocal == "ar") 137.dp else 157.dp),
                    buttonBg = LightBlue
                )
                AnimatedVisibility(visible = false) {
                    ElasticButton(
                        onClick = { /*TODO*/ }, title =stringResource(id = R.string.rate),
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

@Composable
fun DateView() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "AM",
            style = text11,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "08:30",
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
            text = "29-11-2022",
            style = text11,
            color = SecondaryColor
        )
    }
}

@Composable
private fun DateTimeView() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "AM",
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "08:30",
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
            text = "29-11-2022",
            style = text12,
            color = TextColor
        )
    }
}

