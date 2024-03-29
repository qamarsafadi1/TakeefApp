package com.selsela.takeefapp.ui.account

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.notification.NotificationReceiver
import com.selsela.takeefapp.ui.account.components.Header
import com.selsela.takeefapp.ui.account.components.OrderCards
import com.selsela.takeefapp.ui.account.components.SettingsCards
import com.selsela.takeefapp.ui.account.components.SettingsItems
import com.selsela.takeefapp.ui.account.components.WalletCard
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.common.LanguageSheet
import com.selsela.takeefapp.ui.splash.ChangeNavigationBarColor
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.splash.ConfigViewModel
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.showAlertDialog
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
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
    goToOrder: (Int) -> Unit
) {
    Color.Transparent.ChangeStatusBarOnlyColor()
    Color.White.ChangeNavigationBarColor(true)

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
        if (LocalData.accessToken.isNullOrEmpty().not()) {
            if (!viewModel.isLoaded) {
                viewModel.me()
            }
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
    BrodcastRevicer(context = context){
        if (LocalData.accessToken.isNullOrEmpty().not()) {
                viewModel.me()
        }
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
    goToOrder: (Int) -> Unit,
    goToProfile: () -> Unit,
    goToNotification: () -> Unit,
    goToSupport: () -> Unit,
    goToTerms: () -> Unit,
    goToAboutApp: () -> Unit,
    coroutineScope: CoroutineScope,
    languageSheet: ModalBottomSheetState,
    configViewModel: ConfigViewModel = hiltViewModel()
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
            var user by remember {
                viewModel.user
            }
            Header(
                user,
                viewModel.userLoggedIn.value,
                onBack = {
                    {
                        onBack()
                    }.withDelay(1000)
                }
            ) {
                if (it == Constants.LOG_OUT) {
                    context.showAlertDialog(
                        context.getString(R.string.logout),
                        context.getString(R.string.logout_lbl),
                        context.getString(R.string.yes),
                        context.getString(R.string.no),
                    ) {
                        LocalData.clearData()
                        user = null
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
                        goToOrder(it)
                    }
                    SettingsCards(
                        uiState,
                        goToProfile = {
                            goToProfile()
                        },
                        goToOrder = {
                            goToOrder(it)
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

                SettingsItems(
                    goToSupport,
                    goToLogin,
                    goToTerms,
                    goToAboutApp,
                    coroutineScope,
                    languageSheet
                )
            }
        }

        LanguageSheet(languageSheet) {
            coroutineScope.launch { languageSheet.hide() }
            configViewModel.getConfig()
            onBack()
        }
    }
}


@Composable
private fun BrodcastRevicer(
    context: Context,
    onReceived: () -> Unit
) {
    val receiver: NotificationReceiver = object : NotificationReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onReceived()
        }
    }
    LocalBroadcastManager.getInstance(context).registerReceiver(
        receiver, IntentFilter(Constants.ORDER_STATUS_CHANGED)
    )
}