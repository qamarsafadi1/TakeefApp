package com.selsela.takeefapp.ui.address.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.IconedButton
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text20Book
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun DatePickerView(
    onBack: () -> Unit,
    goToReviewOrder: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 40.dp)
            .padding(bottom = 20.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                text = stringResource(R.string.select_lbl),
                style = text14,
                color = SecondaryColor,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.clickable {
                    onBack()
                },
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.back), style = text11,
                    color = SecondaryColor
                )

            }
        }
        Text(
            text = stringResource(R.string.visit_date),
            style = text20Book,
            color = Color.White,
            modifier = Modifier.paddingTop(13.5)
        )
        Calendar()

        var check by remember {
            mutableStateOf(-1)
        }
        PmAmView(check) {
            check = it
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconedButton(
                onClick = {
                    goToReviewOrder()
                },
                icon = R.drawable.forward_arrow,
                modifier = Modifier
                    .paddingTop(5)
                    .requiredWidth(width = 65.dp)
                    .requiredHeight(48.dp)
            )

        }

    }

}

@Composable
fun PmAmView(selectedItem: Int = -1, onCheck: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        repeat(2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(48.dp)
                    .background(TextFieldBg, RoundedCornerShape(11.dp))
                    .border(width = 1.dp, color = BorderColor, RoundedCornerShape(11.dp))
                    .padding(horizontal = 15.dp)
                    .clickable(

                    ) {
                        onCheck(it)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = if (it == 0) stringResource(R.string.am_lbl) else stringResource(R.string.pm_lbl),
                    style = text14,
                    color = Color.White
                )

                Text(
                    text = if (it == 0) "08:00 AM - 12:00 PM" else "12:00 PM - 05:00 PM",
                    style = text14,
                    color = SecondaryColor
                )
                Image(
                    painter =
                    if (it == selectedItem)
                        painterResource(id = R.drawable.checked)
                    else painterResource(id = R.drawable.uncheckedrb),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}
