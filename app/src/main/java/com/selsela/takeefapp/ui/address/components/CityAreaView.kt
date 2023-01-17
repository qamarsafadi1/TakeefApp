package com.selsela.takeefapp.ui.address.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.text11

@Composable
fun CityAreaView(
    onAreaClick: () -> Unit,
    onCityClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        CityView(Modifier.weight(1f)) {
            onCityClick()
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
        AreaView(
            modifier = Modifier.weight(1f)
        ) {
            onAreaClick()
        }
    }
}

@Composable
private fun AreaView(
    modifier: Modifier,
    onAreaClick: () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.city),
            style = text11,
            color = SecondaryColor
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .requiredHeight(46.dp)
                .background(TextFieldBg, RoundedCornerShape(8.dp))
                .border(1.dp, color = BorderColor, RoundedCornerShape(8.dp))
                .clickable(onClick = {
                    onAreaClick()
                })
                .padding(horizontal = 16.dp)

        ) {
            Text(
                stringResource(id = R.string.city_name),
                style = text11,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(id = R.drawable.spinnerarrow),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)

            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CityView(
    weight: Modifier,
    onCityClick: () -> Unit
) {

    Column(weight) {
        Text(
            text = stringResource(R.string.area),
            style = text11,
            color = SecondaryColor
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .requiredHeight(46.dp)
                .background(TextFieldBg, RoundedCornerShape(8.dp))
                .border(1.dp, color = BorderColor, RoundedCornerShape(8.dp))
                .clickable(onClick = {
                    onCityClick()
                })
                .padding(horizontal = 16.dp)

        ) {
            Text(
                stringResource(id = R.string.area_name),
                style = text11,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(id = R.drawable.spinnerarrow),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)

            )
        }
    }
}