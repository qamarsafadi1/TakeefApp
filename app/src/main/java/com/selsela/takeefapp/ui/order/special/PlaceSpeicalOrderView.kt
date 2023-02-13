package com.selsela.takeefapp.ui.order.special

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.theme.SecondaryColor2
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text18
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.selsela.takeefapp.ui.common.EditTextMutltLine
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.InputEditText
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.theme.ButtonBg
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.text10
import com.selsela.takeefapp.ui.theme.text16Line
import java.io.File
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Constants
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
import com.selsela.takeefapp.utils.LocalData
import de.palm.composestateevents.EventEffect

@Preview
@Composable
fun PlaceSpecialOrderView(
    viewModel: SpecialOrderViewModel = hiltViewModel(),
    onClose: () -> Unit
) {

    val viewState: SpecialOrderUiState by viewModel.uiState.collectAsStateLifecycleAware(
        SpecialOrderUiState()
    )
    val context = LocalContext.current
    var isAnimated by remember {
        mutableStateOf(false)
    }

    PlaceSpecialOrderContent(
        viewState,
        viewModel = viewModel,
        isAnimated = isAnimated,
        onClose = onClose,
        placeOrder = viewModel::placeOrder
    )

    /**
     * Handle Ui state from flow
     */

    EventEffect(
        event = viewState.onSuccess,
        onConsumed = viewModel::onSuccess
    ) {
        isAnimated = !isAnimated

    }

    EventEffect(
        event = viewState.onFailure,
        onConsumed = viewModel::onFailure
    ) { error ->
        Common.handleErrors(
            error.responseMessage,
            error.errors,
            context
        )
    }

}

@Composable
private fun PlaceSpecialOrderContent(
    viewState: SpecialOrderUiState,
    viewModel: SpecialOrderViewModel,
    isAnimated: Boolean,
    onClose: () -> Unit,
    placeOrder: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column() {
                if (isAnimated.not()) {
                    AppLogoImage(
                        modifier = Modifier
                            .size(
                                width = 138.05.dp,
                                height = 39.34.dp
                            )
                    )
                }

            }
            Box(
                Modifier
                    .paddingTop(15)
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 21.dp, top = 22.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .background(TextColor, RoundedCornerShape(33.dp))
                        .padding(horizontal = 24.dp)
                        .animateContentSize(tween(600))
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    if (isAnimated.not()) {
                        SpecialOrderFormView(viewModel)
                    } else {
                        SuccessSend() {
                            onClose()
                        }
                    }
                }

                if (isAnimated) {
                    LottieAnimationView(
                        raw = R.raw.send,
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .size(126.dp)
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
        if (isAnimated.not()) {
            ElasticButton(
                onClick = {
                    placeOrder()
                }, title = stringResource(R.string.send_order),
                modifier = Modifier
                    .padding(vertical = 21.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .requiredHeight(48.dp),
                isLoading = viewState.isLoading
            )
        }

    }
}

@Composable
private fun SpecialOrderFormView(viewModel: SpecialOrderViewModel) {
    Column(
        Modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.special_order_1),
            style = text18,
            color = Color.White,
            modifier = Modifier
                .paddingTop(41)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.will_contatc),
            modifier = Modifier
                .paddingTop(16)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = text11,
            color = SecondaryColor2
        )

        Text(
            text = stringResource(R.string.full_name),
            style = text11,
            modifier = Modifier.padding(top = 29.dp)
        )

        InputEditText(
            onValueChange = {
                viewModel.name.value = it
                if (!viewModel.isNameValid.value)
                    viewModel.isNameValid.value = true
            },
            text = viewModel.name.value,
            hint = stringResource(R.string.name),
            inputType = KeyboardType.Text,
            isValid = viewModel.isNameValid.value,
            validationMessage = viewModel.errorMessageName.value,
            modifier = Modifier.padding(top = 16.dp),
            borderColor = viewModel.validateNameBorderColor()
        )
        Text(
            text = stringResource(R.string.order_title),
            style = text11,
            modifier = Modifier.padding(top = 16.dp)
        )

        InputEditText(
            onValueChange = {
                viewModel.title.value = it
                if (!viewModel.isTitleValid.value)
                    viewModel.isTitleValid.value = true
            },
            text = viewModel.title.value,
            hint = stringResource(R.string.order_title),
            inputType = KeyboardType.Text,
            modifier = Modifier.padding(top = 16.dp),
            isValid = viewModel.isTitleValid.value,
            validationMessage = viewModel.errorMessage.value,
            borderColor = viewModel.validateTitleBorderColor()

        )

        Text(
            text = stringResource(R.string.order_details),
            style = text11,
            modifier = Modifier.padding(top = 16.dp)
        )
        EditTextMutltLine(
            onValueChange = {
                viewModel.description.value = it
                if (!viewModel.isDescriptionValid.value)
                    viewModel.isDescriptionValid.value = true

            },
            text = viewModel.description.value,
            hint = stringResource(R.string.write_order_details),
            inputType = KeyboardType.Text,
            modifier = Modifier
                .padding(top = 16.dp),
            isValid = viewModel.isDescriptionValid.value,
            validationMessage = viewModel.errorMessageDescription.value,
            borderColor = viewModel.validateDescriptionBorderColor()

        )

        ImagesChooser(viewModel = viewModel)


    }
}

@Composable
private fun ImagesChooser(viewModel: SpecialOrderViewModel) {
    val photoImages = remember {
        mutableStateListOf<File>()
    }
    val context = LocalContext.current
    val imagePicker = Extensions.mStartActivityForResultMutlipaale(
        context = context,
    ) { file ->
        if (file != null) {
            file.forEach {
                photoImages.add(it)
            }
            viewModel.attachments = file.toMutableList()
        }
    }
    Row(
        modifier = Modifier
            .paddingTop(28)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Row {
                Text(
                    text = stringResource(R.string.add_attachments),
                    style = text11,
                    color = Color.White.copy(0.85f)
                )
                Text(
                    text = stringResource(R.string.optinal),
                    style = text10,
                    color = SecondaryColor.copy(0.70f),
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
            LazyRow() {
                items(photoImages) {
                    Text(
                        text = it.nameWithoutExtension,
                        style = text10,
                        color = SecondaryColor
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = ",",
                        style = text10,
                        color = SecondaryColor
                    )
                }
            }

        }

        IconButton(onClick = {
            Extensions.uploadImages(context, imagePicker, true)
        }) {
            Image(
                painter = painterResource(id = R.drawable.attachment), contentDescription = ""
            )
        }
    }
}

@Composable
fun SuccessSend(
    onClose: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .requiredHeight(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(137.dp))

        Text(
            text = stringResource(R.string.has_been_sent), style = text18,
            color = Color.White
        )
        Text(
            text = stringResource(R.string.will_contact_soon),
            style = text16Line,
            color = Color.White.copy(0.85f),
            textAlign = TextAlign.Center,
            modifier = Modifier.paddingTop(21)
        )

        Spacer(modifier = Modifier.height(20.dp))
        ElasticButton(
            onClick = {
                LocalData.user?.specificOrders =   LocalData.user?.specificOrders!!+1
                onClose()
            }, title = stringResource(R.string.close),
            colorBg = ButtonBg,
            textColor = Purple40,
            modifier = Modifier
                .padding(top = 45.dp)
                .padding(horizontal = 37.dp)
                .fillMaxWidth()
                .requiredHeight(48.dp)
        )
    }
}