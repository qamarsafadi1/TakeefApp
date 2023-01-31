package com.selsela.takeefapp.ui.address.components

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.address.AddressViewModel
import com.selsela.takeefapp.ui.common.EditTextAddress
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.utils.Extensions.Companion.showError
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
 fun AddressInfo(
    modifier: Modifier,
    addressCardVisible: Boolean,
    addressViewModel: AddressViewModel,
    addressVisible: Boolean,
    coroutineScope: CoroutineScope,
    areaSheetState: ModalBottomSheetState,
    citySheetState: ModalBottomSheetState,
    districtSheetState: ModalBottomSheetState,
    context: Context,
    parentViewModel: HomeViewModel,
    modalSheetState: ModalBottomSheetState,
    onCardAnimation: () -> Unit
) {
    var addressCardVisible1 = addressCardVisible
    var addressVisible1 = addressVisible
    AnimatedVisibility(
        visible = addressCardVisible1,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
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
                        addressVisible1 = !addressVisible1
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
                    addressVisible1 = !addressVisible1
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
                        addressVisible1 = !addressVisible1
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
                        onCardAnimation()
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
