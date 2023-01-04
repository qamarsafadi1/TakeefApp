package com.selsela.takeefapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.buttonText

@Composable
fun AppLogoImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.splashlogo), contentDescription = "Logo",
        modifier = modifier
    )
}

@Composable
fun ElasticButton(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
        .width(167.dp)
        .requiredHeight(48.dp)
) {
    ElasticView(onClick = { onClick() }) {
        Button(
            onClick = {},
            modifier = modifier,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
        ) {
            Text(text = title, style = buttonText)
        }
    }
}