package com.selsela.takeefapp.ui.common

import android.content.res.Configuration
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.selsela.takeefapp.LocalMutableContext
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.Case
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.model.order.OrderService
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.splash.ConfigViewModel
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.VerifiedBg
import com.selsela.takeefapp.ui.theme.buttonText
import com.selsela.takeefapp.ui.theme.text10
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Bold
import com.selsela.takeefapp.ui.theme.text13
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text14White
import com.selsela.takeefapp.ui.theme.text14WhiteCenter
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.ui.theme.text8
import com.selsela.takeefapp.utils.Constants.LEFT
import com.selsela.takeefapp.utils.Constants.RIGHT
import com.selsela.takeefapp.utils.Extensions.Companion.convertToDecimalPatter
import com.selsela.takeefapp.utils.Extensions.Companion.getActivity
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.LocalUtils.setLocale
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ElasticButton(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
        .width(167.dp)
        .requiredHeight(48.dp),
    colorBg: Color = Purple40,
    textColor: Color = Color.White,
    isLoading: Boolean = false
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
        colors = ButtonDefaults.buttonColors(backgroundColor = colorBg)
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                LottieAnimationView(
                    raw = R.raw.whiteloading,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = title, style = buttonText,
                    color = textColor
                )
            }
        }
    }
    // }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ElasticButton(
    onClick: () -> Unit,
    title: String,
    icon: Int,
    buttonBg: Color = Purple40,
    iconGravity: Int = if (LocalData.appLocal == "ar") LEFT else RIGHT,
    modifier: Modifier = Modifier
        .width(167.dp)
        .requiredHeight(48.dp),
    isLoading: Boolean = false
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
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonBg)
    ) {

        AnimatedVisibility(
            visible = isLoading,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            LottieAnimationView(
                raw = R.raw.whiteloading,
                modifier = Modifier.size(25.dp)
            )
        }
        AnimatedVisibility(
            visible = isLoading.not(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (iconGravity == 1) {
                    Image(
                        painter = painterResource(id = icon), contentDescription = "",
                    )
                }
                Text(
                    text = title, style = buttonText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = if (iconGravity == RIGHT) 5.dp else 0.dp, top = 1.dp),
                    textAlign = TextAlign.Center
                )
                if (iconGravity == 0) {
                    Image(
                        painter = painterResource(id = icon), contentDescription = "",
                    )
                }
            }
        }
    }
    // }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ElasticButton(
    onClick: () -> Unit,
    title: String,
    icon: Int,
    buttonBg: Color = Purple40,
    modifier: Modifier = Modifier
        .width(167.dp)
        .requiredHeight(48.dp),
    isLoading: Boolean = false
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
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonBg)
    ) {
        AnimatedVisibility(
            visible = isLoading,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            LottieAnimationView(
                raw = R.raw.whiteloading,
                modifier = Modifier.size(25.dp)
            )
        }
        AnimatedVisibility(
            visible = isLoading.not(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title, style = buttonText,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(18.dp))
                Image(
                    painter = painterResource(id = icon), contentDescription = "",
                )
            }
        }
    }
}
// }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun IconedButton(
    onClick: () -> Unit,
    icon: Int,
    modifier: Modifier = Modifier
        .width(167.dp)
        .requiredHeight(48.dp),
    isLoading: Boolean = false
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

        AnimatedVisibility(
            visible = isLoading,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            LottieAnimationView(
                raw = R.raw.whiteloading,
                modifier = Modifier.size(25.dp)
            )
        }
        AnimatedVisibility(
            visible = isLoading.not(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = icon), contentDescription = ""
                )
            }
        }

    }
    // }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NextPageButton(
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Purple40),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isLoading,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            LottieAnimationView(
                raw = R.raw.whiteloading,
                modifier = Modifier.size(25.dp)
            )
        }
        AnimatedVisibility(
            visible = isLoading.not(),
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.forward_arrow),
                contentDescription = "arrow"
            )
        }
    }
}


@Composable
fun LottieAnimationView(modifier: Modifier = Modifier, raw: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw))
    LottieAnimation(
        composition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever,
    )
}

@Composable
fun LottieAnimationSizeView(modifier: Modifier = Modifier, raw: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw))
    LottieAnimation(
        composition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever,
        contentScale = ContentScale.None
    )
}

@Composable
fun EditText(
    onValueChange: (String) -> Unit, text: String,
    hint: String = "",
    inputType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    borderColor: Color = BorderColor,
    textStyle: androidx.compose.ui.text.TextStyle = text13,
    trailing: @Composable (() -> Unit)? = null
) {

    val surfaceColor: Color by animateColorAsState(
        borderColor
    )
    TextField(
        value = text, onValueChange = {
            onValueChange(it)
        },
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .requiredHeight(48.dp)
                .border(1.dp, color = surfaceColor, RoundedCornerShape(8.dp))
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
                text = hint, style = textStyle,
                color = SecondaryColor.copy(0.39f)
            )
        },
        trailingIcon = {
            if (trailing != null)
                trailing()
        },
        keyboardOptions = KeyboardOptions(keyboardType = inputType)
    )
}

