package com.selsela.takeefapp.ui.address.components.sheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import com.selsela.takeefapp.ui.address.AddressViewModel
import com.selsela.takeefapp.ui.common.ListedBottomSheet
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterialApi::class)
 fun AreaSheet(
    addressViewModel: AddressViewModel,
    areaSheetState: ModalBottomSheetState,
    addressVisible: Boolean,
    coroutineScope: CoroutineScope
): Boolean {
    var addressVisible1 = addressVisible
    ListedBottomSheet(
        addressViewModel,
        sheetState = areaSheetState,
        ciites = addressViewModel.getCitiesOfAreas(),
        onSelectedItem = addressViewModel::setSelectedCity
    ) {
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
    return addressVisible1
}