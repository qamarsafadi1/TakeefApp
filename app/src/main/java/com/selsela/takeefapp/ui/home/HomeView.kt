package com.selsela.takeefapp.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.CardColor
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.NoRippleTheme
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.TextColorHint
import com.selsela.takeefapp.ui.theme.TextColorHintAlpha60
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.utils.Extensions.Companion.convertToDecimalPatter
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeView(
    goToSpecialOrder: () -> Unit,
    goToMyAccount: () -> Unit,
    goToLogin: () -> Unit,
) {
    var paddingTitle by remember {
        mutableStateOf(27.3)
    }
    var paddingLabel by remember {
        mutableStateOf(13)
    }
    Color.White.ChangeStatusBarColor()
    // hide ripple effect
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            var costVisible by remember {
                mutableStateOf(false)
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 45.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ElasticView(onClick = { goToMyAccount() }) {
                        Image(painter = painterResource(id = R.drawable.menu), contentDescription = "",)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.notificationnew),
                        contentDescription = ""
                    )
                }
                AppLogoImage(
                    modifier = Modifier.size(
                        width = 128.05.dp,
                        height = 36.34.dp
                    )
                )
                TitleView(paddingTitle, paddingLabel)

                LazyColumn(modifier = Modifier.padding(top = 24.dp)) {
                    items(3) {
                        AnimContent(
                            onSelect = {
                                costVisible = !costVisible
                            }
                        ) {
                            if (it) {
                                paddingTitle = 12.0
                                paddingLabel = 5
                            } else {
                                paddingTitle = 27.3
                                paddingLabel = 13

                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    ElasticButton(
                        onClick = { goToSpecialOrder() }, title = "   +  طلب خاص",
                    )
                }
            }
            AnimatedVisibility(
                visible = costVisible,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier
                        .animateEnterExit(
                            // Slide in/out the inner box.
                            enter = slideInVertically(
                                initialOffsetY = {
                                    it / 2
                                },
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = {
                                    it / 2
                                },
                            ),
                        )
                        .fillMaxWidth()
                        .fillMaxHeight(0.14f)
                        .background(TextColor, RoundedCornerShape(topEnd = 45.dp, topStart = 45.dp))
                        .padding(horizontal = 20.dp, vertical = 22.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cost),
                                style = text14,
                                color = SecondaryColor2
                            )
                            Row(modifier = Modifier.paddingTop(9)) {
                                Text(
                                    text = "300", style = text16Medium,
                                    color = Color.White
                                )
                                Text(
                                    text = stringResource(id = R.string.currency),
                                    style = text11,
                                    color = SecondaryColor2,
                                    modifier = Modifier.padding(start = 4.3.dp)
                                )
                            }
                        }
                        ElasticButton(
                            onClick = { goToLogin() },
                            title = "متابعة الطلب",
                            icon = R.drawable.forward_arrow,
                            modifier = Modifier
                                .width(150.dp)
                                .height(48.dp)
                        )
                    }
                    SelectedServicesView()
                }
            }


        }
    }
}

@Composable
private fun SelectedServicesView() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row {
            Text(
                text = "صيانة : ", style = text11,
                color = SecondaryColor2
            )
            Text(text = "00", style = text12, color = Color.White)
        }
        Spacer(modifier = Modifier.width(14.1.dp))
        Row {
            Text(
                text = "تنظيف : ", style = text11,
                color = SecondaryColor2
            )
            Text(text = "00", style = text12, color = Color.White)
        }
        Spacer(modifier = Modifier.width(14.1.dp))
        Row {
            Text(
                text = "تركيب : ", style = text11,
                color = SecondaryColor2
            )
            Text(text = "00", style = text12, color = Color.White)
        }
        Spacer(modifier = Modifier.width(14.1.dp))
    }
}

