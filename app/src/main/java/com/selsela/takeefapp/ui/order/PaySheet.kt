package com.selsela.takeefapp.ui.order

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
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.splash.ChangeNavigationBarColor
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.ui.theme.text20Meduim
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PaySheet(sheetState: ModalBottomSheetState, onConfirm: () -> Unit) {
    Box() {
        val coroutineScope = rememberCoroutineScope()

        var check by remember {
            if (LocalData.appLocal == "ar")
                mutableStateOf(0)
            else mutableStateOf(1)
        }

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                PaySheetContent(onConfirm)
            }) {

        }
    }

}

@Composable
private fun PaySheetContent(onConfirm: () -> Unit) {
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
            text = "دفع",
            style = text14,
            color = SecondaryColor,
            modifier = Modifier.paddingTop(37.5)
        )

        Text(
            text = "التكلفة الإضافية للصيانة",
            style = text20,
            color = Color.White,
            modifier = Modifier.paddingTop(9.5)
        )
        Box(
            modifier = Modifier
                .paddingTop(25)
                .fillMaxWidth()
                .height(81.dp)
                .background(LightBlue.copy(.07f), RoundedCornerShape(11.dp))
                .padding(horizontal = 23.5.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.money),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterStart),
                colorFilter = ColorFilter.tint(SecondaryColor2.copy(0.33f))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.cost_1), style = text12,
                    color = SecondaryColor
                )
                Text(
                    text = "310",
                    style = text20Meduim,
                    color = Color.White,
                )
                Text(
                    text = stringResource(R.string.currency), style = text12,
                    color = SecondaryColor,
                )
            }

        }

      Row(modifier = Modifier.fillMaxWidth()) {
          Text(
              text = "طريقة الدفع",
              style = text14,
              color = SecondaryColor,
              modifier = Modifier.paddingTop(24.5)
          )

      }
        PaymentViewDark()

        ElasticButton(
            onClick = { onConfirm() }, title = stringResource(R.string.pay_noew),
            modifier = Modifier
                .padding(top = 21.dp)
                .fillMaxWidth()
                .requiredHeight(48.dp)
        )


    }
}


@Composable
private fun PaymentItemDark(isSelected: Boolean, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .requiredHeight(53.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.visa), contentDescription = ""
        )

        Spacer(modifier = Modifier.width(17.7.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .requiredHeight(53.dp)
                .background(
                    color =
                    LightBlue.copy(0.07f),
                    RoundedCornerShape(11.dp)
                )
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = if (isSelected) Purple40 else LightBlue.copy(0.07f),
                    shape = RoundedCornerShape(11.dp)
                )
                .clip(shape = RoundedCornerShape(11.dp))
                .clickable {
                    onClick()
                }
                .padding(horizontal = 14.3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "فيزا كارد", style = text14,
                color = Color.White
            )
            Image(
                painter =
                if (isSelected)
                    painterResource(id = R.drawable.checked)
                else painterResource(id = R.drawable.uncheckedrbwhitealpha),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun PaymentViewDark() {
    Column(
        modifier = Modifier
            .paddingTop(18)
            .fillMaxWidth()
    ) {
        var selectedPayment by remember {
            mutableStateOf(0)
        }
        repeat(3) {
            PaymentItemDark(selectedPayment == it) {
                selectedPayment = it
            }
        }

    }
}