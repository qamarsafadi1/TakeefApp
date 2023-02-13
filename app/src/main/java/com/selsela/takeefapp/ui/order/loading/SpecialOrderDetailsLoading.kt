package com.selsela.takeefapp.ui.order.loading

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.Gray2
import com.selsela.takeefapp.ui.theme.ShimmerGray
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Preview
@Composable
fun OrderDetailsLoadingView() {
    val gradient = listOf(
        ShimmerGray.copy(alpha = 0.9f),
        ShimmerGray.copy(alpha = 0.4f),
        ShimmerGray.copy(alpha = 0.9f)
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

    Column(Modifier.fillMaxSize().background(Bg)) {
        Box(
            Modifier
                .fillMaxWidth()
                .requiredHeight(60.dp)
                .background(Color.White)
                .padding(top = 30.dp)
                .padding(horizontal = 6.dp),
        ) {

            IconButton(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = ""
                )
            }
            Text(
                text = stringResource(id = R.string.special_order_detail),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = text14Meduim
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 19.dp)
        ) {
            Box(
                Modifier
                    .padding(bottom = 8.4.dp, top = 14.dp)
                    .fillMaxSize()
                    .padding(bottom = 8.4.dp)
                    .background(brush, RoundedCornerShape(8.dp))

            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    columns = GridCells.Fixed(3),
                    userScrollEnabled = false,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 21.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Spacer(modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .requiredHeight(10.dp)
                                    .background(brush = brush, RoundedCornerShape(11.dp)))
                                Spacer(modifier = Modifier
                                    .paddingTop(9)
                                    .fillMaxWidth(0.3f)
                                    .requiredHeight(10.dp)
                                    .background(brush = brush, RoundedCornerShape(11.dp)))
                            }

                            Spacer(modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .requiredHeight(10.dp)
                                .background(brush = brush, RoundedCornerShape(11.dp)))
                        }
                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Row(
                            Modifier
                                .paddingTop(9)
                                .fillMaxWidth()
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .requiredHeight(10.dp)
                                    .background(brush, RoundedCornerShape(11.dp))
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .requiredHeight(10.dp)
                                    .background(brush, RoundedCornerShape(11.dp))
                            )
                        }
                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .requiredHeight(10.dp)
                                .background(brush, RoundedCornerShape(11.dp))
                        )


                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {

                        Row(
                            Modifier
                                .paddingTop(10)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .requiredHeight(200.dp)
                                    .background(brush, RoundedCornerShape(11.dp))
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .requiredHeight(10.dp)
                                .background(brush, RoundedCornerShape(11.dp))
                        )
                    }

                    items(5) { photo ->
                        AttachmentLoadingItem()
                    }
                }
            }

        }
    }

}

@Composable
fun AttachmentLoadingItem() {
    val gradient = listOf(
        ShimmerGray.copy(alpha = 0.9f),
        ShimmerGray.copy(alpha = 0.4f),
        ShimmerGray.copy(alpha = 0.9f)
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
    Spacer(
        modifier = Modifier
            .width(92.dp)
            .requiredHeight(67.dp)
            .background(brush)
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = Gray2, RoundedCornerShape(4.dp)),
    )
}