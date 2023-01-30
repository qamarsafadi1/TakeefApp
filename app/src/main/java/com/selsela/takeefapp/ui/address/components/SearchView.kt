package com.selsela.takeefapp.ui.address.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.selsela.takeefapp.ui.common.SearchBar


@Composable
fun SearchView(
    onSearch: (String) -> Unit
) {
        SearchBar()
}