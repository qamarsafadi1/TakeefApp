package com.selsela.takeefapp.ui.notification.cell

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text11NoLines
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

val swipeableItems = mutableListOf<Int>()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationItem(
    index: Int, isSelected: Boolean = false,
    onSwipe: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var selected = isSelected
    val width = 96.dp
    val squareSize = 75.dp
    var swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states
    val alignment = Alignment.CenterEnd

    Box(
        modifier = Modifier
            .padding(bottom = 9.dp)
            .fillMaxWidth()
            .height(122.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal,
            )
            .background(Color.Transparent)
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .background(TextColor)
                .padding(horizontal = 20.dp),
            contentAlignment = alignment
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    swipeableState.animateTo(0)
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.deletebtn),
                    contentDescription = ""
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(122.dp)
            .offset {
                IntOffset(-(swipeableState.offset.value.roundToInt()), 0)
            }) {
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

                    Text(
                        text = "انهاء الطلب #1234",
                        style = text11NoLines,
                        color = Color.White
                    )

                    Text(
                        text = "منذ دقيقة",
                        style = text11NoLines,
                        color = SecondaryColor,
                        modifier = Modifier.paddingTop(5)
                    )

                    Text(
                        text = "هذا النص هو مثال لنص يمكن أن يستبدل في نفس المساحة، لقد تم توليد \n",
                        style = text11NoLines,
                        color = Color.White.copy(0.85f),
                        modifier = Modifier.paddingTop(8)
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

