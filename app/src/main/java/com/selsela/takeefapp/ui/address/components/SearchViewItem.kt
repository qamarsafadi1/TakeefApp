package com.selsela.takeefapp.ui.address.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.ui.common.SearchAddressBar
import com.selsela.takeefapp.ui.theme.SecondaryColorAlpha


@Composable
 fun SearchView(
    query: String?,
    onTextChange: (String) -> Unit
) {
    var text by remember {
        if (query.isNullOrEmpty() || query == "none")
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
            onTextChange(it)
        }
    }

}