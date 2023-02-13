package com.selsela.takeefapp.ui.address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.selsela.takeefapp.R
import com.selsela.takeefapp.data.auth.model.address.FavouriteAddresse
import com.selsela.takeefapp.ui.address.components.SearchView
import com.selsela.takeefapp.ui.address.components.item.FavItem
import com.selsela.takeefapp.ui.common.SearchAddressBar
import com.selsela.takeefapp.ui.common.State
import com.selsela.takeefapp.ui.common.components.LoadingView
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.theme.DividerColor
import com.selsela.takeefapp.ui.theme.FavBg
import com.selsela.takeefapp.ui.theme.Red
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.SecondaryColorAlpha
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.Common
import com.selsela.takeefapp.utils.Extensions.Companion.collectAsStateLifecycleAware
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop

@Composable
fun SearchAddressView(
    query: String?,
    sharedVm: HomeViewModel,
    vm: AddressViewModel = hiltViewModel(),
    onSelect: () -> Unit
) {
    val context = LocalContext.current
    val viewState: AddressUiState by vm.uiState.collectAsStateLifecycleAware(
        AddressUiState()
    )

    when (viewState.state) {
        State.SUCCESS -> {
            viewState.addresses?.let {
                SearchAddressContent(
                    query, it, sharedVm = sharedVm, vm,
                    onSelect
                )
            }
        }

        State.LOADING -> {
            LoadingView()
        }

        State.ERROR -> {
            viewState.error.let {
                Common.handleErrors(
                    it?.responseMessage,
                    it?.errors,
                    context
                )
            }
        }

        else -> {}
    }

    /**
     * Handle Ui state from flow
     */

    LaunchedEffect(Unit) {
        if (!vm.isLoaded)
            vm.getFavAddresses()
    }

}

@Composable
private fun SearchAddressContent(
    query: String?,
    favouriteAddresses: List<FavouriteAddresse>,
    sharedVm: HomeViewModel,
    vm: AddressViewModel,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            var isFav by remember {
                mutableStateOf(query.equals("none"))
            }
            SearchView(query) {
                isFav = it.isEmpty()
                vm.getGoggleSearchAddresses(it)
            }
            Text(
                text = if (isFav.not()) stringResource(R.string.search_result) else stringResource(R.string.fav_addresses),
                style = text12,
                color = SecondaryColor,
                modifier = Modifier.paddingTop(20.9)
            )

            LazyColumn(
                modifier = Modifier
                    .paddingTop(3)
                    .fillMaxWidth()
            ) {
                items(
                    if (isFav) favouriteAddresses else vm.searchAddress
                ) {
                    if (isFav) {
                        FavItem(
                            address = it as FavouriteAddresse,
                            selectAddress = sharedVm::selectAddressFromFav,
                            updateSheetSelected = vm::updateSelectAddress
                        ) {
                            onSelect()
                            sharedVm.currentLocation.value = LatLng( it.latitude, it.longitude)
                        }
                    } else {
                        AddressItem(
                            it as Place,
                            selectAddress = sharedVm::selectAddressFromFav,
                            updateSheetSelected = vm::updateSelectAddress
                        ) {
                            onSelect()
                            sharedVm.currentLocation.value = it.latLng as LatLng
                        }
                    }
                }
            }
        }
    }
}
