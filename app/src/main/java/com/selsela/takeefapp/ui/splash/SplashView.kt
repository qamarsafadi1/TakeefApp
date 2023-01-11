package com.selsela.takeefapp.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.sloganStyle
import com.selsela.takeefapp.ui.theme.textMeduim
import kotlinx.coroutines.delay

@Composable
fun SplashView(
    onFinish: () -> Unit
) {
    Color.White.ChangeStatusBarColor()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogoImage()

        Text(
            text = stringResource(R.string.slogan),
            style = sloganStyle,
            modifier = Modifier.padding(top = 19.7.dp)
        )
        ConditionAnimation()
        Row(modifier = Modifier.padding(top = 150.dp)) {
            Text(
                text = stringResource(R.string.quality),
                style = textMeduim,
                color = Purple40,
                fontSize = 19.sp
            )
            Text(
                text = stringResource(R.string.speed),
                style = textMeduim,
                color = TextColor,
                fontSize = 19.sp,
                modifier = Modifier.padding(horizontal = 9.dp)
            )
            Text(
                text = stringResource(R.string.safty),
                style = textMeduim,
                color = LightBlue,
                fontSize = 19.sp
            )
        };
        LaunchedEffect(Unit) {
            delay(3000)
            onFinish()
        }
    }
}

@Composable
fun ConditionAnimation() {
    LottieAnimationView(
        modifier = Modifier
            .requiredHeight(184.dp)
            .padding(top = 42.dp),
        raw = R.raw.splashanimation
    )
}

@Composable
fun Color.ChangeStatusBarColor(
    isDark: Boolean = false
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = this,
        darkIcons = false
    )
    systemUiController.setNavigationBarColor(
        color = this,
        darkIcons = isDark
    )
}
@Composable
fun Color.ChangeStatusBarOnlyColor(
    isDark: Boolean = false
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = this,
        darkIcons = false
    )
}