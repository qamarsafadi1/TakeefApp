package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.address.components.GoogleMapView
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Preview
@Composable
fun OrderRouteView() {

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapView(
            markerDrawable = R.drawable.markerroute
        )


        Column(
            modifier = Modifier
                .padding(bottom = 28.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArrivalView()
            Card(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .requiredHeight(93.dp),
                shape = RoundedCornerShape(18.dp),
                backgroundColor = TextColor,
                elevation = 12.dp
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 9.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "",
                            modifier = Modifier.clip(CircleShape)
                        )
                        Column(
                            modifier = Modifier.padding(start = 8.1.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.maintinance_supervisor),
                                style = text12,
                                color = SecondaryColor
                            )
                            Text(
                                text = "محمد احمد على",
                                style = text14,
                                color = Color.White,
                                modifier = Modifier.paddingTop(6)
                            )
                        }
                    }

                    Row {
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                painter = painterResource(id = R.drawable.chatbtn),
                                contentDescription = ""
                            )
                        }
                        Spacer(modifier = Modifier.width(26.7.dp))

                        IconButton(onClick = { /*TODO*/ }) {
                            Image(
                                painter = painterResource(id = R.drawable.callbtn),
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArrivalView() {
    Card(
        modifier = Modifier
            .padding(horizontal = 31.dp)
            .fillMaxWidth()
            .requiredHeight(46.dp),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 18.dp),
        backgroundColor = Color.White,
        elevation = 6.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.arrive_within),
                style = text12,
                color = SecondaryColor
            )

            Row {

                Text(
                    text = "5",
                    style = text12Meduim,
                    color = TextColor
                )

                Text(
                    text = stringResource(R.string.mintues),
                    style = text12,
                    color = SecondaryColor,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}