@Composable
private fun TitleView(paddingTitle: Double, paddingLabel: Int) {
    Text(
        text = stringResource(R.string.glad_to_serve),
        style = text18,
        modifier = Modifier.paddingTop(paddingTitle)
    )
    Text(
        text = stringResource(R.string.serve_lbl),
        style = text14,
        modifier = Modifier.paddingTop(paddingLabel),
        color = TextColor.copy(0.40f)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun AnimContent(
    onSelect: (Boolean) -> Unit,
    onExpand: (Boolean) -> Unit
) {
    var itemExpanded by remember { mutableStateOf(false) }
    var arrowVisibility by remember { mutableStateOf(true) }
    val contentTransition = updateTransition(itemExpanded, label = "Expand")
    Card(
        modifier = Modifier
            .padding(bottom = 9.dp)
            .fillMaxWidth(),
        backgroundColor = CardColor,
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp,
        onClick = { itemExpanded = !itemExpanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start

        ) {
            ServiceItem(arrowVisibility)

            contentTransition.AnimatedContent { targetState ->
                if (targetState) {
                    onExpand(true)
                    arrowVisibility = false
                    DetailsView {
                        itemExpanded = !itemExpanded
                        onSelect(itemExpanded)
                    }
                } else {
                    arrowVisibility = true
                    onExpand(false)
                }
            }
        }
    }
}

@Composable
private fun DetailsView(onCollapse: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            thickness = 1.dp,
            color = LightBlue.copy(.14f),
            modifier = Modifier.padding(
                top = 38.dp,
                bottom = 28.dp
            )
        )
        Text(
            text = stringResource(R.string.condation_type),
            style = text12,
            color = TextColorHint
        )
        Column(modifier = Modifier.padding(top = 12.dp)) {
            repeat(2) {
                ConditionTypeView()
            }
            Row(
                modifier = Modifier.padding(top = 49.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "التكلفة : ",
                    style = text16Medium
                )
                Text(
                    text = "100",
                    style = text16Medium
                )
                Text(
                    text = "ر.س",
                    style = text12Meduim,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Text(
                text = "يتم خصم المبلغ المدفوع من قيمة التكلفة الاجمالية للصيانة ",
                style = text12,
                color = TextColorHintAlpha60,
                modifier = Modifier.padding(top = 12.dp)
            )

            Divider(
                thickness = 1.dp,
                color = LightBlue.copy(.14f),
                modifier = Modifier.padding(
                    top = 21.dp,
                    bottom = 28.dp
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 23.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ElasticView(
                    onClick = { },
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.topward_arrow),
                        contentDescription = ""
                    )
                }

                ElasticButton(
                    onClick = { onCollapse() },
                    title = stringResource(R.string.select),
                    modifier = Modifier
                        .width(167.dp)
                        .requiredHeight(48.dp)
                )


            }

        }

    }
}

@Composable
private fun ConditionTypeView() {
    var count by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .padding(bottom = 7.dp)
            .fillMaxWidth()
            .requiredHeight(60.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "مكيف شباك",
            style = text12Meduim
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "",
                modifier = Modifier.clickable {
                    count++
                }
            )

            Text(
                text = count.convertToDecimalPatter(),
                style = text14Meduim,
                modifier = Modifier.padding(horizontal = 18.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.minus),
                contentDescription = "",
                modifier = Modifier.clickable {
                    if (count != 0)
                        count--
                }
            )

        }

    }
}

@Composable
private fun ServiceItem(arrowVisibility: Boolean) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(140.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.service1),
            contentDescription = ""
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 19.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "طلب خدمة",
                style = text12,
                color = TextColor.copy(0.44f)
            )
            Text(
                text = "التنظيف",
                style = text20,
                color = TextColor
            )
            Text(
                text = "صيانة جميع انواع المكيفات",
                style = text12,
                color = TextColor.copy(0.44f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 18.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.unchecked),
                contentDescription = ""
            )

            if (arrowVisibility) {
                Image(
                    painter = painterResource(id = R.drawable.backward_arrow),
                    contentDescription = ""
                )
            }

        }
    }
}