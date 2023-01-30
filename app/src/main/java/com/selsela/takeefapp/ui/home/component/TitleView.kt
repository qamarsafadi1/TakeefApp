package com.selsela.takeefapp.ui.home.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
 fun TitleView() {
    Text(
        text = stringResource(R.string.glad_to_serve),
        style = text18,
        modifier = Modifier.paddingTop(12)
    )
    Text(
        text = stringResource(R.string.serve_lbl),
        style = text14,
        modifier = Modifier.paddingTop(10),
        color = TextColor.copy(0.40f)
    )
}
