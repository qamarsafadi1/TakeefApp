package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.StepperView
import com.selsela.takeefapp.ui.order.cell.DateView
import com.selsela.takeefapp.ui.order.rate.RateSheet
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.ColorAccent
import com.selsela.takeefapp.ui.theme.DividerColor
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
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.utils.Constants.ACCEPT
import com.selsela.takeefapp.utils.Constants.REJECT
import com.selsela.takeefapp.utils.Constants.RIGHT
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderDetailsView(
    onBack: () -> Unit
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
                        .verticalScroll(rememberScrollState())
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
                                text = "رقم الطلب",
                                style = text11,
                                color = SecondaryColor
                            )
                            Text(
                                text = "#12342",
                                style = text16Bold,
                                color = TextColor
                            )
                        }
                        DateView()

                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    StepperView(
                        modifier = Modifier

                            .fillMaxWidth(),
                        isDetails = true,
                    )
                    MaintenanceCostWarning()
                    AcceptRejectButtons() {
                        coroutineScope.launch {
                            when (it) {
                                REJECT -> {
                                    if (rateSheetState.isVisible)
                                        rateSheetState.hide()
                                    else rateSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }

                                ACCEPT -> {
                                    if (paySheetState.isVisible)
                                        paySheetState.hide()
                                    else paySheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }

                        }
                    }
                    Divider(
                        thickness = 1.dp,
                        color = DividerColor,
                        modifier = Modifier.padding(top = 27.6.dp)
                    )
                    VisitDateView()
                    Divider(
                        thickness = 1.dp,
                        color = DividerColor,
                        modifier = Modifier.padding(top = 8.6.dp)
                    )
                    SelectedAddressView()
                    CostView()
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
                            text = "تفاصيل الخدمة",
                            style = text14,
                        )
                    }

                    Column(
                        Modifier
                            .padding(horizontal = 7.dp)
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        repeat(2) {
                            ServiceItem()
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
private fun ServiceItem() {
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
                    text = "خدمة صيانة",
                    style = text12,
                    color = TextColor,
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                androidx.compose.material3.Text(
                    text = "100",
                    style = text16Medium,
                    color = TextColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = "رس",
                    style = text13,
                    color = SecondaryColor
                )

            }
        }

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
                    text = "مكيف شباك",
                    style = text12,
                    color = SecondaryColor,
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                androidx.compose.material3.Text(
                    text = "2",
                    style = text16Medium,
                    color = TextColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = "جهاز",
                    style = text13,
                    color = SecondaryColor
                )

            }
        }
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
                    text = "مكيف سبليت",
                    style = text12,
                    color = SecondaryColor,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                androidx.compose.material3.Text(
                    text = "2",
                    style = text16Medium,
                    color = TextColor,
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = "جهاز",
                    style = text13,
                    color = SecondaryColor
                )

            }
        }

    }
}

@Composable
private fun CostView() {
    Column(
        Modifier
            .padding(top = 21.dp)
            .padding(horizontal = 7.3.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 134.dp)
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
                    text = "100",
                    style = text16Medium,
                    color = TextColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = "رس",
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
                    text = "100",
                    style = text16Medium,
                    color = TextColor
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = "رس",
                    style = text13,
                    color = SecondaryColor
                )

            }
        }
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
                Image(
                    painter = painterResource(id = R.drawable.visa),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Text(
                    text = "100",
                    style = text16Medium,
                    color = TextColor,
                )
                Spacer(modifier = Modifier.width(3.dp))
                androidx.compose.material3.Text(
                    text = "رس",
                    style = text13,
                    color = SecondaryColor
                )

            }
        }

    }
}

@Composable
private fun AcceptRejectButtons(onClick: (Int) -> Unit) {
    Row(
        Modifier
            .padding(top = 11.dp)
            .padding(horizontal = 21.dp)
            .fillMaxWidth()
    ) {

        ElasticButton(
            onClick = { onClick(REJECT) },
            title = "رفض",
            colorBg = Red,
            modifier = Modifier
                .weight(1f)
                .requiredHeight(36.dp)
        )
        Spacer(modifier = Modifier.width(18.dp))
        ElasticButton(
            onClick = {
                onClick(ACCEPT)
            },
            title = "قبول ودفع",
            icon = R.drawable.pay,
            modifier = Modifier
                .weight(1f)
                .requiredHeight(36.dp),
            iconGravity = RIGHT
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
            text = " يوجد تكلفة اضافية للصيانة في حال القبول\n والدفع",
            style = text11NoLines,
            color = TextColor,
            modifier = Modifier.padding(start = 8.8.dp)
        )
    }
}

@Composable
private fun VisitDateView() {
    Row(
        modifier = Modifier
            .padding(top = 13.dp)
            .padding(horizontal = 9.6.dp)
            .fillMaxWidth(),
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
            androidx.compose.material3.Text(
                text = "موعد الزيارة",
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.padding(start = 8.dp)
            )

        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(5.dp))
                androidx.compose.material3.Text(
                    text = "فترة صباحية",
                    style = text14,
                    color = TextColor
                )
            }
            androidx.compose.material3.Text(
                text = stringResource(R.string._08_00_am_12_00_pm),
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
private fun SelectedAddressView() {
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
                text = "المنقطة ـ المدينة ، الاشعر ، اسم الCA 95673",
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(11)
            )
            Text(
                text = "شارع عبد العزيز م، مدينة السالم",
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(9)
            )
        }

    }
}
