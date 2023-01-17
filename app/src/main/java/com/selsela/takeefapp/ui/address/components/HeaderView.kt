package com.selsela.takeefapp.ui.address.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.address.BackButton
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun Headerview(
    onSearch: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 34.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.53f)
    ) {
        BackButton()
        Card(
            modifier = Modifier
                .paddingTop(16)
                .fillMaxWidth()
                .requiredHeight(48.dp),
            elevation = 6.dp,
            shape = RoundedCornerShape(6.dp)
        ) {
            SearchView() {
                onSearch(it)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.mylocation),
                contentDescription = ""
            )
        }
    }
}