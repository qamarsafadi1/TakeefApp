package com.selsela.takeefapp.ui.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.order.AddedCostSheet
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text40
import com.selsela.takeefapp.ui.wallet.cell.TransactionItem
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Preview
@Composable
fun WalletScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.walletbg),
            contentDescription = ""
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CurrentBalance()
            TransactionList()
        }
    }

}

@Composable
private fun TransactionList() {
    Column(
        Modifier
            .paddingTop(6)
            .fillMaxWidth()
            .background(TextColor, RoundedCornerShape(33.dp))
            .padding(horizontal = 22.dp)
            .padding(top = 26.dp, bottom = 96.dp)
    ) {

        Text(
            text = "سجل العلميات",
            style = text14,
            color = SecondaryColor
        )

        LazyColumn(
            modifier = Modifier
                .paddingTop(33)
                .fillMaxWidth()
        ) {
            items(5) {
                TransactionItem(it)
            }
        }
    }
}

@Composable
private fun CurrentBalance() {
    Column(
        Modifier
            .fillMaxWidth()
            .requiredHeight(132.dp)
            .background(LightBlue.copy(0.28f), RoundedCornerShape(33.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "الرصيد الحالي",
            style = text14,
            color = SecondaryColor
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.paddingTop(9)
        ) {
            Text(
                text = "250",
                style = text40,
                color = LightBlue
            )
            Text(
                text = "رس",
                style = text14,
                color = TextColor,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}