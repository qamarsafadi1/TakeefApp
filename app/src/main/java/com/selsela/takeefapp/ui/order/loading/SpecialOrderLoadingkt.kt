package com.selsela.takeefapp.ui.order.loading

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.order.item.DateView
import com.selsela.takeefapp.ui.theme.Gray
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.Shimmer
import com.selsela.takeefapp.ui.theme.ShimmerGray
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun SpecialOrderLoadingView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 18.dp,
            )
    ) {
        items(4) {
            SpecialOrderLoadingItem()
        }
    }
}

@Preview
@Composable
fun SpecialOrderLoadingItem(color: Color = ShimmerGray) {
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
    Box(
        Modifier
            .padding(bottom = 8.4.dp)
            .fillMaxSize()
            .requiredHeight(187.dp)
            .padding(bottom = 8.4.dp)
            .background(brush = brush, RoundedCornerShape(8.dp))

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 21.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .requiredHeight(10.dp)
                            .background(brush, RoundedCornerShape(11.dp))
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .requiredHeight(10.dp)
                            .background(brush, RoundedCornerShape(11.dp))
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .requiredHeight(10.dp)
                        .background(brush, RoundedCornerShape(11.dp))
                )

            }
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .requiredHeight(10.dp)
                    .background(brush, RoundedCornerShape(11.dp))
            )
            Row(
                Modifier
                    .paddingTop(10)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .requiredHeight(50.dp)
                        .background(brush, RoundedCornerShape(11.dp))
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.itemarrow),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Gray)
                )
            }


        }
    }
}