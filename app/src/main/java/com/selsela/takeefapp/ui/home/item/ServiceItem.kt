package com.selsela.takeefapp.ui.home.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.Service
import com.selsela.takeefapp.ui.common.AsyncImage
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text20
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import com.selsela.takeefapp.utils.Extensions.Companion.log

@Composable
fun ServiceItem(
    arrowVisibility: Boolean, service: Service,
    isSelected: Boolean =false
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(140.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(service.imageBackgroundColor(), RoundedCornerShape(24.dp))
                .size(100.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(imageUrl = service.imageUtl,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.padding(horizontal = 5.dp))
        }

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 19.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.order_service),
                style = text12,
                color = TextColor.copy(0.44f)
            )
            Text(
                text = service.name,
                style = text20,
                color = TextColor
            )
            Text(
                text = service.description,
                style = text12,
                color = TextColor.copy(0.44f)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 18.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter =
                if (isSelected)
                    painterResource(id = R.drawable.checked)
                else painterResource(id = R.drawable.unchecked),
                contentDescription = ""
            )

            if (arrowVisibility) {
                Image(
                    painter = painterResource(id = R.drawable.backward_arrow),
                    contentDescription = ""
                )
            }

        }
    }
}
