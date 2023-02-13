package com.selsela.takeefapp.ui.order.special

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.order.model.special.Image
import com.selsela.takeefapp.data.order.model.special.SpecificOrder
import com.selsela.takeefapp.ui.common.AsyncImage
import com.selsela.takeefapp.ui.common.components.ImagePreviewer
import com.selsela.takeefapp.ui.order.item.DateView
import com.selsela.takeefapp.ui.order.loading.OrderDetailsLoadingView
import com.selsela.takeefapp.ui.theme.Bg
import com.selsela.takeefapp.ui.theme.Gray2
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.ui.theme.text16Bold
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import de.palm.composestateevents.EventEffect

@Composable
fun SpecialOrderDetailsView(
    orderID: Int,
    onBack: () -> Unit,
    viewModel: SpecialOrderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val viewState: SpecialOrderUiState by viewModel.uiState.collectAsStateLifecycleAware(
        SpecialOrderUiState()
    )

    OrderDetailsContent(viewState, onBack)


    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded)
            viewModel.getSpecialOrderDetails(orderID)
    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = viewModel::onFailure
    ) { error ->
        if (error.status == 403)
            LocalData.clearData()
        Common.handleErrors(
            error.responseMessage,
            error.errors,
            context
        )
    }
}

@Composable
private fun OrderDetailsContent(viewState: SpecialOrderUiState, onBack: () -> Unit) {

    var isShowing by remember {
        mutableStateOf(false)
    }
    var image by remember {
        mutableStateOf("")
    }
    when (viewState.isLoading) {
        false -> OrderDetailsView(viewState.order, onBack) {
            isShowing = !isShowing
            image = it
        }

        true -> OrderDetailsLoadingView()
    }
    ImagePreviewer(isShowing, image) {
        isShowing = !isShowing
    }
}

@Composable
private fun OrderDetailsView(
    order: SpecificOrder?,
    onBack: () -> Unit,
    onClick: (String) -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .requiredHeight(77.dp)
                .background(Color.White)
                .padding(top = 30.dp)
                .padding(horizontal = 6.dp),
        ) {

            IconButton(
                onClick = { onBack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton),
                    contentDescription = ""
                )
            }
            Text(
                text = stringResource(id = R.string.special_order_detail),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = text14Meduim
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 19.dp)
                .fillMaxSize()
                .background(Bg)
        ) {
            Card(
                Modifier
                    .padding(bottom = 8.4.dp, top = 14.dp)
                    .fillMaxSize()
                    .padding(bottom = 8.4.dp)

            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    columns = GridCells.Fixed(3),
                    userScrollEnabled = false,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 21.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = stringResource(id = R.string.order_number),
                                    style = text11,
                                    color = SecondaryColor
                                )
                                Text(
                                    text = "#${order?.orderNumber}",
                                    style = text16Bold,
                                    color = TextColor,
                                    modifier = Modifier.paddingTop(9)
                                )
                            }
                            DateView()

                        }
                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {

                        Row(
                            Modifier
                                .paddingTop(9)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.address_dot),
                                style = text11,
                                color = SecondaryColor
                            )
                            Text(
                                text = "${order?.title}",
                                style = text12,
                                color = TextColor
                            )
                        }
                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.detalis),
                            style = text11,
                            color = SecondaryColor,
                            modifier = Modifier.paddingTop(8)
                        )
                    }
                    item(
                        span = {
                            GridItemSpan(3)
                        }
                    ) {

                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "${order?.description}",
                                style = text12,
                                color = TextColor.copy(0.56f),
                                modifier = Modifier
                                    .paddingTop(8)
                                    .weight(1f)
                            )

                        }

                    }
                    if (order?.images.isNullOrEmpty().not()) {
                        item(
                            span = {
                                GridItemSpan(3)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.attachment),
                                style = text11,
                                color = SecondaryColor,
                                modifier = Modifier.paddingTop(9)
                            )

                        }
                        items(order?.images ?: listOf()) { photo ->
                            AttachmentItem(photo) {
                                onClick(it)
                            }
                        }
                    }
                }

            }

        }
    }

}

@Composable
fun AttachmentItem(photo: Image, onClick: (String) -> Unit) {
    AsyncImage(
        imageUrl = photo.imageUrl,
        modifier = Modifier
            .width(92.dp)
            .height(67.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = Gray2, RoundedCornerShape(4.dp))
            .clickable {
                onClick(photo.imageUrl)
            },
        contentScale = ContentScale.Crop
    )
}
