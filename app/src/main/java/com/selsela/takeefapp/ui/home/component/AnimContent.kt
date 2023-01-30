package com.selsela.takeefapp.ui.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.data.config.model.Service
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.home.item.DetailsView
import com.selsela.takeefapp.ui.home.item.ServiceItem
import com.selsela.takeefapp.utils.LocalData


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun AnimContent(
    service: Service,
    viewModel: HomeViewModel,
    onSelect: (Boolean) -> Unit,
    onExpand: (Boolean) -> Unit
) {
    var itemExpanded by remember { mutableStateOf(false) }
    var arrowVisibility by remember { mutableStateOf(true) }
    val contentTransition = updateTransition(itemExpanded, label = "Expand")
    Card(
        modifier = Modifier
            .padding(
                bottom =
                if (service.id != LocalData.services?.last()?.id)
                    9.dp
                else 79.dp
            )
            .fillMaxWidth(),
        backgroundColor = service.cellBg(),
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp,
        onClick = { itemExpanded = !itemExpanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start

        ) {

            val selectedISService by remember {
                viewModel.selectedOrderService
            }
            val isSelected =
                if (selectedISService.services.isEmpty().not())
                    selectedISService.services.any { it.serviceId == service.id }
                else false
            ServiceItem(arrowVisibility, service, isSelected)
            contentTransition.AnimatedContent { targetState ->
                if (targetState) {
                    onExpand(true)
                    arrowVisibility = false
                    DetailsView(
                        isSelected, service,
                        viewModel,
                        viewModel::addAcTypeCount
                    ) {
                        itemExpanded = !itemExpanded
                        onSelect(itemExpanded)
                    }
                } else {
                    arrowVisibility = true
                    onExpand(false)
                }
            }
        }
    }
}
