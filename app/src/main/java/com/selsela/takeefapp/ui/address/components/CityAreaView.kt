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
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import androidx.compose.runtime.*
import com.selsela.takeefapp.ui.address.AddressViewModel
import com.selsela.takeefapp.utils.Extensions.Companion.log

@Composable
fun CityAreaView(
    onAreaClick: () -> Unit,
    addressViewModel: AddressViewModel,
    onCityClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        CityView(Modifier.weight(1f), addressViewModel.selectedAreaName) {
            onCityClick()
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
        AreaView(
            modifier = Modifier.weight(1f),
            addressViewModel.selectedCityName
        ) {
            onAreaClick()
        }
    }
}

@Composable
private fun AreaView(
    modifier: Modifier,
    selectedName: MutableState<String>,
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
                selectedName.value.ifEmpty { stringResource(id = R.string.city_name) },
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

@Composable
fun DistrictView(
    viewModel: AddressViewModel,
    modifier: Modifier = Modifier
        .paddingTop(9)
        .fillMaxWidth(),
    onAreaClick: () -> Unit
) {
    viewModel.selectedDistrictName.value.log("selectedDistrictName")
    Column(modifier) {
        Box(
            modifier = Modifier
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
                viewModel.selectedDistrictName.value.ifEmpty {
                    stringResource(id = R.string.district)
                },
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
    selectedName: MutableState<String>,
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
                selectedName.value.ifEmpty { stringResource(id = R.string.area_name) },
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