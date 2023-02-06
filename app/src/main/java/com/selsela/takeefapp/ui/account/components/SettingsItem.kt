package com.selsela.takeefapp.ui.account.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterialApi::class)
 fun SettingsItems(
    goToSupport: () -> Unit,
    goToLogin: () -> Unit,
    goToTerms: () -> Unit,
    goToAboutApp: () -> Unit,
    coroutineScope: CoroutineScope,
    languageSheet: ModalBottomSheetState
) {
    Column {
        Row(
            modifier = Modifier
                .paddingTop(25.9)
                .clickable {
                    goToSupport()
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.chat_text),
                contentDescription = ""
            )
            Text(
                text = stringResource(R.string.help_and_support),
                style = text14,
                color = TextColor,
                modifier = Modifier.padding(start = 18.6.dp)
            )
        }

        Row(
            modifier = Modifier
                .paddingTop(31)
                .clickable {
                    goToTerms()
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.terms),
                contentDescription = ""
            )
            Text(
                text = stringResource(R.string.terms_confaitions),
                style = text14,
                color = TextColor,
                modifier = Modifier.padding(start = 18.6.dp)
            )
        }

        Row(
            modifier = Modifier
                .paddingTop(31)
                .clickable {
                    goToAboutApp()
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.aboutapp),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.about_app),
                style = text14,
                color = TextColor,
                modifier = Modifier.padding(start = 18.6.dp)
            )
        }

        Row(
            modifier = Modifier
                .paddingTop(31)
                .fillMaxWidth()
                .clickable {
                    coroutineScope.launch {
                        if (languageSheet.isVisible)
                            languageSheet.hide()
                        else languageSheet.animateTo(ModalBottomSheetValue.Expanded)
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.langauage),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(R.string.langague),
                    style = text14,
                    color = TextColor,
                    modifier = Modifier.padding(start = 18.6.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (LocalData.appLocal == "ar") "العربية" else "English",
                    style = text12,
                    color = SecondaryColor
                )
                Image(
                    painter = painterResource(id = R.drawable.languagearrow),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 12.3.dp)
                )
            }
        }
    }
}
