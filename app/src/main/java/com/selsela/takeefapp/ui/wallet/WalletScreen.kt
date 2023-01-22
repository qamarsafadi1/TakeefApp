package com.selsela.takeefapp.ui.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text40
import com.selsela.takeefapp.ui.wallet.cell.TransactionItem
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.selsela.takeefapp.data.auth.model.wallet.Wallet
import com.selsela.takeefapp.ui.auth.WalletUiState
import com.selsela.takeefapp.ui.common.components.EmptyView
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.getCurrency
import de.palm.composestateevents.EventEffect

@Preview
@Composable
fun WalletScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val viewState: WalletUiState by viewModel.walletUiState.collectAsStateLifecycleAware(
        WalletUiState()
    )
    WalletContent(viewState)

    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.wallet()
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
private fun WalletContent(viewState: WalletUiState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.walletbg),
            contentDescription = ""
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CurrentBalance(viewState.wallet?.balance ?: 0.0)
            TransactionList(viewState.wallet?.wallet)
        }
    }
}

@Composable
private fun TransactionList(wallet: List<Wallet>?) {
    Column(
        Modifier
            .paddingTop(6)
            .fillMaxWidth()
            .background(TextColor, RoundedCornerShape(33.dp))
            .padding(horizontal = 22.dp)
            .padding(top = 26.dp, bottom = 96.dp)
    ) {
        Text(
            text = stringResource(R.string.transactions),
            style = text14,
            color = SecondaryColor
        )

        Box() {
            if (wallet.isNullOrEmpty().not()) {
                Column() {
                    LazyColumn(
                        modifier = Modifier
                            .paddingTop(33)
                            .fillMaxWidth()
                    ) {
                        items(wallet ?: mutableListOf()) {
                            TransactionItem(it)
                        }
                    }
                }
            } else {
                EmptyView(
                    title = stringResource(R.string.no_transaction),
                    description = stringResource(R.string.no_transaction_lbl)
                )
            }
        }

    }
}

@Composable
private fun CurrentBalance(balance: Double) {
    Column(
        Modifier
            .fillMaxWidth()
            .requiredHeight(132.dp)
            .background(LightBlue.copy(0.28f), RoundedCornerShape(33.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.current_balance),
            style = text14,
            color = SecondaryColor
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.paddingTop(9)
        ) {
            Text(
                text = "$balance",
                style = text40,
                color = LightBlue
            )
            Text(
                text = stringResource(id = R.string.currency_1, getCurrency()),
                style = text14,
                color = TextColor,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}