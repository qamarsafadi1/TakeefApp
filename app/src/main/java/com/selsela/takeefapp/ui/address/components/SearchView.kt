package com.selsela.takeefapp.ui.address.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.selsela.takeefapp.ui.common.SearchBar


@Composable
fun SearchView(
    onSearch: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    SearchBar(text,
        onSearch = {
            onSearch(it)
        }) {
        text = it
    }
}