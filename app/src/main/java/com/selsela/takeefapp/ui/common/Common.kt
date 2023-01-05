package com.selsela.takeefapp.ui.common

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.VerifiedBg
import com.selsela.takeefapp.ui.theme.buttonText
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text14White
import com.selsela.takeefapp.ui.theme.text14WhiteCenter
import com.selsela.takeefapp.utils.Extensions.Companion.log
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Locale

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
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple40)
        ) {
            Text(text = title, style = buttonText)
        }
    }
}

@Composable
fun NextPageButton() {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Purple40),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.forward_arrow),
            contentDescription = "arrow"
        )
    }
}


@Composable
fun LottieAnimationView(modifier: Modifier = Modifier, raw: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw))
    LottieAnimation(
        composition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun EditText(
    onValueChange: (String) -> Unit, text: String,
    hint: String = "",
    inputType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)
) {
    TextField(
        value = text, onValueChange = {
            onValueChange(it)
        },
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .requiredHeight(46.dp)
                .border(1.dp, color = BorderColor, RoundedCornerShape(8.dp))
        ),
        textStyle = text14White,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            Color.White,
            backgroundColor = TextFieldBg,
            cursorColor = Color.White,
            disabledLabelColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        singleLine = singleLine,
        placeholder = {
            Text(
                text = hint, style = text14,
                color = SecondaryColor.copy(0.39f)
            )
        },
        trailingIcon = {
            trailing()
        },
        keyboardOptions = KeyboardOptions(keyboardType = inputType)
    )
}


@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it, it.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(otpCount) { index ->
                    OtpView(
                        index = index,
                        text = otpText,
                        modifier = modifier
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
    )
}

@Composable
private fun OtpView(
    index: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Box(
        modifier = Modifier
            .width(54.dp)
            .height(48.dp)
            .background(color = VerifiedBg, shape = RoundedCornerShape(11.dp))
            .border(
                1.dp, when {
                    isFocused -> BorderColor
                    else -> BorderColor
                },
                RoundedCornerShape(11.dp)
            )
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(

            text = char,
            style = text14WhiteCenter,
            textAlign = TextAlign.Center,
            color = if (isFocused) {
                Color.White
            } else {
                Color.White

            },
        )
    }

}

@Composable
fun Countdown(seconds: Long, modifier: Modifier) {
    val millisInFuture: Long = seconds * 1000

    var timeData by remember {
        mutableStateOf(millisInFuture)
    }

    val countDownTimer =
        object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("TAG", "onTick: ")
                timeData = millisUntilFinished
            }

            override fun onFinish() {

            }
        }

    DisposableEffect(key1 = "key") {
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }
    val secMilSec: Long = 1000
    val minMilSec = 60 * secMilSec
    val hourMilSec = 60 * minMilSec
    val dayMilSec = 24 * hourMilSec
    val minutes = (timeData % dayMilSec % hourMilSec / minMilSec).toInt()
    val seconds = (timeData % dayMilSec % hourMilSec % minMilSec / secMilSec).toInt()

    Text(
        text = String.format(
            Locale.ENGLISH,
            "%02d:%02d", minutes, seconds
        ),
        style = text14Meduim,
        color = Color.White,
        modifier = modifier
    )
}
