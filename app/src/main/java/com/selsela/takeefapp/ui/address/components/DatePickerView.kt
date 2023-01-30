package com.selsela.takeefapp.ui.address.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.WorkPeriod
import com.selsela.takeefapp.ui.common.IconedButton
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text20Book
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun DatePickerView(
    viewModel: HomeViewModel,
    onBack: () -> Unit,
    goToReviewOrder: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 40.dp)
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                text = stringResource(R.string.select_lbl),
                style = text14,
                color = SecondaryColor,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.clickable {
                    onBack()
                },
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.back), style = text11,
                    color = SecondaryColor
                )

            }
        }
        Text(
            text = stringResource(R.string.visit_date),
            style = text20Book,
            color = Color.White,
            modifier = Modifier.paddingTop(13.5)
        )
        Calendar(
            onDateSelection = viewModel::selectDate
        )

        PmAmView(
            selectedItem = viewModel.selectedPeriodId.value.id,
            onCheck = viewModel::selectWorkPeriod
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconedButton(
                onClick = {
                    goToReviewOrder()
                },
                icon = R.drawable.forward_arrow,
                modifier = Modifier
                    .paddingTop(5)
                    .requiredWidth(width = 65.dp)
                    .requiredHeight(48.dp)
            )

        }

    }

}

@Composable
fun PmAmView(selectedItem: Int = -1, onCheck: (WorkPeriod) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        repeat(LocalData.workPeriods?.size ?: 0) {
            val isSelected = LocalData.workPeriods?.get(it)?.id == selectedItem
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(48.dp)
                    .background(TextFieldBg, RoundedCornerShape(11.dp))
                    .clip(RoundedCornerShape(11.dp))
                    .clickable {
                        onCheck(LocalData.workPeriods?.get(it)!!)
                    }
                    .border(width = 1.dp, color = BorderColor, RoundedCornerShape(11.dp))
                    .padding(horizontal = 15.dp)
                 ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = LocalData.workPeriods?.get(it)?.name ?: "",
                    style = text14,
                    color = Color.White
                )

                Text(
                    text = "${
                        LocalData.workPeriods?.get(it)?.getTimeFromFormatted()
                    } - ${LocalData.workPeriods?.get(it)?.getTimeToFormatted()}",
                    style = text14,
                    color = SecondaryColor
                )
                Image(
                    painter =
                    if (isSelected)
                        painterResource(id = R.drawable.checked)
                    else painterResource(id = R.drawable.uncheckedrb),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}
