package com.selsela.takeefapp.ui.intro.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.buttonText

@Composable
 fun StartNowButton() {
    Row(
        modifier = Modifier
            .requiredWidth(181.dp)
            .requiredHeight(56.dp)
            .background(TextColor, shape = RoundedCornerShape(28.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.start_now), style = buttonText,
            modifier = Modifier.padding(end = 23.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.forward_arrow),
            contentDescription = "arrow",
        )
    }
}