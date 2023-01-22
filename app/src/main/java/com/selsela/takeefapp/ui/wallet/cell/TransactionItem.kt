package com.selsela.takeefapp.ui.wallet.cell

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.data.auth.model.wallet.Wallet
import com.selsela.takeefapp.ui.theme.DarkGray
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text13Meduim
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.Constants.DEBIT
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun TransactionItem(it: Wallet) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = it.action,
                    style = text12,
                    color = SecondaryColor
                )
                Text(
                    text = "#${it.reference}",
                    style = text13Meduim,
                    color = Color.White,
                    modifier = Modifier.paddingTop(1)
                )
            }

            Text(
                text = "${it.amount}",
                style = text14Meduim,
                color = if (it.type == DEBIT) Purple40 else Red,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }
        Divider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = DarkGray
        )
    }

}