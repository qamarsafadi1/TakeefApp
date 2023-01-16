package com.selsela.takeefapp.ui.wallet.cell

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.theme.DarkGray
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text13Meduim
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun TransactionItem(it: Int){
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
                    text = "دفع تكلفة طلب",
                    style = text12,
                    color = SecondaryColor
                )
                Text(
                    text = "#1312",
                    style = text13Meduim,
                    color = Color.White,
                    modifier = Modifier.paddingTop(1)
                )
            }

            Text(
                text = "-200",
                style = text14Meduim,
                color = if (it % 2 == 0)
                    Red
                else Purple40
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