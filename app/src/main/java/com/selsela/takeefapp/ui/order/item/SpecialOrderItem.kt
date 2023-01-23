package com.selsela.takeefapp.ui.order.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.special.SpecificOrder
import com.selsela.takeefapp.ui.theme.Gray
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun SpecialOrderItem(
    specificOrder: SpecificOrder,
    onClick: (Int) -> Unit
) {
    Card(
        Modifier
            .padding(bottom = 8.4.dp)
            .fillMaxSize()
            .requiredHeight(187.dp)
            .padding(bottom = 8.4.dp)
            .clickable {
                onClick(specificOrder.id)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 21.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.order_number),
                        style = text11,
                        color = SecondaryColor
                    )
                    Text(
                        text = "#${specificOrder.orderNumber}",
                        style = text16Bold,
                        color = TextColor,
                        modifier = Modifier.paddingTop(9)
                    )
                }
                DateView(specificOrder.createdAt)

            }
            Row(
                Modifier
                    .paddingTop(9)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.address_dot),
                    style = text11,
                    color = SecondaryColor
                )
                Text(
                    text = specificOrder.title,
                    style = text12,
                    color = TextColor
                )
            }
            Text(
                text = stringResource(R.string.detalis),
                style = text11,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(8)
            )
            Row(Modifier.fillMaxWidth()
                ,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = specificOrder.description,
                    style = text12,
                    color = TextColor.copy(0.56f),
                    maxLines = 2,
                    overflow =TextOverflow.Ellipsis,
                    modifier = Modifier.paddingTop(8).weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.itemarrow),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Gray)
                )
            }


        }
    }
}