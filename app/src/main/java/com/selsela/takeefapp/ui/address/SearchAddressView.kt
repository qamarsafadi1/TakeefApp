package com.selsela.takeefapp.ui.address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.SearchAddressBar
import com.selsela.takeefapp.ui.theme.DividerColor
import com.selsela.takeefapp.ui.theme.FavBg
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColorAlpha
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun SearchAddressView(
    query: String?) {
    query?.log("query")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            SearchView(query)
            val isFav = query.equals("none")
            Text(
                text = if (isFav.not()) stringResource(R.string.search_result) else stringResource(R.string.fav_addresses),
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(20.9)
            )

            Column(
                modifier = Modifier
                    .paddingTop(3)
                    .fillMaxWidth()
            ) {
                repeat(4) {
                    if (isFav) {
                        FavItem()
                    } else {
                        AddressItem()
                    }
                }
            }


        }

    }
}

@Preview
@Composable
private fun FavItem() {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
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
                    Text(
                        text = stringResource(R.string.temp_address_name),
                        style = text12Meduim,
                        color = TextColor
                    )
                    Text(
                        text = "المنقطة ـ المدينة ، الاشعر ، اسم الCA 95673",
                        style = text12,
                        color = TextColor,
                        modifier = Modifier.paddingTop(7)
                    )
                    Text(
                        text = "شارع عبد العزيز م، مدينة السالم",
                        style = text12,
                        color = TextColor,
                        modifier = Modifier.paddingTop(7)
                    )
                }
                IconButton(
                    modifier = Modifier
                        .paddingTop(15)
                        .clip(CircleShape)
                        .background(FavBg)
                        .size(30.dp),
                    onClick = {}
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.favorite
                        ),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Red)
                    )
                }

            }
        }
        Divider(
            Modifier
                .padding(top = 14.dp)
                .fillMaxWidth(),
            color = DividerColor,
            thickness = 1.dp
        )
    }

}

@Preview
@Composable
private fun AddressItem() {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
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
                Text(
                    text = "المنقطة ـ المدينة ، الاشعر ، اسم الCA 95673",
                    style = text12,
                    color = TextColor
                )
                Text(
                    text = "شارع عبد العزيز م، مدينة السالم",
                    style = text12,
                    color = TextColor,
                    modifier = Modifier.paddingTop(7)
                )
            }
            IconButton(
                modifier = Modifier
                    .paddingTop(15)
                    .clip(CircleShape)
                    .background(FavBg)
                    .size(30.dp),
                onClick = {}
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.favorite
                    ),
                    contentDescription = ""
                )
            }


        }

        Divider(
            Modifier
                .padding(top = 14.dp)
                .fillMaxWidth(),
            color = DividerColor,
            thickness = 1.dp
        )
    }
}

@Composable
private fun SearchView(query: String?) {
    var text by remember {
        if (query.isNullOrEmpty() || query == "none")
            mutableStateOf("")
        else mutableStateOf(query)

    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(48.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(6.dp),
        backgroundColor = Color.Transparent,
        border = BorderStroke(1.dp, SecondaryColorAlpha)
    ) {
        SearchAddressBar(text) {
            text = it
        }
    }

}