@Composable
fun InputEditText(
    text: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    hint: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inputType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cursorColor: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start,
    cornerRaduis: Dp = 8.dp,
    fillMax: Float = 1f,
    borderColor: Color = BorderColor,
    isValid: Boolean = true,
    validationMessage: String = "",
    textStyle: androidx.compose.ui.text.TextStyle = text14White
) {
    val color: Color by animateColorAsState(
        borderColor
    )
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(fillMax)
                    .requiredHeight(48.dp)
                    .background(TextFieldBg, shape = RoundedCornerShape(cornerRaduis))
                    .border(1.dp, color = color, RoundedCornerShape(cornerRaduis))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = hint,
                        color = SecondaryColor,
                        style = textStyle
                    )
                }
                innerTextField()

            }
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor)
    )


    Row(Modifier.fillMaxWidth()) {
        AnimatedVisibility(visible = isValid.not()) {
            Text(
                text = validationMessage,
                style = text12,
                color = Red,
                modifier = Modifier.paddingTop(10)
            )
        }
    }
}


@Composable
fun InputEditText(
    text: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    hint: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    inputType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cursorColor: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start,
    cornerRaduis: Dp = 8.dp,
    fillMax: Float = 1f,
    hintColor: Color = SecondaryColor.copy(0.39f),
    borderColor: Color = BorderColor,
    isValid: Boolean = true,
    validationMessage: String = "",
    textStyle: androidx.compose.ui.text.TextStyle = text14White
) {
    val color: Color by animateColorAsState(
        borderColor
    )
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(fillMax)
                    .requiredHeight(48.dp)
                    .background(TextFieldBg, shape = RoundedCornerShape(cornerRaduis))
                    .border(1.dp, color = color, RoundedCornerShape(cornerRaduis))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = hint,
                        color = hintColor,
                        style = textStyle
                    )
                }
                innerTextField()

            }
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor)
    )
    Row(Modifier.fillMaxWidth()) {
        AnimatedVisibility(visible = isValid.not()) {
            Text(
                text = validationMessage,
                style = text12,
                color = Red,
                modifier = Modifier.paddingTop(10)
            )
        }
    }

}


@Composable
fun EditTextMutltLine(
    onValueChange: (String) -> Unit, text: String,
    hint: String = "",
    inputType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    borderColor: Color = BorderColor,
    isValid: Boolean = true,
    validationMessage: String = "",
    trailing: @Composable (() -> Unit)? = null
) {
    val color: Color by animateColorAsState(
        borderColor
    )
    TextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .requiredHeight(115.dp)
                .border(1.dp, color = color, RoundedCornerShape(8.dp))
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
        singleLine = false,
        placeholder = {
            Text(
                text = hint, style = text14White,
                color = SecondaryColor
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = inputType),
    )
    Row(Modifier.fillMaxWidth()) {
        AnimatedVisibility(visible = isValid.not()) {
            Text(
                text = validationMessage,
                style = text12,
                color = Red,
                modifier = Modifier.paddingTop(10)
            )
        }
    }

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
                text = stringResource(R.string.search_on_address),
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
    viewModel: AuthViewModel,
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
                        modifier = modifier,
                        borderColor = viewModel.validateBorderColor()
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
    )
    AnimatedVisibility(visible = viewModel.isValid.value.not()) {
        Text(
            text = viewModel.errorMessage.value,
            style = text12,
            color = Red,
            modifier = Modifier.paddingTop(12)
        )
    }
}

@Composable
private fun OtpView(
    index: Int,
    text: String,
    borderColor: Color = BorderColor,
    modifier: Modifier = Modifier
) {
    val color: Color by animateColorAsState(
        borderColor
    )
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text.reversed()[index].toString()
    }
    Box(
        modifier = Modifier
            .width(54.dp)
            .height(48.dp)
            .background(color = VerifiedBg, shape = RoundedCornerShape(11.dp))
            .border(
                1.dp, when {
                    isFocused -> color
                    else -> color
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
    val millisInFuture: Long = seconds*1000

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
    val minMilSec = 60*secMilSec
    val hourMilSec = 60*minMilSec
    val dayMilSec = 24*hourMilSec
    val minutes = (timeData%dayMilSec%hourMilSec/minMilSec).toInt()
    val seconds = (timeData%dayMilSec%hourMilSec%minMilSec/secMilSec).toInt()

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
fun ListedBottomSheet(sheetState: ModalBottomSheetState) {
    Box() {
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
                        hint = stringResource(R.string.search),
                        leading = {
                            androidx.compose.material3.Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "",
                                tint = SecondaryColor
                            )
                        })

                    LazyColumn(
                        modifier = Modifier
                            .paddingTop(42)
                            .fillMaxWidth()
                    ) {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LanguageSheet(
    sheetState: ModalBottomSheetState,
    configViewModel: ConfigViewModel,
    onConfirm: () -> Unit
) {
    Box() {
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val mutableContext = LocalMutableContext.current

        var check by remember {
            if (LocalData.appLocal == "ar")
                mutableStateOf(0)
            else mutableStateOf(1)
        }
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .padding(
                            horizontal = 24.dp,
                            vertical = 46.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.app_language),
                        style = text18,
                        color = Color.White
                    )

                    LanguageItem(check) {
                        check = it
                    }

                    Spacer(modifier = Modifier.height(35.dp))
                    ElasticButton(
                        onClick = {
                            if (check == 0) {
                                context.getActivity()?.setLocale("ar")
                            } else {
                                context.getActivity()?.setLocale("en")
                            }
                            {
                                mutableContext.value =false
                            }.withDelay(9000)
                            onConfirm()
                        },
                        title = stringResource(R.string.confirm_lbl),
                    )
                }
            }) {

        }
    }

}


