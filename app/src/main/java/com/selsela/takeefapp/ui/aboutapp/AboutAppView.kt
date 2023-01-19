package com.selsela.takeefapp.ui.aboutapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.splash.ConfigViewModel
import com.selsela.takeefapp.ui.splash.GeneralUiState
import com.selsela.takeefapp.ui.theme.CardBg
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.ui.theme.text22Book
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import de.palm.composestateevents.EventEffect

@Preview
@Composable
fun AboutAppView(
    viewModel: ConfigViewModel = hiltViewModel()
) {

    val viewState: GeneralUiState by viewModel.uiState.collectAsStateLifecycleAware(GeneralUiState())
    val context = LocalContext.current

    AboutAppContent(viewState)
    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (viewModel.isLoaded.not()) {
            viewModel.getAboutApp()
        }
    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = viewModel::onFailure
    ) { error ->
        Common.handleErrors(
            error.responseMessage,
            error.errors,
            context
        )
    }
}

@Composable
private fun AboutAppContent(viewState: GeneralUiState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogoImage()

            Box(
                modifier =
                Modifier
                    .paddingTop(14)
                    .width(194.dp)
                    .defaultMinSize(minHeight = 192.dp)
            ) {
                LottieAnimationView(
                    modifier = Modifier.requiredHeight(134.dp),
                    raw = R.raw.splashanimation
                )

                Image(
                    painter = painterResource(id = R.drawable.man),
                    contentDescription = "",
                    modifier = Modifier
                        .paddingTop(34.4)
                        .align(Alignment.CenterEnd)
                )

                Column(
                    Modifier
                        .paddingTop(107)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.quality_1),
                        style = text22Book,
                        color = Purple40
                    )
                    Text(
                        text = stringResource(R.string.fast),
                        style = text22Book,
                        color = TextColor,
                        modifier = Modifier
                            .paddingTop(6)
                            .padding(start = 43.dp)
                    )
                    Text(
                        text = stringResource(R.string.safety),
                        style = text22Book,
                        color = LightBlue,
                        modifier = Modifier
                            .paddingTop(6)
                            .padding(start = 65.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .paddingTop(12)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                backgroundColor = CardBg,
                shape = RoundedCornerShape(11.dp),
                elevation = 0.dp
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 29.dp)
                        .padding(18.dp)
                ) {

                    Text(
                        text = stringResource(R.string.about_app),
                        style = text16Medium,
                        color = TextColor
                    )

                    Text(
                        text = viewState.aboutApp?.text ?: "",
                        style = text12,
                        color = TextColor
                    )
                }
            }
        }
    }
}