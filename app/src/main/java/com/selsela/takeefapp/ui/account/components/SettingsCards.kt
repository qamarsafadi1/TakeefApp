package com.selsela.takeefapp.ui.account.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.theme.ColorAccent
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.text10NoLines
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.Constants.FINISHED
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun SettingsCards(
    uiState: AuthUiState,
    goToProfile: () -> Unit,
    goToOrder: (Int) -> Unit,
    goToNotification: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 17.dp)
            .paddingTop(12)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(88.dp)
                .clickable {
                    goToOrder(FINISHED)
                },
            shape = RoundedCornerShape(13.dp),
            elevation = 20.dp,
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.archive),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(R.string.archive),
                    style = text12,
                    color = SecondaryColor,
                    modifier = Modifier.paddingTop(10)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(88.dp)
                .clickable {
                    goToProfile()
                },
            shape = RoundedCornerShape(13.dp),
            elevation = 20.dp,
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(R.string.my_account),
                    style = text12,
                    color = SecondaryColor,
                    modifier = Modifier.paddingTop(10)

                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(88.dp)
                .clickable {
                    goToNotification()
                },
            shape = RoundedCornerShape(13.dp),
            elevation = 20.dp,
            backgroundColor = Color.White
        ) {
            Box {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.notifiication),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(R.string.notifications),
                        style = text12,
                        color = SecondaryColor,
                        modifier = Modifier.paddingTop(10)

                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, end = 10.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(ColorAccent)
                        .size(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${uiState.user?.newNotifications}",
                        style = text10NoLines,
                        color = Color.White,
                        modifier = Modifier
                            .paddingTop(1.5)
                            .align(Alignment.Center)
                    )
                }
            }

        }
    }
}

