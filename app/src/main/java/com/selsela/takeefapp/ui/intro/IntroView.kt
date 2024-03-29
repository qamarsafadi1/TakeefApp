package com.selsela.takeefapp.ui.intro

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.LocalMutableContext
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.NextPageButton
import com.selsela.takeefapp.ui.intro.componenets.Indicators
import com.selsela.takeefapp.ui.intro.componenets.IntroItem
import com.selsela.takeefapp.ui.intro.componenets.SkipButton
import com.selsela.takeefapp.ui.intro.componenets.StartNowButton
import com.selsela.takeefapp.ui.intro.intro.model.Intro
import com.selsela.takeefapp.ui.splash.ChangeNavigationBarColor
import com.selsela.takeefapp.ui.splash.ConfigViewModel
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TakeefAppTheme
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.buttonText
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.textBodyStyle
import com.selsela.takeefapp.ui.theme.textTitleStyle
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.LocalUtils.setLocale
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Locale

val doneSelection = mutableListOf<Int>()

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroView(
    modifier: Modifier = Modifier,
    goToHome: () -> Unit
) {
    var isSkipVisible by remember {
        mutableStateOf(true)
    }
    val introList = Intro().listOfIntro(LocalContext.current)
    var backgroundColor by remember {
        mutableStateOf(TextColor)
    }
    var selectedPage by remember {
        mutableStateOf(0)
    }
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged().collect { page ->
                backgroundColor = when (page) {
                    0 -> TextColor
                    1 -> LightBlue
                    else -> Purple40
                }
                selectedPage = page
            }
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.99f)
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(
                                topStart = 0.dp, topEnd = 0.dp,
                                bottomStart = 188.dp, bottomEnd = 188.dp
                            )
                        )
                ) {
                    IntroItem(introList[page])
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 37.dp, end = 24.dp, start = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            isSkipVisible = selectedPage != 2
            SkipButton(isSkipVisible, goToHome)
            Indicators(selectedPage)
            ElasticView(onClick = {
                if (selectedPage == 2) {
                    LocalData.firstLaunch = false
                    goToHome()
                }
                if (selectedPage != 2) {
                    scope.launch {
                        pagerState.animateScrollToPage(selectedPage + 1)
                    }
                }
            }) {
                if (selectedPage != 2) {
                    NextPageButton()
                } else {
                    StartNowButton()
                }
            }
        }
        backgroundColor.ChangeStatusBarColorWhiteNav()
        Color.White.ChangeNavigationBarColor(true)
    }
}

@Composable
fun Color.ChangeStatusBarColorWhiteNav() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = this,
        darkIcons = false
    )
    systemUiController.setNavigationBarColor(
        color = Color.White,
        darkIcons = false
    )
}