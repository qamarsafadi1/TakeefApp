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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.address.components.CityAreaView
import com.selsela.takeefapp.ui.address.components.CurrentAddressView
import com.selsela.takeefapp.ui.address.components.DatePickerView
import com.selsela.takeefapp.ui.address.components.DistrictView
import com.selsela.takeefapp.ui.address.components.GoogleMapView
import com.selsela.takeefapp.ui.address.components.Headerview
import com.selsela.takeefapp.ui.common.EditTextAddress
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.ListedBottomSheet
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.splash.ChangeNavigationBarColor
import com.selsela.takeefapp.ui.splash.ChangeStatusBarOnlyColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.showError
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AddressView(
    parentViewModel: HomeViewModel,
    onBack: () -> Unit,
    goToSearchView: (String) -> Unit,
    goToReviewOrder: () -> Unit
) {
    Color.Transparent.ChangeStatusBarOnlyColor()
    TextColor.ChangeNavigationBarColor()
    BottomSheetLayout(
        parentViewModel = parentViewModel,
        onBack = onBack,
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
    parentViewModel: HomeViewModel,
    addressViewModel: AddressViewModel = hiltViewModel(),
    onBack: () -> Unit,
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
    val districtSheetState = rememberModalBottomSheetState(
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
    LaunchedEffect(districtSheetState) {
        snapshotFlow { districtSheetState.isVisible }.collect { isVisible ->
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
    BackHandler(districtSheetState.isVisible) {
        coroutineScope.launch { districtSheetState.hide() }
    }
    val context = LocalContext.current
    Box {
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetShape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp),
            sheetBackgroundColor = TextColor,
            sheetContent = {
                Column(modifier = Modifier.fillMaxHeight(0.85f)) {
                    DatePickerView(
                        viewModel = parentViewModel,
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

                    GoogleMapView(viewModel = parentViewModel,addressViewModel)
                    Headerview(onBack = { onBack() }) {
                        onSearch(it)
                    }
                    CurrentAddressView(
                        parentViewModel,
                        Modifier
                            .paddingTop(16)
                            .fillMaxWidth()
                            .fillMaxHeight(0.47f)
                            .align(Alignment.BottomCenter),
                        addressVisible
                    ) {
                        if (it) addressViewModel.isFav = 1
                        else addressViewModel.isFav = 0
                        addressViewModel.isFav.log("isFAV")
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
                                            it/2
                                        },
                                    ),
                                    exit = slideOutVertically(
                                        targetOffsetY = {
                                            it/2
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
                                    if (addressViewModel.selectedAreaName.value != "") {
                                        addressVisible = !addressVisible
                                        {
                                            coroutineScope.launch {
                                                if (areaSheetState.isVisible)
                                                    areaSheetState.hide()
                                                else
                                                    areaSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                            }
                                        }.withDelay(100)
                                    }
                                }, addressViewModel) {
                                    addressViewModel.clearSelected()
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
                                DistrictView(addressViewModel) {
                                    if (addressViewModel.getDistrictOfCities().isEmpty().not()
                                    ) {
                                        addressVisible = !addressVisible
                                        {
                                            coroutineScope.launch {
                                                if (districtSheetState.isVisible)
                                                    districtSheetState.hide()
                                                else
                                                    districtSheetState.animateTo(
                                                        ModalBottomSheetValue.Expanded
                                                    )
                                            }
                                        }.withDelay(100)
                                    } else {
                                        context.showError("لا يوجد احياء لهذه المدينة")
                                    }
                                }
                                EditTextAddress(
                                    onValueChange = {
                                        addressViewModel.note.value = it
                                    }, text =
                                    addressViewModel.note.value,
                                    hint = stringResource(R.string.add_note),
                                    modifier = Modifier.paddingTop(8)
                                )
                                ElasticButton(
                                    onClick = {
                                        parentViewModel.address = addressViewModel.createAddress()
                                        addressCardVisible = true
                                        if (parentViewModel.address != null) {
                                            coroutineScope.launch {
                                                if (modalSheetState.isVisible)
                                                    modalSheetState.hide()
                                                else
                                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                            }
                                        } else context.showError("الرجاء تعبئة تفاصيل العنوان")
                                    },
                                    title = stringResource(R.string.continue_lbl),
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

        ListedBottomSheet(
            addressViewModel,
            sheetState = citySheetState, ciites = LocalData.ciites,
            onSelectedItem = addressViewModel::setSelectedArea
        ) {
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
        ListedBottomSheet(
            addressViewModel,
            sheetState = areaSheetState,
            ciites = addressViewModel.getCitiesOfAreas(),
            onSelectedItem = addressViewModel::setSelectedCity
        ) {
            addressVisible = !addressVisible
            {
                coroutineScope.launch {
                    if (areaSheetState.isVisible)
                        areaSheetState.hide()
                    else
                        areaSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }.withDelay(100)
        }

        ListedBottomSheet(
            addressViewModel,
            sheetState = districtSheetState,
            ciites = addressViewModel.getDistrictOfCities(),
            onSelectedItem = addressViewModel::setSelectedDistrict
        ) {
            addressVisible = !addressVisible
            {
                coroutineScope.launch {
                    if (districtSheetState.isVisible)
                        districtSheetState.hide()
                    else
                        districtSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }.withDelay(100)
        }

    }

}


@Composable
fun BackButton(
    onBack: () -> Unit
) {
    IconButton(
        onClick = { onBack() },
    ) {
        Image(
            painter = painterResource(id = R.drawable.circleback),
            contentDescription = ""
        )
    }
}
