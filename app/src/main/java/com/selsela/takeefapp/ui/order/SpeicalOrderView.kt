package com.selsela.takeefapp.ui.order

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.common.EditText
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.ui.common.EditTextMutltLine
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.InputEditText
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.theme.ButtonBg
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.text10
import com.selsela.takeefapp.ui.theme.text16
import com.selsela.takeefapp.ui.theme.text16Line
import com.selsela.takeefapp.ui.theme.text18Book

@Preview
@Composable
fun SpecialOrderView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            var isAnimated by remember {
                mutableStateOf(false)
            }
            if (isAnimated.not()) {
                AppLogoImage(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(
                            width = 138.05.dp,
                            height = 39.34.dp
                        )
                )
            }
            Column(
                modifier = Modifier
                    .padding(bottom = 21.dp)
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .animateContentSize(tween(500))
                    .background(TextColor, RoundedCornerShape(33.dp))
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isAnimated.not()) {
                    SpecialOrderFormView()
                } else {
                    SuccessSend()
                }
            }

            if (isAnimated.not()) {
                ElasticButton(
                    onClick = {
                        isAnimated = !isAnimated

                    }, title = "إرسال الطلب",
                    modifier = Modifier
                        .padding(vertical = 21.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .requiredHeight(48.dp)
                        .align(Alignment.BottomCenter)
                )
            }

            if (isAnimated){
                LottieAnimationView(raw = R.raw.send,
                modifier = Modifier
                    .paddingTop(112)
                    .size(126.dp)
                    .align(Alignment.TopCenter))
            }
        }
    }
}

@Composable
private fun SpecialOrderFormView() {
    Column(
        Modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "طلب خاص",
            style = text18,
            color = Color.White,
            modifier = Modifier
                .paddingTop(41)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "سيتم التواصل معك من قبل الادارة لانجاز الطلب",
            modifier = Modifier
                .paddingTop(16)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = text11,
            color = SecondaryColor2
        )

        Text(
            text = stringResource(R.string.full_name),
            style = text11,
            modifier = Modifier.padding(top = 29.dp)
        )
        var name by remember {
            mutableStateOf("")
        }
        InputEditText(
            onValueChange = {
                name = it
            },
            text = name,
            hint = stringResource(R.string.name),
            inputType = KeyboardType.Text,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(R.string.order_title),
            style = text11,
            modifier = Modifier.padding(top = 16.dp)
        )
        var title by remember {
            mutableStateOf("")
        }
        InputEditText(
            onValueChange = {
                title = it
            },
            text = title,
            hint = stringResource(R.string.order_title),
            inputType = KeyboardType.Text,
            modifier = Modifier.padding(top = 16.dp)
        )
        var details by remember {
            mutableStateOf("")
        }
        Text(
            text = stringResource(R.string.order_details),
            style = text11,
            modifier = Modifier.padding(top = 16.dp)
        )
        EditTextMutltLine(
            onValueChange = {
                details = it
            },
            text = details,
            hint = stringResource(R.string.write_order_details),
            inputType = KeyboardType.Text,
            modifier = Modifier
                .padding(top = 16.dp)
        )

        Row(
            modifier = Modifier
                .paddingTop(28)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    Text(
                        text = "اضافة مرفقات",
                        style = text11,
                        color = Color.White.copy(0.85f)
                    )
                    Text(
                        text = "( اختياري)",
                        style = text10,
                        color = SecondaryColor.copy(0.70f)
                    )
                }
                Text(
                    text = "اسم الملف يوضع هنا",
                    style = text10,
                    color = SecondaryColor
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.attachment), contentDescription = ""
                )
            }
        }


    }
}

@Composable
fun SuccessSend() {
    Column(
        Modifier
            .fillMaxWidth()
            .requiredHeight(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(137.dp))

        Text(
            text = "تم الإرسال", style = text18,
            color = Color.White
        )
        Text(
            text = "سيتم التواصل معك ومتابعة الطلب من قبل الادارة",
            style = text16Line,
            color = Color.White.copy(0.85f),
            textAlign = TextAlign.Center,
            modifier = Modifier.paddingTop(21)
        )

        Spacer(modifier = Modifier.height(20.dp))
        ElasticButton(
            onClick = { /*TODO*/ }, title = "إغلاق",
            colorBg = ButtonBg,
            textColor = Purple40,
            modifier = Modifier
                .padding(top = 45.dp)
                .padding(horizontal = 37.dp)
                .fillMaxWidth()
                .requiredHeight(48.dp)
        )
    }
}