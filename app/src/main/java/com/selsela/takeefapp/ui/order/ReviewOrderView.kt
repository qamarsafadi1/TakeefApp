package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.DividerColor
import com.selsela.takeefapp.ui.theme.DividerColorAlpha
import com.selsela.takeefapp.ui.theme.FavBg
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Preview
@Composable
fun ReviewOrderView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.please_select_payment),
                style = text12,
                color = SecondaryColor,
            )
            SelectedAddressView()
            Divider(
                modifier = Modifier
                    .padding(vertical = 18.6.dp)
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = DividerColor
            )

            VisitDateView()
            Divider(
                modifier = Modifier
                    .padding(vertical = 18.6.dp)
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = DividerColor
            )

            Row(
                modifier = Modifier
                    .paddingTop(15.7)
                    .requiredHeight(39.dp)
                    .fillMaxWidth()
                    .background(color = SecondaryColor2, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.required_services),
                    style = text14,
                    color = TextColor,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = stringResource(R.string.cost),
                    style = text14,
                    color = TextColor,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

            }

        }
    }
}

@Composable
private fun VisitDateView() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.date_line),
                contentDescription = ""
            )
            Text(
                text = "موعد الزيارة",
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.padding(start = 8.dp)
            )

        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "فترة صباحية",
                    style = text14,
                    color = TextColor
                )
            }
            Text(
                text = "08:00 AM - 12:00 PM",
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(
                    7
                )
            )
        }
    }
}

@Composable
private fun SelectedAddressView() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = "",
            modifier = Modifier.padding(end = 6.6.dp, top = 20.dp)
        )
        Column(
            modifier = Modifier
                .paddingTop(20)
                .weight(1f)
        )
        {
            androidx.compose.material.Text(
                text = stringResource(R.string.selected_address),
                style = text12Meduim,
                color = SecondaryColor
            )
            androidx.compose.material.Text(
                text = "المنقطة ـ المدينة ، الاشعر ، اسم الCA 95673",
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(11)
            )
            androidx.compose.material.Text(
                text = "شارع عبد العزيز م، مدينة السالم",
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(9)
            )
        }

    }
}