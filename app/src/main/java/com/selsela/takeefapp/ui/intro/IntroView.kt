package com.selsela.takeefapp.ui.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.selsela.takeefapp.ui.theme.TakeefAppTheme
import com.selsela.takeefapp.ui.theme.TextColor


@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroView(name: String, modifier: Modifier = Modifier) {
    Column(Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()

        // Display 10 items
        HorizontalPager(
            count = 3,
            state = pagerState,
            // Add 32.dp horizontal padding to 'center' the pages
            contentPadding = PaddingValues(horizontal = 0.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                TextColor.changeStatusBarColor()
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.90f)
                        .background(
                            color = TextColor,
                            shape = RoundedCornerShape(
                                topStart = 0.dp, topEnd = 0.dp,
                                bottomStart = 188.dp, bottomEnd = 188.dp
                            )
                        )
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TakeefAppTheme {
        IntroView("Android")
    }
}

// TODO: Enhance performance and stop change navigation bar color
@Composable
fun Color.changeStatusBarColor(){
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = TextColor,
            darkIcons = useDarkIcons
        )
    }
}