package com.selsela.takeefapp.ui.account.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Bold


@Composable
fun Header(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onBack()
        }) {
            Image(
                painter = painterResource(id = R.drawable.backbutton),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        LogoutButton()
    }
    Row(
        Modifier
            .padding(horizontal = 33.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage()
        Text(
            text = "محمد صالح الجربوع",
            style = text16Bold,
            color = Color.White,
            modifier = Modifier.padding(start = 18.dp)
        )
    }
}

@Composable
private fun CircularImage() {
    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier =
            Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(72.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.tempimg), contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)
        )
    }
}

@Composable
private fun LogoutButton() {
    ElasticView(onClick = { /*TODO*/ }) {
        Row(
            modifier = Modifier
                .width(129.dp)
                .height(
                    38.dp
                )
                .background(
                    color = TextColor.copy(0.10f),
                    RoundedCornerShape(19.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout"
            )
            Text(
                text = stringResource(R.string.logout),
                style = text12,
                color = Color.White,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}