package com.selsela.takeefapp.ui.order.cell

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
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.Gray
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun SpecialOrderItem(
    onClick: () -> Unit
) {
    Card(
        Modifier
            .padding(bottom = 8.4.dp)
            .fillMaxSize()
            .requiredHeight(187.dp)
            .padding(bottom = 8.4.dp)
            .clickable {
                onClick()
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
                        text = "رقم الطلب",
                        style = text11,
                        color = SecondaryColor
                    )
                    Text(
                        text = "#12342",
                        style = text16Bold,
                        color = TextColor,
                        modifier = Modifier.paddingTop(9)
                    )
                }
                DateView()

            }
            Row(
                Modifier
                    .paddingTop(9)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "عنوان : ",
                    style = text11,
                    color = SecondaryColor
                )
                Text(
                    text = "اسم العنوان يوضع هنا في هذه المساحة",
                    style = text12,
                    color = TextColor
                )
            }
            Text(
                text = "التفاصيل",
                style = text11,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(8)
            )
            Row(Modifier.fillMaxWidth()
                ,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد هذا النص من مولد النص العربى، حيث يمكنك أن تولد مثل هذا النص أو العديد من النصوص الأخرى إضافة إلى زيادة عدد الحروف التى يولدها التطبيق.",
                    style = text12,
                    color = TextColor.copy(0.56f),
                    maxLines = 2,
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