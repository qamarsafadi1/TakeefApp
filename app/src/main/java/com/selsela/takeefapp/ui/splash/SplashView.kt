package com.selsela.takeefapp.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TakeefAppTheme
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.sloganStyle
import com.selsela.takeefapp.ui.theme.textMeduim
import com.selsela.takeefapp.utils.Extentions.Companion.withDelay

@Composable
fun SplashView(
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.splashlogo), contentDescription = "Logo")
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
        {
            onFinish()
        }.withDelay(3000)
    }
}

@Composable
fun ConditionAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splashanimation))
    LottieAnimation(
        composition,
        modifier = Modifier.requiredHeight(184.dp)
            .padding(top = 42.dp),
        iterations = LottieConstants.IterateForever
    )
}