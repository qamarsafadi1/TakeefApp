package com.selsela.takeefapp.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.auth.component.EditTextView
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.common.NextPageButton
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text11Meduim
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.ui.theme.text18Book
import com.selsela.takeefapp.ui.theme.text18Meduim
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants.NOT_VERIFIED
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import de.palm.composestateevents.EventEffect

@Composable
fun LoginView(
    viewModel: AuthViewModel = hiltViewModel(),
    goToTerms: () -> Unit,
    goToSupport: () -> Unit,
    goToHome: () -> Unit,
    goToVerify: () -> Unit
) {
    val viewState: AuthUiState by viewModel.uiState.collectAsStateLifecycleAware(AuthUiState())
    val context = LocalContext.current

    LoginContent(
        viewModel,
        viewState,
        onClick = viewModel::auth,
        goToTerms, goToSupport
    )

    /**
     * Handle Ui state from flow
     */

    EventEffect(
        event = viewState.onSuccess,
        onConsumed = viewModel::onSuccess
    ) { status ->
        viewModel.updateFcm()
        if (status == NOT_VERIFIED)
            goToVerify()
        else goToHome()
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
private fun LoginContent(
    viewModel: AuthViewModel,
    uiState: AuthUiState,
    onClick: () -> Unit,
    goToTerms: () -> Unit,
    goToSupport: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.77f)
        ) {
            Card(
                shape = RoundedCornerShape(33.dp),
                backgroundColor = TextColor,
                modifier = Modifier
                    .padding(top = 71.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 405.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.welcome_lbl),
                        style = text18Book,
                        color = Purple40
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 23.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            style = text18,
                            color = Color.White
                        )
                        Text(
                            text = "/",
                            style = text18Meduim,
                            modifier = Modifier.padding(start = 1.dp, end = 6.dp)
                        )
                        Text(
                            text = stringResource(R.string.register),
                            style = text14Meduim,
                            color = Color.White.copy(0.85f)
                        )
                    }

                    Text(
                        text = stringResource(R.string.mobile),
                        style = text11,
                        modifier = Modifier.padding(top = 35.dp)
                    )
                    EditTextView(viewModel = viewModel)
                    Spacer(modifier = Modifier.height(58.dp))
                    Row(
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.when_you_register),
                                style = text11
                            )
                            Row(modifier = Modifier.padding(top = 4.dp)) {
                                Text(text = stringResource(R.string.you_accept), style = text11)
                                ElasticView(onClick = { goToTerms() }) {
                                    Text(
                                        text = stringResource(R.string.terms_condition),
                                        style = text11Meduim,
                                        textDecoration = TextDecoration.Underline,
                                        modifier = Modifier.padding(start = 6.dp)
                                    )
                                }

                            }
                        }
                        ElasticView(onClick = { onClick() }) {
                            NextPageButton(uiState.isLoading)
                        }
                    }
                }
            }
            LottieAnimationView(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(187.dp)
                    .height(84.dp),
                raw = R.raw.look
            )
        }

        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .padding(bottom = 43.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.mjloho),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(19.5.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .requiredHeight(51.dp)
                    .background(LightBlue.copy(0.07f), shape = RoundedCornerShape(25.dp))
            ) {
                Text(text = stringResource(R.string.facing_problem), style = text12)
                ElasticView(onClick = { goToSupport() }) {
                    Text(
                        text = stringResource(R.string.support_lbl),
                        style = text12Meduim,
                        color = LightBlue,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
        }
    }
}