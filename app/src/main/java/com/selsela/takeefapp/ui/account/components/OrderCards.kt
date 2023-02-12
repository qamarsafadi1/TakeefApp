package com.selsela.takeefapp.ui.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.utils.Constants.NEW_ORDER
import com.selsela.takeefapp.utils.Constants.UPCOMING_ORDERS
import com.selsela.takeefapp.utils.Extensions.Companion.convertToDecimalPatter
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun OrderCards(
    uiState: AuthUiState,
    goToSpecialOrder: () -> Unit,
    goToOrder: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 17.dp)
            .paddingTop(15)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(88.dp)
                .clip(RoundedCornerShape(13.dp))
                .clickable {
                    goToOrder(NEW_ORDER)
                },
            shape = RoundedCornerShape(13.dp),
            elevation = 20.dp,
            backgroundColor = TextColor
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${uiState.user?.newOrders?.convertToDecimalPatter()}",
                    style = text16Medium,
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.new_orders),
                    style = text12,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(88.dp)
                .clip(RoundedCornerShape(13.dp))
                .clickable {
                    goToSpecialOrder()
                },
            shape = RoundedCornerShape(13.dp),
            elevation = 20.dp,
            backgroundColor = LightBlue
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${uiState.user?.specificOrders?.convertToDecimalPatter()}",
                    style = text16Medium,
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.special_order),
                    style = text12,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(88.dp)
                .clip(RoundedCornerShape(13.dp))
                .clickable {
                    goToOrder(UPCOMING_ORDERS)
                },
            shape = RoundedCornerShape(13.dp),
            elevation = 20.dp,
            backgroundColor = Purple40
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${uiState.user?.processingOrders?.convertToDecimalPatter()}",
                    style = text16Medium,
                    color = Color.White
                )
                Text(
                    text = stringResource(R.string.ongoing_order),
                    style = text12,
                    color = Color.White
                )
            }
        }
    }
}
