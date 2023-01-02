package com.selsela.takeefapp.ui.intro

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.selsela.takeefapp.ui.theme.TakeefAppTheme


@Composable
fun IntroView(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TakeefAppTheme {
        IntroView("Android")
    }
}