package com.selsela.takeefapp.ui.address.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
@OptIn(ExperimentalAnimationApi::class)
 fun CurrentAddressView(
    viewModel: HomeViewModel,
    modifier: Modifier, addressVisible: Boolean,
    onFav: (Boolean) -> Unit
) {
    AnimatedVisibility(
        visible = addressVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .animateEnterExit(
                    // Slide in/out the inner box.
                    enter = slideInVertically(
                        initialOffsetY = {
                            it / 2
                        },
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = {
                            it / 2
                        },
                    ),
                ),
            elevation = 9.dp,
            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 25.dp)
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .requiredHeight(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "",
                        modifier = Modifier.padding(end = 6.6.dp)
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    )
                    {
                        Text(
                            text = stringResource(R.string.current_location),
                            style = text11,
                            color = SecondaryColor
                        )
                        Text(
                            text = viewModel.selectedAddress.value,
                            style = text12,
                            color = TextColor,
                            modifier = Modifier.paddingTop(3)
                        )
                    }
                    var isFav by remember {
                        mutableStateOf(viewModel.address?.isFav == 1)
                    }
                    IconButton(onClick = { isFav = !isFav
                        onFav(isFav)}) {
                        Image(
                            painter = painterResource(
                                id =
                                if (isFav.not()) R.drawable.unfav else R.drawable.fav
                            ),
                            contentDescription = ""
                        )
                    }

                }
            }
        }
    }
}