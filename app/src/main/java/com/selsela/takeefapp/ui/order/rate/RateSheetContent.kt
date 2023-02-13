package com.selsela.takeefapp.ui.order.rate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.RateProperitiesSupervisor
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.InputEditText
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.common.components.RatingBar
import com.selsela.takeefapp.ui.order.OrderUiState
import com.selsela.takeefapp.ui.order.OrderViewModel
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.UnselectedColor
import com.selsela.takeefapp.ui.theme.YellowColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12White
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text260Book
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.showError
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop


@Composable
fun RateSheetContent(
    viewModel: OrderViewModel,
    viewState: OrderUiState,
    onConfirm: (Int, List<List<Rate>>, String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
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

        LottieAnimationView(
            raw = R.raw.star,
            modifier = Modifier
                .width(119.dp)
                .height(154.dp)
        )

        Text(
            text = stringResource(R.string.rate_service),
            style = text260Book,
            color = Color.White,
            modifier = Modifier.paddingTop(29)
        )
        val rateArray = mutableListOf<List<Rate>>()

        Column {
            repeat(viewModel.rateItemArray.value.size ?: 0) {
                QualityRating(viewModel.rateItemArray.value.get(it)) {
                    //[[rate_properity_id,rate]]
                    val foundedItem = rateArray.find { rateItem ->
                        it.id == rateItem.find { rate -> rate.id == it.id }?.id
                    }
                    foundedItem?.log("findRate")
                    if (foundedItem == null) {
                        val rateItem = listOf(Rate(it.id, it.rate))
                        rateArray.add(rateItem)
                    } else {
                        rateArray.remove(foundedItem)
                        val rateItem = listOf(Rate(it.id, it.rate))
                        rateArray.add(rateItem)
                    }
                    viewModel.rateArray = rateArray.toMutableStateList()
                    rateArray.log("rateArray")
                }

            }
        }
        NoteView(viewModel) {
            viewModel.note = it
        }
        Column(
            Modifier
                .paddingTop(27)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.rate_shows_only_for_admin),
                style = text11,
                color = SecondaryColor2
            )
            Text(
                text = stringResource(R.string.from_maintinance),
                style = text11,
                color = SecondaryColor2,
                modifier = Modifier.paddingTop(10)
            )
        }

        val context = LocalContext.current
        ElasticButton(
            onClick = {
                viewModel.rateArray.size.log(" rateArray ")
                if (viewModel.rateArray.isEmpty().not()) {
                    if (viewModel.note.isEmpty().not()) {
                        onConfirm(viewState.order?.id!!, viewModel.rateArray, viewModel.note)
                        viewModel.note = ""
                        viewModel.rateItemArray.value = listOf()
                    } else {
                        context.showError(context.getString(R.string.please_enter_note))
                    }
                } else {
                    context.showError(context.getString(R.string.please_rate_at_least))
                }
            }, title = stringResource(R.string.send_rate),
            modifier = Modifier
                .padding(vertical = 30.dp)
                .fillMaxWidth()
                .requiredHeight(48.dp),
            isLoading = viewState.state == State.LOADING
        )
    }
}

@Composable
private fun NoteView(
    viewModel: OrderViewModel,
    onValueChange: (String) -> Unit
) {
    var note by remember { mutableStateOf("") }
    InputEditText(
        text =  viewModel.note,
        hint = stringResource(R.string.write_note),
        modifier = Modifier.padding(top = 34.dp),
        onValueChange = {
            note = it
            onValueChange(note)
        },
        textStyle = text12White,
    )
}

@Composable
private fun QualityRating(
    rateItem: RateProperitiesSupervisor,
    qualityRate: (RateProperitiesSupervisor) -> Unit
) {
    rateItem.rate.log("rateItem.rate")
    Row(
        modifier = Modifier
            .paddingTop(46.3)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = rateItem.name,
            style = text14,
            color = SecondaryColor2
        )
        var ratring by remember {
            mutableStateOf(0f)
        }
        RatingBar(
            rating = ratring,
            space = 2.dp,
            imageVectorEmpty = ImageVector.vectorResource(R.drawable.staricon),
            imageVectorFFilled = ImageVector.vectorResource(R.drawable.starfill),
            tintEmpty = UnselectedColor,
            tintFilled = YellowColor,
            animationEnabled = true,
            gestureEnabled = true,
            itemSize = 25.dp
        ) {
            ratring = 0f
            qualityRate(rateItem.copy(rate = it))
        }
    }
}

@Composable
private fun PriceRating() {
    Row(
        modifier = Modifier
            .paddingTop(37.3)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.service_proce),
            style = text14,
            color = SecondaryColor2
        )
        var rating by remember { mutableStateOf(3.7f) }
        RatingBar(
            rating = rating,
            space = 2.dp,
            imageVectorEmpty = ImageVector.vectorResource(R.drawable.staricon),
            imageVectorFFilled = ImageVector.vectorResource(R.drawable.starfill),
            tintEmpty = UnselectedColor,
            tintFilled = YellowColor,
            animationEnabled = true,
            gestureEnabled = true,
            itemSize = 25.dp
        ) {
            rating = it
        }
    }
}

@Composable
private fun SupervisorRating() {
    Row(
        modifier = Modifier
            .paddingTop(37.3)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = stringResource(R.string.supervisor_attitude),
            style = text14,
            color = SecondaryColor2
        )
        var rating by remember { mutableStateOf(3.7f) }
        RatingBar(
            rating = rating,
            space = 2.dp,
            imageVectorEmpty = ImageVector.vectorResource(R.drawable.staricon),
            imageVectorFFilled = ImageVector.vectorResource(R.drawable.starfill),
            tintEmpty = UnselectedColor,
            tintFilled = YellowColor,
            animationEnabled = true,
            gestureEnabled = true,
            itemSize = 25.dp
        ) {
            rating = it
        }
    }
}