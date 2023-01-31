package com.selsela.takeefapp.ui.address.components

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
 fun DistrictSheet(
    addressViewModel: AddressViewModel,
    districtSheetState: ModalBottomSheetState,
    addressVisible: Boolean,
    coroutineScope: CoroutineScope
) {
    var addressVisible1 = addressVisible
    ListedBottomSheet(
        addressViewModel,
        sheetState = districtSheetState,
        ciites = addressViewModel.getDistrictOfCities(),
        onSelectedItem = addressViewModel::setSelectedDistrict
    ) {
        addressVisible1 = !addressVisible1
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
