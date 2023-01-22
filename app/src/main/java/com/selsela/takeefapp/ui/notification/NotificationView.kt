package com.selsela.takeefapp.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.notification.item.NotificationItem
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.auth.AuthViewModel
import com.selsela.takeefapp.ui.auth.NotificationUiState
import com.selsela.takeefapp.ui.common.components.EmptyView
import com.selsela.takeefapp.ui.common.components.LoadingNotificationView
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import de.palm.composestateevents.EventEffect


@Preview
@Composable
fun NotificationView(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val viewState: NotificationUiState by viewModel.notificationUiState.collectAsStateLifecycleAware(
        NotificationUiState()
    )
    NotificationContent(viewState){
        viewModel.deleteNotification(it)
    }

    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.getNotification()
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
private fun NotificationContent(viewState: NotificationUiState,
onDelete: (Int) -> Unit) {
    TextColor.ChangeStatusBarColor()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TextColor)
    ) {
        when (viewState.isLoading) {
            true -> LoadingNotificationView()
            false -> NotificationList(viewState){
                onDelete(it)
            }
        }
    }
}

@Composable
private fun NotificationList(
    viewState: NotificationUiState,
    onDelete : (Int) -> Unit
) {
    if (viewState.notifications.isNullOrEmpty().not()) {
        LazyColumn(
            modifier = Modifier
                .paddingTop(53)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            items(viewState.notifications ?: mutableListOf()) {
                NotificationItem(it){
                    onDelete(it)
                }
            }
        }
    } else {
        EmptyView(
            title = stringResource(R.string.no_notifications),
            description = stringResource(R.string.no_notifications_lbl)
        )
    }
}