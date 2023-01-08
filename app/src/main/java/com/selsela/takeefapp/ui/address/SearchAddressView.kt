package com.selsela.takeefapp.ui.address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.common.SearchAddressBar
import com.selsela.takeefapp.ui.theme.SecondaryColorAlpha
import com.selsela.takeefapp.utils.Extensions.Companion.log
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun SearchAddressView(query: String?) {
    query?.log("query")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            SearchView(query)

            Text(
                text = if (query.isNullOrEmpty().not()) stringResource(R.string.search_result) else stringResource(R.string.fav_addresses),
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(20.9)
            )

        }

    }
}

@Composable
private fun SearchView(query: String?) {
    var text by remember {
        if (query.isNullOrEmpty())
            mutableStateOf("")
        else mutableStateOf(query)

    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(48.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(6.dp),
        backgroundColor = Color.Transparent,
        border = BorderStroke(1.dp, SecondaryColorAlpha)
    ) {
        SearchAddressBar(text) {
            text = it
        }
    }

}