@Composable
private fun LanguageItem(selectedItem: Int, onCheck: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .paddingTop(15)
            .fillMaxWidth()
    ) {
        repeat(2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(48.dp)
                    .background(TextFieldBg, RoundedCornerShape(11.dp))
                    .border(
                        width = 1.dp,
                        color = BorderColor,
                        RoundedCornerShape(11.dp)
                    )
                    .padding(horizontal = 15.dp)
                    .clickable(
                    ) {
                        onCheck(it)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = if (it == 0) "العربية" else "English",
                    style = text14,
                    color = Color.White
                )

                Image(
                    painter =
                    if (it == selectedItem)
                        painterResource(id = R.drawable.checked)
                    else painterResource(id = R.drawable.uncheckedrb),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
private fun AreaListItem() {
    Row(
        modifier = Modifier
            .padding(bottom = 7.dp)
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


@Composable
private fun DateView() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "AM",
            style = text11,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "08:30",
            style = text11,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = ".",
            style = text11,
            color = SecondaryColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "29-11-2022",
            style = text11,
            color = SecondaryColor
        )
    }
}

@Composable
private fun DateTimeView() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "AM",
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "08:30",
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))

        Text(
            text = ".",
            style = text12,
            color = TextColor
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "29-11-2022",
            style = text12,
            color = TextColor
        )
    }
}

@Composable
fun StepperView(
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    currentStep: Int = 0,
    isDetails: Boolean = false,
    items: List<Case>?
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Divider(
            Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 10.dp)
                .align(Alignment.TopCenter),
            thickness = 4.dp,
            color = SecondaryColor2.copy(0.32f)
        )
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            itemsIndexed(
                items ?: listOf()
            ) { index, step ->
                Step(
                    step = step.name,
                    isCurrent = currentStep == index,
                    isCompleted = index < currentStep,
                    isDetails = isDetails
                )
            }

        }

    }
}

@Composable
private fun Step(
    step: String,
    isCurrent: Boolean,
    isCompleted: Boolean,
    isDetails: Boolean = false
) {
    val drawable: Int by animateIntAsState(
        targetValue = if (isCurrent) {
            R.drawable.checked
        } else {
            if (isCompleted)
                R.drawable.checked
            else R.drawable.uncomplete

        },
        animationSpec = tween(
            durationMillis = 1000,
        )
    )
    val textColor: Color by animateColorAsState(
        targetValue = if (isCurrent) {
            TextColor
        } else {
            SecondaryColor
        },
        animationSpec = tween(
            durationMillis = 1000,
        )
    )
//    if (isDetails.not())
//        Spacer(modifier = Modifier.width(10.dp))

    Column(
        Modifier
            .wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = ""
        )
        Text(
            text = step,
            modifier = Modifier
                .paddingTop(9)
                .width(if (LocalData.appLocal == "ar") 30.dp else 50.dp),
            style = text8,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SelectedServicesView(orderServices: List<OrderService>) {
    Row(
        modifier = Modifier
            .paddingTop(11)
            .padding(horizontal = 34.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(orderServices.size) {
            Row {
                Text(
                    text = "${orderServices[it].service.name} : ", style = text11,
                    color = SecondaryColor
                )
                Text(
                    text = orderServices[it].count.convertToDecimalPatter(),
                    style = text12,
                    color = TextColor
                )
            }
            Spacer(modifier = Modifier.width(41.1.dp))
        }

    }
}

@Composable
fun AsyncImage(
    imageUrl: Any,
    modifier: Modifier
    = Modifier
        .size(90.dp)
        .padding(horizontal = 8.dp),
    contentScale: ContentScale = ContentScale.Fit
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = imageUrl,
        error = {
            painterResource(R.drawable.placeholder)
        },
        loading = {
            LottieAnimationSizeView(
                raw = R.raw.imageloading,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        },
        contentDescription = "",
        contentScale = contentScale
    )
}
