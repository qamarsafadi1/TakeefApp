package com.selsela.takeefapp.ui.common

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.VerifiedBg
import com.selsela.takeefapp.ui.theme.buttonText
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text14White
import com.selsela.takeefapp.ui.theme.text14WhiteCenter
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
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
    // ElasticView(onClick = { onClick() }) {
    Button(
        onClick = {
            onClick()
        },
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
    // }
}

@Composable
fun ElasticButton(
    onClick: () -> Unit,
    title: String,
    icon: Int,
    modifier: Modifier = Modifier
        .width(167.dp)
        .requiredHeight(48.dp)
) {
    // ElasticView(onClick = { onClick() }) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Purple40)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = buttonText)
            Image(
                painter = painterResource(id = icon), contentDescription = "",
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
    // }
}
@Composable
fun IconedButton(
    onClick: () -> Unit,
    icon: Int,
    modifier: Modifier = Modifier
        .width(167.dp)
        .requiredHeight(48.dp)
) {
    // ElasticView(onClick = { onClick() }) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Purple40)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon), contentDescription = "")
        }
    }
    // }
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
fun EditTextAddress(
    onValueChange: (String) -> Unit, text: String,
    hint: String = "",
    inputType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null,
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
        textStyle = text11,
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
                text = hint, style = text11,
                color = SecondaryColor
            )
        },
        trailingIcon = {
            if (trailing != null) {
                trailing()
            }
        },

        keyboardOptions = KeyboardOptions(keyboardType = inputType)
    )
}
@Composable
fun EditTextLeadingIcon(
    onValueChange: (String) -> Unit, text: String,
    hint: String = "",
    inputType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    leading: @Composable (() -> Unit)? = null,
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
        textStyle = text11,
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
                text = hint, style = text11,
                color = SecondaryColor
            )
        },
        leadingIcon = {
            if (leading != null) {
                leading()
            }
        },

        keyboardOptions = KeyboardOptions(keyboardType = inputType)
    )
}

@Composable
fun SearchBar(
    text: String,
    onSearch: (String) -> Unit,
    onValueChange: (String) -> Unit
) {
    TextField(
        value =
        text, onValueChange =
        {
            onValueChange(it)
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            TextColor,
            backgroundColor = Color.Transparent,
            cursorColor = SecondaryColor,
            disabledLabelColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = text12,
        placeholder = {
            Text(
                text = "بحث عن عنوان او تحديد عنوان سابق",
                style = text12,
                color = SecondaryColor
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "",
                tint = SecondaryColor
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            if (text.isEmpty())
            onSearch("")
            else onSearch(text)
        })

    )
}

@Composable
fun SearchAddressBar(
    text: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value =
        text, onValueChange =
        {
            onValueChange(it)
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            TextColor,
            backgroundColor = Color.Transparent,
            cursorColor = SecondaryColor,
            disabledLabelColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = text12,
        placeholder = {
            Text(
                text = "بحث عن عنوان ",
                style = text12,
                color = SecondaryColor
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "",
                tint = SecondaryColor
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
          //  onSearch(text)
        })

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

@Composable
fun Spinner(
    placeHolder: String,
    items: List<String> = listOf(
        "اسم المنطقة",
        "اسم المنطقة",
        "اسم المنطقة",
        "اسم المنطقة",
        "اسم المنطقة",
        "اسم المنطقة"
    ),
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .requiredHeight(46.dp)
            .background(TextFieldBg, RoundedCornerShape(8.dp))
            .border(1.dp, color = BorderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = { expanded = true })
            .padding(horizontal = 16.dp)

    ) {
        Text(
            if (selectedIndex != -1)
                items[selectedIndex]
            else placeHolder,
            style = text11,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)

        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentSize(Alignment.BottomStart)
                .border(1.dp, color = Color.Transparent, RoundedCornerShape(8.dp))
                .background(
                    Color.White
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    Text(
                        text = s,
                        style = text11,
                        color = TextColor
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.spinnerarrow),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterEnd)

        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListedBottomSheet(sheetState: ModalBottomSheetState){
    Box(){
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.88f)
                        .padding(
                            horizontal = 24.dp,
                            vertical = 46.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.area_name),
                        style = text18,
                        color = Color.White
                    )

                    var query by remember {
                        mutableStateOf("")
                    }
                    EditTextLeadingIcon(
                        modifier = Modifier.paddingTop(24),
                        onValueChange = {
                            query = it
                        }, text = query,
                        hint = "بحث",
                        leading = {
                            androidx.compose.material3.Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "",
                                tint = SecondaryColor
                            )
                        })

                    LazyColumn(modifier = Modifier.paddingTop(42).fillMaxWidth()) {
                        items(10) {
                            AreaListItem()
                        }
                    }
                }
            }
        ) {
        }
    }
}


@Composable
private fun AreaListItem() {
    Row(
        modifier = Modifier.padding(bottom = 7.dp)
            .requiredHeight(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(13.6.dp))
        Text(
            text = stringResource(id = R.string.area_name),
            style = text14,
            color = Color.White
        )
    }
}
