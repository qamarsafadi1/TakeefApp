package com.selsela.takeefapp.ui.intro

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.NextPageButton
import com.selsela.takeefapp.ui.model.intro.Intro
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

val doneSelection = mutableListOf<Int>()

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun IntroView(
    modifier: Modifier = Modifier,
    goToHome: () -> Unit
) {
    var isSkipVisible by remember {
        mutableStateOf(true)
    }
    val introList = Intro().listOfIntro()
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
                page.log("paaaage")
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
                        .fillMaxHeight(0.95f)
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(
                                topStart = 0.dp, topEnd = 0.dp,
                                bottomStart = 188.dp, bottomEnd = 188.dp
                            )
                        )
                ) {
                    IntroItem(introList[page], page)
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
                "heeyClick".log()
                selectedPage.log("selectedPage")
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
    }
}


@Composable
private fun StartNowButton() {
    Row(
        modifier = Modifier
            .requiredWidth(181.dp)
            .requiredHeight(56.dp)
            .background(TextColor, shape = RoundedCornerShape(28.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "إبدء الان", style = buttonText,
            modifier = Modifier.padding(end = 23.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.forward_arrow),
            contentDescription = "arrow",
        )
    }
}

@Composable
private fun SkipButton(isSkipVisible: Boolean, goToHome: () -> Unit) {
    if (isSkipVisible) {
        ElasticView(
            onClick = {
                LocalData.firstLaunch = false
                goToHome()
            }) {
            Text(
                stringResource(R.string.Skip),
                style = text14
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HorizontalPagerListener(
    pagerState: PagerState,
    backgroundColor: Color,
    selectedPage: Int
): Pair<Color, Int> {
    var backgroundColor1 = backgroundColor
    var selectedPage1 = selectedPage
    return Pair(backgroundColor1, selectedPage1)
}

@Composable
fun IntroItem(intro: Intro, index: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(

            modifier = Modifier.fillMaxSize(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.en),
                    contentDescription = "en",
                    modifier = Modifier
                        .padding(top = 54.dp, start = 24.dp, end = 24.dp)
                        .align(Alignment.TopEnd)
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


@Composable
fun Indicators(selectedIndex: Int) {
    Row(
        Modifier
            .wrapContentSize(Alignment.BottomStart)
            .animateContentSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 3) {
            Image(
                painter =
                if (i == selectedIndex) {
                    doneSelection.add(i)
                    doneSelection.log("doneSelection")
                    painterResource(id = R.drawable.nowselected)
                } else {
                    if (doneSelection.contains(i).not())
                        painterResource(id = R.drawable.unselected)
                    else painterResource(id = R.drawable.selected)
                },
                contentDescription = "indicator",
                modifier = if (i == 1)
                    Modifier.padding(horizontal = 7.dp)
                else Modifier.padding(horizontal = 0.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TakeefAppTheme {
        IntroView() {}
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