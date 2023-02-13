package com.selsela.takeefapp.ui.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.splash.ConfigViewModel
import com.selsela.takeefapp.ui.splash.GeneralUiState
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import  androidx.compose.runtime.*
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.bindHtml
import com.selsela.takeefapp.utils.LocalData
import de.palm.composestateevents.EventEffect

@Composable
fun TermsView(
    viewModel: ConfigViewModel = hiltViewModel()
) {
    val viewState: GeneralUiState by viewModel.uiState.collectAsStateLifecycleAware(GeneralUiState())
    val context = LocalContext.current

    TermContent(viewState)

    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (viewModel.isLoaded.not()) {
            viewModel.getTerms()
        }
    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = viewModel::onFailure
    ) { error ->
        if (error.status == 403)
            LocalData.clearData()

        Common.handleErrors(
            error.responseMessage,
            error.errors,
            context
        )
    }
}

@Composable
private fun TermContent(viewState: GeneralUiState) {
    Column(
        Modifier
            .padding(top = 18.dp)
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 29.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = stringResource(id = R.string.terms_condition),
            style = text16Medium,
            color = TextColor
        )

        viewState.term?.text?.let {
            Text(
                text = bindHtml(it),
                style = text12,
                color = TextColor,
                modifier = Modifier.paddingTop(24)
            )
        }
    }
}