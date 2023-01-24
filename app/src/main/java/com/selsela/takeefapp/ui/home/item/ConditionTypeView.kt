package com.selsela.takeefapp.ui.home.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.AcType
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.Extensions.Companion.convertToDecimalPatter


@Composable
fun ConditionTypeView(
    acType: AcType,
    onCountChange: (Int,AcType) -> Unit
) {
    var count by remember { mutableStateOf(acType.count) }
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
            text = acType.name,
            style = text12Meduim
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "",
                modifier = Modifier.clickable {
                    count++
                    onCountChange(count, acType)
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
                    if (count != 0) {
                        count--
                        onCountChange(count, acType)
                    }
                }
            )

        }

    }
}