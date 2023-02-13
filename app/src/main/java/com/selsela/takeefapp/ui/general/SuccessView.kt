package com.selsela.takeefapp.ui.general

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text16
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.ui.theme.text21
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Preview
@Composable
fun SuccessView(
    goOrders: () -> Unit,
    goHome: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        BackHandler() {
            goHome()
        }

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                Image(painter = painterResource(id = R.drawable.manart), contentDescription = "")
                LottieAnimationView(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(Alignment.BottomCenter)
                        .size(281.dp),
                    raw = R.raw.man
                )
            }

            Text(
                text = stringResource(R.string.operation_success),
                style = text21,
                color = TextColor
            )
            Text(
                text = stringResource(R.string.success_lbl),
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(22)
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )

            ElasticButton(
                onClick = {goOrders()},
                title = stringResource(R.string.order_list),
                modifier = Modifier
                    .paddingTop(31)
                    .padding(horizontal = 47.dp)
                    .fillMaxWidth()
                    .requiredHeight(48.dp)
            )

            ElasticView(onClick = { goHome()}) {
                Text(
                    text = stringResource(R.string.home),
                    style = text16,
                    color = Purple40,
                    modifier = Modifier
                        .paddingTop(31)

                )
            }

        }
    }
}