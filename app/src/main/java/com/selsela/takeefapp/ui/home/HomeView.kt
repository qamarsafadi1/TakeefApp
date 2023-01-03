package com.selsela.takeefapp.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text18
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntSize
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.NoRippleTheme
import com.selsela.takeefapp.ui.theme.TextColorHint
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.utils.ModifiersExtension.cornerRadius
import com.selsela.takeefapp.utils.ModifiersExtension.noRippleClickable
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import com.selsela.takeefapp.utils.ModifiersExtension.widthMatchParent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun HomeView() {
    Color.White.ChangeStatusBarColor()
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 45.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(painter = painterResource(id = R.drawable.menu), contentDescription = "")
                    Image(
                        painter = painterResource(id = R.drawable.notificationnew),
                        contentDescription = ""
                    )
                }

                AppLogoImage(
                    modifier = Modifier.size(
                        width = 128.05.dp,
                        height = 36.34.dp
                    )
                )
                Text(
                    text = stringResource(R.string.glad_to_serve),
                    style = text18,
                    modifier = Modifier.paddingTop(27.3)
                )
                Text(
                    text = stringResource(R.string.serve_lbl),
                    style = text14,
                    modifier = Modifier.paddingTop(13),
                    color = TextColor.copy(0.40f)
                )

//            AnimatedContent(
//                targetState = expanded,
//                transitionSpec = {
//                    fadeIn(animationSpec = tween(150, 150)) with
//                            fadeOut(animationSpec = tween(150)) using
//                            SizeTransform { initialSize, targetSize ->
//                                if (targetState) {
//                                    keyframes {
//                                        // Expand horizontally first.
//                                        IntSize(targetSize.width, initialSize.height) at 150
//                                        durationMillis = 300
//                                    }
//                                } else {
//                                    keyframes {
//                                        // Shrink vertically first.
//                                        IntSize(initialSize.width, targetSize.height) at 150
//                                        durationMillis = 300
//                                    }
//                                }
//                            }
//                }
//            ) { targetExpanded ->
//                if (targetExpanded) {
//                    Expanded(){
//                        expanded = !expanded
//                    }
//                } else {
//                    ServiceItem(){
//                        expanded = !expanded
//                    }
//                   // Expanded()
//                }
//            }


                LazyColumn() {
                    items(3) {
                        var expanded by remember { mutableStateOf(false) }
                        Surface(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                            color = Color.Transparent,
                        ) {
                            AnimatedContent(
                                targetState = expanded,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(150, 150)) with
                                            fadeOut(animationSpec = tween(150)) using
                                            SizeTransform { initialSize, targetSize ->
                                                if (targetState) {
                                                    keyframes {
                                                        // Expand horizontally first.
                                                        IntSize(
                                                            targetSize.width,
                                                            initialSize.height
                                                        ) at 150
                                                        durationMillis = 300
                                                    }
                                                } else {
                                                    keyframes {
                                                        // Shrink vertically first.
                                                        IntSize(
                                                            initialSize.width,
                                                            targetSize.height
                                                        ) at 150
                                                        durationMillis = 300
                                                    }
                                                }
                                            }
                                }
                            ) { targetExpanded ->
                                if (targetExpanded) {
                                    Expanded() {}
                                } else {
                                    ServiceItem() {}
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun ServiceItem(onClick: () -> Unit) {
    Row(
        modifier =
        Modifier
            .padding(bottom = 9.dp)
            .then(
                Modifier
                    .widthMatchParent(140)
                    .then(
                        Modifier.cornerRadius(Purple40.copy(0.10f), 20)
                    )
            )
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.service1),
            contentDescription = ""
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 19.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "طلب خدمة",
                style = text12,
                color = TextColor.copy(0.44f)
            )
            Text(
                text = "التنظيف",
                style = text20,
                color = TextColor
            )
            Text(
                text = "صيانة جميع انواع المكيفات",
                style = text12,
                color = TextColor.copy(0.44f)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 18.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.unchecked),
                contentDescription = ""
            )
            Image(
                painter = painterResource(id = R.drawable.backward_arrow),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun Expanded(onClick: () -> Unit) {
    Column(
        modifier =
        Modifier
            .padding(bottom = 9.dp)
            .background(color = Purple40.copy(0.10f), RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .padding(horizontal = 18.dp)

    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(140.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.service1),
                contentDescription = ""
            )

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(start = 19.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = "طلب خدمة",
                    style = text12,
                    color = TextColor.copy(0.44f)
                )
                Text(
                    text = "التنظيف",
                    style = text20,
                    color = TextColor
                )
                Text(
                    text = "صيانة جميع انواع المكيفات",
                    style = text12,
                    color = TextColor.copy(0.44f)
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 18.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.unchecked),
                    contentDescription = ""
                )
                Image(
                    painter = painterResource(id = R.drawable.backward_arrow),
                    contentDescription = ""
                )
            }
        }

        Divider(
            thickness = 2.dp,
            color = LightBlue.copy(.14f),
            modifier = Modifier.padding(
                top = 38.dp,
                bottom = 28.dp
            )
        )

        Text(
            text = stringResource(R.string.condation_type),
            style = text12,
            color = TextColorHint
        )


    }

}