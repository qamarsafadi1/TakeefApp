package com.selsela.takeefapp.ui.order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text10
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Bold
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.ui.theme.text8
import com.selsela.takeefapp.utils.Constants.RIGHT
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Preview
@Composable
fun OrdersView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .padding(horizontal = 19.dp)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            items(2) {
                OrderItem()
            }
        }
    }
}

@Composable
private fun OrderItem() {
    Card(
        modifier = Modifier
            .padding(bottom = 11.dp, top = 11.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 191.dp),
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
                    DateView()

                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                StepperView()
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
                            text = "موعد الزيارة",
                            style = text11,
                            color = SecondaryColor,
                            modifier = Modifier.padding(start = 6.4.dp)
                        )

                    }
                    DateTimeView()
                }

                Row(
                    Modifier.paddingTop(10)
                        .fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.moneyicon),
                        contentDescription = ""
                    )
                    Text(
                        text = "التكلفة  : ",
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
                            text = "ر.س",
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
                    onClick = { /*TODO*/ }, title = "متابعة المسار",
                    icon = R.drawable.map,
                    iconGravity = RIGHT ,
                    modifier = Modifier
                        .paddingTop(13)
                        .requiredHeight(36.dp)
                        .width(137.dp),
                    buttonBg = LightBlue
                )
                AnimatedVisibility(visible = false) {
                    ElasticButton(
                        onClick = { /*TODO*/ }, title = "تقييم",
                        icon = R.drawable.star,
                        iconGravity = RIGHT,
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
private fun DateView() {
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

@Composable
private fun StepperView(
    currentStep: Int = 0,
    items: List<String> = listOf(
        "استلام الطلب",
        "متوجه لك",
        "جاري التنفيذ",
        "انتهاء الطلب"
    )
) {
    Box(
        modifier = Modifier.wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            Modifier
                .fillMaxWidth(0.6f)
                .padding(vertical = 10.dp)
                .align(Alignment.TopCenter),
            thickness = 4.dp,
            color = SecondaryColor2.copy(0.32f)
        )
        LazyRow(
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            itemsIndexed(
                items
            ) { index, step ->
                Step(
                    step, currentStep == index,
                    index < currentStep
                )
            }

        }

    }
}

@Composable
private fun Step(
    step: String,
    isCurrent: Boolean,
    isCompleted: Boolean,
) {
    val drawable: Int by animateIntAsState(
        targetValue = if (isCurrent) {
            R.drawable.checked
        } else {
            if (isCompleted)
                R.drawable.checked
            else R.drawable.uncomplete

        },
        animationSpec = tween(
            durationMillis = 1000,
        )
    )
    val textColor: Color by animateColorAsState(
        targetValue = if (isCurrent) {
            TextColor
        } else {
            SecondaryColor
        },
        animationSpec = tween(
            durationMillis = 1000,
        )
    )
    Spacer(modifier = Modifier.width(10.dp))
    Column(
        Modifier
            .wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = ""
        )
        Text(
            text = step,
            modifier = Modifier
                .paddingTop(9)
                .width(30.dp),
            style = text8,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SelectedServicesView() {
    Row(
        modifier = Modifier
            .paddingTop(11)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Row {
            Text(
                text = "صيانة : ", style = text11,
                color = SecondaryColor
            )
            Text(text = "00", style = text12, color = TextColor)
        }
        Spacer(modifier = Modifier.width(51.1.dp))
        Row {
            Text(
                text = "تنظيف : ", style = text11,
                color = SecondaryColor
            )
            Text(text = "00", style = text12, color = TextColor)
        }
        Spacer(modifier = Modifier.width(51.1.dp))
        Row {
            Text(
                text = "تركيب : ", style = text11,
                color = SecondaryColor
            )
            Text(text = "00", style = text12, color = TextColor)
        }
        Spacer(modifier = Modifier.width(14.1.dp))
    }
}
