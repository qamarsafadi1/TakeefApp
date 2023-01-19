package com.selsela.takeefapp.ui.account

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.account.components.Header
import com.selsela.takeefapp.ui.account.components.OrderCards
import com.selsela.takeefapp.ui.account.components.SettingsCards
import com.selsela.takeefapp.ui.account.components.WalletCard
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.common.LanguageSheet
import com.selsela.takeefapp.ui.splash.ChangeNavigationBarColor
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.showAlertDialog
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import de.palm.composestateevents.EventEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyAccountView(
    viewModel: AuthViewModel = hiltViewModel(),
    onBack: () -> Unit,
    goToLogin: () -> Unit,
    goToSpecialOrders: () -> Unit,
    goToAboutApp: () -> Unit,
    goToNotification: () -> Unit,
    goToTerms: () -> Unit,
    goToSupport: () -> Unit,
    goToProfile: () -> Unit,
    goToWallet: () -> Unit,
    goToOrder: () -> Unit
) {
    Color.Transparent.ChangeStatusBarOnlyColor()
    Color.White.ChangeNavigationBarColor()
    val coroutineScope = rememberCoroutineScope()
    val languageSheet = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val viewState: AuthUiState by viewModel.uiState.collectAsStateLifecycleAware(AuthUiState())
    val context = LocalContext.current

    AccountViewContent(
        uiState = viewState,
        viewModel,
        onBack,
        context,
        goToLogin,
        goToWallet,
        goToSpecialOrders,
        goToOrder,
        goToProfile,
        goToNotification,
        goToSupport,
        goToTerms,
        goToAboutApp,
        coroutineScope,
        languageSheet
    )

    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.me()
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
@OptIn(ExperimentalMaterialApi::class)
private fun AccountViewContent(
    uiState: AuthUiState,
    viewModel: AuthViewModel,
    onBack: () -> Unit,
    context: Context,
    goToLogin: () -> Unit,
    goToWallet: () -> Unit,
    goToSpecialOrders: () -> Unit,
    goToOrder: () -> Unit,
    goToProfile: () -> Unit,
    goToNotification: () -> Unit,
    goToSupport: () -> Unit,
    goToTerms: () -> Unit,
    goToAboutApp: () -> Unit,
    coroutineScope: CoroutineScope,
    languageSheet: ModalBottomSheetState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(207.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.accountheader),
                contentDescription = "header",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .padding(horizontal = 16.dp)
        ) {
            // SCREEN HEADER
            Header(
                viewModel.userLoggedIn.value,
                onBack = { onBack() }
            ) {
                if (it == Constants.LOG_OUT) {
                    context.showAlertDialog(
                        context.getString(R.string.logout),
                        context.getString(R.string.logout_lbl),
                        context.getString(R.string.yes),
                        context.getString(R.string.no),
                    ) {
                        LocalData.clearData()
                        viewModel.userLoggedIn.value = false
                    }
                } else {
                    goToLogin()
                }
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                if (viewModel.userLoggedIn.value) {
                    WalletCard(uiState) {
                        goToWallet()
                    }
                    OrderCards(
                        uiState,
                        goToSpecialOrder = {
                            goToSpecialOrders()
                        }) {
                        goToOrder()
                    }
                    SettingsCards(
                        uiState,
                        goToProfile = {
                            goToProfile()
                        }
                    ) {
                        goToNotification()
                    }

                }

                Text(
                    text = stringResource(R.string.settings),
                    style = text12,
                    color = SecondaryColor,
                    modifier = Modifier.paddingTop(71)
                )
                Row(
                    modifier = Modifier
                        .paddingTop(25.9)
                        .clickable {
                            goToSupport()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chat_text),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(R.string.help_and_support),
                        style = text14,
                        color = TextColor,
                        modifier = Modifier.padding(start = 18.6.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .paddingTop(31)
                        .clickable {
                            goToTerms()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.terms),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(R.string.terms_confaitions),
                        style = text14,
                        color = TextColor,
                        modifier = Modifier.padding(start = 18.6.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .paddingTop(31)
                        .clickable {
                            goToAboutApp()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.aboutapp),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(id = R.string.about_app),
                        style = text14,
                        color = TextColor,
                        modifier = Modifier.padding(start = 18.6.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .paddingTop(31)
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                if (languageSheet.isVisible)
                                    languageSheet.hide()
                                else languageSheet.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.langauage),
                            contentDescription = ""
                        )
                        Text(
                            text = stringResource(R.string.langague),
                            style = text14,
                            color = TextColor,
                            modifier = Modifier.padding(start = 18.6.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = if (LocalData.appLocal == "ar") "العربية" else "English",
                            style = text12,
                            color = SecondaryColor
                        )
                        Image(
                            painter = painterResource(id = R.drawable.languagearrow),
                            contentDescription = "",
                            modifier = Modifier.padding(start = 12.3.dp)
                        )
                    }
                }


            }
        }
        LanguageSheet(languageSheet) {
            coroutineScope.launch {
                if (languageSheet.isVisible)
                    languageSheet.hide()
                else languageSheet.animateTo(ModalBottomSheetValue.Expanded)
            }
        }
    }
}
