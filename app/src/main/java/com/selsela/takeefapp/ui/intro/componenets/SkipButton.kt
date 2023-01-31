package com.selsela.takeefapp.ui.intro.componenets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.utils.LocalData


@Composable
 fun SkipButton(isSkipVisible: Boolean, goToHome: () -> Unit) {
    if (isSkipVisible) {
        ElasticView(
            onClick = {
                LocalData.firstLaunch = false
                goToHome()
            }) {
            Text(
                stringResource(R.string.Skip),
                style = text14
            )
        }
    }
}
