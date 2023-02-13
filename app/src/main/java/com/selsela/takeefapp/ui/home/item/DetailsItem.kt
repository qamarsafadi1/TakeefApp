package com.selsela.takeefapp.ui.home.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.AcType
import com.selsela.takeefapp.data.config.model.Service
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.Blue
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.TextColorHint
import com.selsela.takeefapp.ui.theme.TextColorHintAlpha60
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text16Medium
import com.selsela.takeefapp.utils.Constants.MAINTENANCE
import com.selsela.takeefapp.utils.Extensions.Companion.getCurrency
import com.selsela.takeefapp.utils.Extensions.Companion.log
import kotlinx.coroutines.selects.select


@Composable
fun DetailsView(
    isSelected: Boolean? = false,
    service: Service,
    viewModel: HomeViewModel,
    onChange: (
        service: Service,
        count: Int,
        acyType: AcType
    ) -> Unit,
    onCollapse: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        if (service.multipleCount == 0) {
            Divider(
                thickness = 1.dp,
                color = LightBlue.copy(.14f),
                modifier = Modifier.padding(
                    top = 38.dp,
                    bottom = 28.dp
                )
            )
        } else {
            Row(
                Modifier
                    .padding(top = 21.dp, bottom = 12.dp)
                    .fillMaxWidth()
                    .requiredHeight(39.dp)
                    .background(Blue, RoundedCornerShape(10.dp))
                    .padding(horizontal = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.service_cost_dot),
                    style = text12Meduim,
                    color = TextColor,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "${service.price}",
                        style = text12Meduim,
                        color = TextColor
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(id = R.string.currency_1, getCurrency()),
                        style = text12, color = SecondaryColor
                    )
                    Text(
                        text = "/",
                        style = text12, color = SecondaryColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))


                    Text(
                        text = stringResource(R.string.conditinar),
                        style = text12, color = SecondaryColor
                    )

                }
            }
        }
        Text(
            text = stringResource(R.string.condation_type),
            style = text12,
            color = TextColorHint
        )
        val acsType = remember {
            viewModel.getAcTypesList().value!!.map { acyType ->
                if (isSelected == true) {
                    if (viewModel.selectedOrderService.value.services.isEmpty().not()) {
                        acyType.count = viewModel.selectedOrderService.value.services.find {
                            it.acyTypeOd == acyType.id && it.serviceId == service.id
                        }?.count ?: 0
                    } else {
                        acyType.count = 0
                        "heeyEmpty${acyType.count}".log()

                    }
                } else acyType.count = 0
                acyType.count.log(" acyType.count")
                acyType
            }
        }

        Column(modifier = Modifier.padding(top = 12.dp)) {
            repeat(acsType.size ?: 0) {
                acsType[it].count.log("acsType[it]")
                ConditionTypeView(acsType[it]) { count, acyType ->
                    count.log("countcount")
                    onChange(service, count, acyType)
                    viewModel.updateServiceToOrderItem()
                }
            }
            Row(
                modifier = Modifier.padding(top = 49.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.cost_dot),
                    style = text16Medium
                )
                Text(
                    text = viewModel.getCost(service),
                    style = text16Medium
                )
                Text(
                    text = stringResource(id = R.string.currency_1, getCurrency()),
                    style = text12Meduim,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            if (service.id == MAINTENANCE) {
                Text(
                    text = stringResource(R.string.maintinance_lbl),
                    style = text12,
                    color = TextColorHintAlpha60,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Divider(
                thickness = 1.dp,
                color = LightBlue.copy(.14f),
                modifier = Modifier.padding(
                    top = 21.dp,
                    bottom = 28.dp
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 23.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ElasticView(
                    onClick = { },
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.topward_arrow),
                        contentDescription = ""
                    )
                }

                ElasticButton(
                    onClick = {
                        if (isSelected == true) {
                            viewModel.acyTypes.removeIf {
                                it.serviceId == service.id
                            }
                        }
                        viewModel.updateServiceToOrderItem()
                        onCollapse()
                    },
                    title = if (isSelected == false) stringResource(R.string.select) else stringResource(
                        R.string.select_cancel
                    ),
                    modifier = Modifier
                        .width(167.dp)
                        .requiredHeight(48.dp)
                )
            }

        }

    }
}