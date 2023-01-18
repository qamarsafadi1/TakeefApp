package com.selsela.takeefapp.ui.home.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qamar.elasticview.ElasticView
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.config.model.Service
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.TextColorHint
import com.selsela.takeefapp.ui.theme.TextColorHintAlpha60
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text12Meduim
import com.selsela.takeefapp.ui.theme.text16Medium


@Composable
fun DetailsView(
    service: Service,
    viewModel: HomeViewModel = hiltViewModel(),
    onCollapse: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            thickness = 1.dp,
            color = LightBlue.copy(.14f),
            modifier = Modifier.padding(
                top = 38.dp,
                bottom = 28.dp
            )
        )
        Text(
            text = stringResource(R.string.condation_type),
            style = text12,
            color = TextColorHint
        )
        val acsType = remember { viewModel.acTypes!!.map { it }.toMutableStateList() }

        Column(modifier = Modifier.padding(top = 12.dp)) {
            repeat(acsType.size) {
                ConditionTypeView(acsType[it])
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
                    text = "${service.price}",
                    style = text16Medium
                )
                Text(
                    text = stringResource(id = R.string.currency_1),
                    style = text12Meduim,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Text(
                text = stringResource(R.string.maintinance_lbl),
                style = text12,
                color = TextColorHintAlpha60,
                modifier = Modifier.padding(top = 12.dp)
            )

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
                    onClick = { onCollapse() },
                    title = stringResource(R.string.select),
                    modifier = Modifier
                        .width(167.dp)
                        .requiredHeight(48.dp)
                )
            }

        }

    }
}