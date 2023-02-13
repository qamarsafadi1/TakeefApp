package com.selsela.takeefapp.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage


@Composable
 fun Header(
    isNewNotification: Int,
    goToNotification: () -> Unit,
    goToMyAccount: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ElasticView(onClick = { goToMyAccount() }) {
            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "",
            )
        }
        ElasticView(onClick = { goToNotification() }) {
            Image(
                painter = painterResource(id =

                if (isNewNotification == 0) R.drawable.notifiication
                else R.drawable.notificationnew),
                contentDescription = ""
            )
        }

    }
    AppLogoImage(
        modifier = Modifier.size(
            width = 128.05.dp,
            height = 36.34.dp
        )
    )
}