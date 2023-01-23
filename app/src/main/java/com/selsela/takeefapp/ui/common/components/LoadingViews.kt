package com.selsela.takeefapp.ui.common.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.Shimmer
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Preview
@Composable
fun LoadingNotificationView(color: Color = Shimmer) {
    val gradient = listOf(
        color.copy(alpha = 0.9f),
        color.copy(alpha = 0.4f),
        color.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
    // Your content
    LazyColumn( modifier = Modifier
        .paddingTop(53)
        .fillMaxSize()
        .padding(horizontal = 10.dp)) {
        items(4) {
            Box(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .requiredHeight(122.dp)
            ) {
                Card(
                    elevation = 0.dp,
                    shape = RoundedCornerShape(11.dp),
                    backgroundColor = TextFieldBg,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .height(122.dp)
                        .fillMaxWidth()

                ) {
                    Column(
                        Modifier
                            .padding(top = 25.dp)
                            .padding(
                                horizontal = 35.dp
                            )
                            .fillMaxSize()
                    ) {

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .requiredHeight(10.dp)
                                .background(brush = brush, RoundedCornerShape(8.dp))
                        )
                        Spacer(
                            modifier = Modifier
                                .paddingTop(5)
                                .fillMaxWidth(0.15f)
                                .requiredHeight(10.dp)
                                .background(brush = brush, RoundedCornerShape(8.dp))
                        )
                        Spacer(
                            modifier = Modifier
                                .paddingTop(5)
                                .fillMaxWidth(0.7f)
                                .requiredHeight(10.dp)
                                .background(brush = brush, RoundedCornerShape(8.dp))
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.notificationitemicon),
                    contentDescription = "",
                    modifier = Modifier.paddingTop(17)
                )
            }
        }
    }
}
@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(50.dp)
                .background(Shimmer, RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimationView(raw = R.raw.whiteloading)
        }
    }
}
