package com.selsela.takeefapp.ui.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun TermsView() {
    Column(
        Modifier
            .padding(top = 18.dp)
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 29.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "الشروط الاحكام",
            style = text16Medium,
            color = TextColor
        )

        Text(
            text = stringResource(id = R.string.temp_details),
            style = text12,
            color = TextColor,
            modifier = Modifier.paddingTop(24)
        )
    }
}