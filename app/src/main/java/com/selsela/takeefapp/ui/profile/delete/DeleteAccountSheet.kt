package com.selsela.takeefapp.ui.profile.delete

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.splash.ChangeNavigationBarColor
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.ui.theme.text20Meduim
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteAccountSheet(sheetState: ModalBottomSheetState, onConfirm: () -> Unit) {
    Box() {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                DeleteAccountSheetContent(onConfirm)
            }) {

        }
    }

}

@Composable
private fun DeleteAccountSheetContent(onConfirm: () -> Unit) {
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
            text = "تاكيد",
            style = text14,
            color = SecondaryColor,
            modifier = Modifier.paddingTop(37.5)
        )

        Text(
            text = "حذف الحساب",
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
            text = "لن تتكن من الوصول الي بياناتك لاحقا في حالة حذف الحساب",
            style = text14,
            color = Color.White,
            modifier = Modifier
                .paddingTop(36.5)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "تأكيد الحذف؟",
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
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "الغاء",
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
                colorBg = Red
            )
        }
    }
}
