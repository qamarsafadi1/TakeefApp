package com.selsela.takeefapp.ui.address

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.address.components.CityAreaView
import com.selsela.takeefapp.ui.address.components.CurrentAddressView
import com.selsela.takeefapp.ui.address.components.DatePickerView
import com.selsela.takeefapp.ui.address.components.GoogleMapView
import com.selsela.takeefapp.ui.address.components.Headerview
import com.selsela.takeefapp.ui.common.EditTextAddress
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.ListedBottomSheet
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AddressView(
    parentViewModel: HomeViewModel,
    goToSearchView: (String) -> Unit,
    goToReviewOrder: () -> Unit
) {
    Color.Transparent.ChangeStatusBarOnlyColor()
    parentViewModel.selectedOrderService.value.totalServicesPrice?.value?.log("fromAddress")
    BottomSheetLayout(
        goToReviewOrder = {
            goToReviewOrder()
        }
    ) {
        goToSearchView(it)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun BottomSheetLayout(
    goToReviewOrder: () -> Unit,
    onSearch: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val citySheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val areaSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    var addressVisible by remember {
        mutableStateOf(true)
    }
    var addressCardVisible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(modalSheetState) {
        snapshotFlow { modalSheetState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                addressVisible = true
            }
        }
    }
    LaunchedEffect(citySheetState) {
        snapshotFlow { citySheetState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                addressVisible = true
            }
        }
    }
    LaunchedEffect(areaSheetState) {
        snapshotFlow { areaSheetState.isVisible }.collect { isVisible ->
            if (!isVisible) {
                addressVisible = true
            }
        }
    }

    BackHandler(modalSheetState.isVisible) {
        addressVisible = !addressVisible
        coroutineScope.launch { modalSheetState.hide() }
    }
    BackHandler(citySheetState.isVisible) {
        coroutineScope.launch { citySheetState.hide() }
    }
    BackHandler(areaSheetState.isVisible) {
        coroutineScope.launch { areaSheetState.hide() }
    }
    Box {
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetShape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                Column(modifier = Modifier.fillMaxHeight(0.85f)) {
                    DatePickerView(
                        onBack = {

                        }
                    ) {
                        goToReviewOrder()
                    }
                }
            }
        ) {
            Scaffold {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                )
                {
                    GoogleMapView()
                    Headerview() {
                        onSearch(it)
                    }
                    CurrentAddressView(
                        Modifier
                            .paddingTop(16)
                            .fillMaxWidth()
                            .fillMaxHeight(0.47f)
                            .align(Alignment.BottomCenter),
                        addressVisible
                    ) {
                        addressVisible = !addressVisible
                        {
                            addressCardVisible = !addressCardVisible
                        }.withDelay(100)
                    }
                    // Add address info form
                    AnimatedVisibility(
                        visible = addressCardVisible,
                        enter = fadeIn(),
                        exit = fadeOut(),
                        modifier = Modifier
                            .paddingTop(16)
                            .fillMaxWidth()
                            .fillMaxHeight(0.37f)
                            .align(Alignment.BottomCenter)
                    ) {
                        Card(
                            modifier = Modifier
                                .animateEnterExit(
                                    // Slide in/out the inner box.
                                    enter = slideInVertically(
                                        initialOffsetY = {
                                            it / 2
                                        },
                                    ),
                                    exit = slideOutVertically(
                                        targetOffsetY = {
                                            it / 2
                                        },
                                    ),
                                ),
                            elevation = 0.dp,
                            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp),
                            backgroundColor = TextColor
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        horizontal = 24.dp,
                                        vertical = 25.dp
                                    ),
                                horizontalAlignment = Alignment.End
                            ) {
                                CityAreaView(onAreaClick = {
                                    addressVisible = !addressVisible
                                    {
                                        coroutineScope.launch {
                                            if (areaSheetState.isVisible)
                                                areaSheetState.hide()
                                            else
                                                areaSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    }.withDelay(100)
                                }) {
                                    addressVisible = !addressVisible
                                    {
                                        coroutineScope.launch {
                                            if (citySheetState.isVisible)
                                                citySheetState.hide()
                                            else
                                                citySheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    }.withDelay(100)
                                }
                                var district by remember {
                                    mutableStateOf("")
                                }
                                EditTextAddress(
                                    onValueChange = {
                                        district = it
                                    }, text =
                                    district,
                                    hint = stringResource(R.string.district),
                                    modifier = Modifier.paddingTop(8)
                                )
                                EditTextAddress(
                                    onValueChange = {
                                        district = it
                                    }, text =
                                    district,
                                    hint = stringResource(R.string.add_note),
                                    modifier = Modifier.paddingTop(8)
                                )
                                ElasticButton(
                                    onClick = {
                                        addressCardVisible = true
                                        coroutineScope.launch {
                                            if (modalSheetState.isVisible)
                                                modalSheetState.hide()
                                            else
                                                modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    }, title = stringResource(R.string.continue_lbl),
                                    icon = R.drawable.nexticon,
                                    modifier = Modifier
                                        .paddingTop(14)
                                        .width(133.dp)
                                        .requiredHeight(48.dp),
                                )

                                // Creating a Bottom Sheet
                            }
                        }
                    }
                }
            }
        }

        if (addressCardVisible.not()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp),
                backgroundColor = TextColor
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 40.dp
                        )
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.clickable {

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

                        ElasticButton(
                            onClick = {
                                addressVisible = !addressVisible
                                addressCardVisible = !addressCardVisible
                            }, title = stringResource(R.string.save_lbl),
                            modifier = Modifier
                                .width(123.dp)
                                .height(48.dp)
                        )

                    }

                    var addressName by remember {
                        mutableStateOf("")
                    }
                    Text(
                        text = stringResource(R.string.address_name),
                        style = text11,
                        color = SecondaryColor,
                        modifier = Modifier.paddingTop(25)
                    )
                    EditTextAddress(
                        onValueChange = {
                            addressName = it
                        }, text =
                        addressName,
                        hint = stringResource(R.string.address_name_exmaple),
                        modifier = Modifier.paddingTop(8)
                    )
                }
            }
        }

        ListedBottomSheet(sheetState = citySheetState)
        ListedBottomSheet(sheetState = areaSheetState)

    }

}


@Composable
fun BackButton() {
    IconButton(
        onClick = { /*TODO*/ },
    ) {
        Image(
            painter = painterResource(id = R.drawable.circleback),
            contentDescription = ""
        )
    }
}
