package com.selsela.takeefapp.ui.intro.componenets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.intro.intro.model.Intro
import com.selsela.takeefapp.ui.splash.ConfigViewModel
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.ui.theme.textBodyStyle
import com.selsela.takeefapp.ui.theme.textTitleStyle
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.LocalUtils.setLocale


@Composable
fun IntroItem(intro: Intro, viewModel: ConfigViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 60.dp)
            ) {
                Text(
                    text = if (LocalData.appLocal == "ar") "EN" else "ع",
                    style = text18,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 54.dp, start = 24.dp, end = 24.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            if (LocalData.appLocal == "ar")
                                context.setLocale("en")
                            else context.setLocale("ar")
                            viewModel.getConfig()
                        }
                )

                if (intro.Image == R.drawable.intro2) {
                    Image(
                        painter = painterResource(id = intro.Image),
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    if (intro.Image == R.drawable.intro1) {
                        Image(
                            painter = painterResource(id = intro.Image),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 97.dp, start = 24.dp, end = 24.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    } else {
                        Image(
                            painter = painterResource(id = intro.Image),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 97.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }

            Text(text = intro.title, style = textTitleStyle)
            Text(
                text = intro.text, style = textBodyStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp, end = 43.dp, start = 43.dp)
            )

        }
    }


}

