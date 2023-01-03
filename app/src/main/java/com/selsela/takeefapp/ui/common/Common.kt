package com.selsela.takeefapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.selsela.takeefapp.R

@Composable
fun AppLogoImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.splashlogo), contentDescription = "Logo",
        modifier = modifier
    )
}