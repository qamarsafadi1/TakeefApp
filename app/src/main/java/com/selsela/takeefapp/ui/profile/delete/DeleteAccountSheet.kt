package com.selsela.takeefapp.ui.profile.delete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.auth.AuthUiState
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteAccountSheet(
    sheetState: ModalBottomSheetState,
    uiState: AuthUiState,
    onConfirm: () -> Unit
) {
    Box() {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                DeleteAccountSheetContent(uiState,sheetState,onConfirm)
            }) {

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DeleteAccountSheetContent(
    uiState: AuthUiState,
    sheetState: ModalBottomSheetState,
    onConfirm: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(
                horizontal = 24.dp,
                vertical = 10.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.bottomsheet),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.confirm_lbl_1),
            style = text14,
            color = SecondaryColor,
            modifier = Modifier.paddingTop(37.5)
        )

        Text(
            text = stringResource(R.string.delete_account),
            style = text20,
            color = Color.White,
            modifier = Modifier.paddingTop(9.5)
        )
        Image(
            painterResource(id = R.drawable.sad),
            "",
            modifier = Modifier
                .paddingTop(38)
        )

        Text(
            text = stringResource(R.string.delete_account_lbl),
            style = text14,
            color = Color.White,
            modifier = Modifier
                .paddingTop(36.5)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.delete_confirmation),
            style = text20,
            color = Color.White,
            modifier = Modifier.paddingTop(36.5)

        )

        Spacer(modifier = Modifier.height(57.dp))
        Row(
            Modifier
                .padding(top = 21.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElasticView(
                onClick = {  coroutineScope.launch {
                    sheetState.hide()
                } },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.cancel_lbl),
                    style = text16Medium,
                    color = Purple40
                )
            }
            ElasticButton(
                onClick = { onConfirm() }, title = stringResource(R.string.confirm),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .requiredHeight(48.dp),
                colorBg = Red,
                isLoading = uiState.isLoading
            )
        }
    }
}
