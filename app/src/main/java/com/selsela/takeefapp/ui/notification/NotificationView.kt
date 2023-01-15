package com.selsela.takeefapp.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.notification.cell.NotificationItem
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import androidx.compose.runtime.*


@Preview
@Composable
fun NotificationView() {
    TextColor.ChangeStatusBarColor()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TextColor)
    ) {
        var selectedNotification by remember {
            mutableStateOf(0)
        }
        LazyColumn(
            modifier = Modifier
                .paddingTop(53)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            items(4) {
                NotificationItem(it, it == selectedNotification) {
                    selectedNotification = it
                }
            }
        }
    }

}