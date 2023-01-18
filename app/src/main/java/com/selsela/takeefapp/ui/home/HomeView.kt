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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.Service
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.home.item.DetailsView
import com.selsela.takeefapp.ui.home.item.ServiceItem
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.NoRippleTheme
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel(),
    goToSpecialOrder: () -> Unit,
    goToMyAccount: () -> Unit,
    goToLogin: () -> Unit,
) {
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
                Header(goToMyAccount)
                TitleView()

                val servicesList = remember { viewModel.services!!.map { it }.toMutableStateList() }
                LazyColumn(modifier = Modifier.padding(top = 24.dp)) {
                    items(servicesList,
                        key = { it.id }) {
                        AnimContent(
                            it,
                            onSelect = {
                                costVisible = !costVisible
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
                        onClick = { goToSpecialOrder() },
                        title = stringResource(R.string.special_order_add),
                    )
                }
            }

            // Bottom Cost Card
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
                                    it/2
                                },
                            ),
                            exit = slideOutVertically(
                                targetOffsetY = {
                                    it/2
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
                                text = stringResource(id = R.string.cost_1),
                                style = text14,
                                color = SecondaryColor2
                            )
                            Row(
                                modifier = Modifier.paddingTop(9),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
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
                            title = stringResource(R.string.order_follow_up),
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
private fun Header(goToMyAccount: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ElasticView(onClick = { goToMyAccount() }) {
            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "",
            )
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
}

@Composable
private fun SelectedServicesView() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row {
            Text(
                text = stringResource(id = R.string.maintinance_dot), style = text11,
                color = SecondaryColor2
            )
            Text(text = "00", style = text12, color = Color.White)
        }
        Spacer(modifier = Modifier.width(14.1.dp))
        Row {
            Text(
                text = stringResource(id = R.string.clean_dot), style = text11,
                color = SecondaryColor2
            )
            Text(text = "00", style = text12, color = Color.White)
        }
        Spacer(modifier = Modifier.width(14.1.dp))
        Row {
            Text(
                text = stringResource(id = R.string.installtion_dot), style = text11,
                color = SecondaryColor2
            )
            Text(text = "00", style = text12, color = Color.White)
        }
        Spacer(modifier = Modifier.width(14.1.dp))
    }
}

@Composable
private fun TitleView() {
    Text(
        text = stringResource(R.string.glad_to_serve),
        style = text18,
        modifier = Modifier.paddingTop(12)
    )
    Text(
        text = stringResource(R.string.serve_lbl),
        style = text14,
        modifier = Modifier.paddingTop(10),
        color = TextColor.copy(0.40f)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun AnimContent(
    service: Service,
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
        backgroundColor = service.cellBg(),
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
            ServiceItem(arrowVisibility, service)
            contentTransition.AnimatedContent { targetState ->
                if (targetState) {
                    onExpand(true)
                    arrowVisibility = false
                    DetailsView(service) {
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


