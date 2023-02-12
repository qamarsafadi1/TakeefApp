package com.selsela.takeefapp.ui.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.order.SelectedServicesOrder
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.Extensions.Companion.convertToDecimalPatter
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
@OptIn(ExperimentalAnimationApi::class)
 fun BottomCostCard(
    modifier: Modifier,
    costVisible: Boolean,
    servic: SelectedServicesOrder,
    goToLogin: () -> Unit,
    viewModel: HomeViewModel
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = costVisible,
        enter = fadeIn(),
        exit = fadeOut(),
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
                .fillMaxHeight(0.16f)
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
                            text = "${servic.totalServicesPrice?.value}",
                            style = text16Medium,
                            color = Color.White
                        )
                        Text(
                            text = stringResource(id = R.string.currency, Extensions.getCurrency()),
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
            SelectedServicesView(viewModel.selectedOrderService.value)
        }
    }
}


@Composable
 fun SelectedServicesView(service: SelectedServicesOrder) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (service.maintenanceCount?.value != 0) {
            Row {
                Text(
                    text = stringResource(id = R.string.maintinance_dot), style = text11,
                    color = SecondaryColor2
                )
                service.maintenanceCount?.value?.convertToDecimalPatter()?.let {
                    Text(
                        text = it,
                        style = text12,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.1.dp))
        }
        if (service.cleanCount?.value != 0) {
            Row {
                Text(
                    text = stringResource(id = R.string.clean_dot), style = text11,
                    color = SecondaryColor2
                )
                service.cleanCount?.value?.convertToDecimalPatter()?.let {
                    Text(
                        text = it,
                        style = text12,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.1.dp))
        }
        if (service.installCount?.value != 0) {
            Row {
                Text(
                    text = stringResource(id = R.string.installtion_dot), style = text11,
                    color = SecondaryColor2
                )
                service.installCount?.value?.convertToDecimalPatter()?.let {
                    Text(
                        text = it,
                        style = text12,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.1.dp))
        }

    }
}
