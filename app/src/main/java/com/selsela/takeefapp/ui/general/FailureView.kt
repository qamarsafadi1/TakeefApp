package com.selsela.takeefapp.ui.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text21
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Preview
@Composable
fun FailureView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
              LottieAnimationView(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(288.dp),
                    raw = R.raw.error
                )
            }

            Text(
                text = stringResource(R.string.operation_failed),
                style = text21,
                color = TextColor
            )
            Text(
                text = stringResource(R.string.failed_lbl),
                style = text14,
                color = TextColor,
                modifier = Modifier.paddingTop(22),
                textAlign = TextAlign.Center
            )

            ElasticButton(
                onClick = { /*TODO*/ },
                title = stringResource(R.string.accept),
                modifier = Modifier
                    .paddingTop(79)
                    .padding(horizontal = 47.dp)
                    .fillMaxWidth()
                    .requiredHeight(48.dp)
            )
        }
    }
}