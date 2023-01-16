package com.selsela.takeefapp.ui.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text20
import com.selsela.takeefapp.ui.theme.text20Meduim
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddedCostSheet(sheetState: ModalBottomSheetState, onConfirm: () -> Unit) {
    Box() {
        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 42.dp, topStart = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                AddedCostSheetContent(onConfirm)
            }) {

        }
    }

}

@Composable
private fun AddedCostSheetContent(onConfirm: () -> Unit) {
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

        Text(
            text = "قام المشرف باضافة",
            style = text14,
            color = SecondaryColor,
            modifier = Modifier.paddingTop(37.5)
        )

        Text(
            text = "تكلفة اضافية للصيانة",
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
      Column(Modifier.fillMaxWidth()) {
          Text(
              text = "يمكنك قبول او رفض التكلفة الإضافية",
              style = text14,
              color = SecondaryColor,
              modifier = Modifier.paddingTop(24.5)
          )
          Spacer(modifier = Modifier.height(72.dp))
          Column {
              Text(
                  text = "ملاحظة",
                  style = text14,
                  color = LightBlue,
                  modifier = Modifier.paddingTop(19)
              )
              repeat(2) {
                  Row(
                      verticalAlignment = Alignment.CenterVertically,
                      modifier = Modifier.padding(bottom = 11.dp)
                  ) {
                      Image(
                          painter = painterResource(id = R.drawable.ellipse_293),
                          contentDescription = ""
                      )
                      Text(
                          text = "لن يتم عمل الصيانة قبل دفع التكلفة",
                          style = text14,
                          color = SecondaryColor,
                          modifier = Modifier.padding(start = 10.dp)
                      )
                  }
              }
          }
      }

       Row(
           Modifier
               .padding(top = 21.dp)
               .fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically
       ) {
           ElasticButton(
               onClick = { onConfirm() },
               title = stringResource(R.string.reject),
               modifier = Modifier
                   .fillMaxWidth()
                   .weight(1f)
                   .requiredHeight(48.dp),
               colorBg = Red
           )
           Spacer(modifier = Modifier.width(25.dp))
           ElasticButton(
               onClick = { onConfirm() },
               title = stringResource(R.string.accept_1),
               modifier = Modifier
                   .fillMaxWidth()
                   .weight(1f)
                   .requiredHeight(48.dp)
           )
       }


    }
}