package com.selsela.takeefapp.ui.address.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.address.AddressViewModel
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.Extensions.Companion.RequestPermission
import com.selsela.takeefapp.utils.GetLocationDetail


@Composable
fun GoogleMapView(
    viewModel: HomeViewModel,
    addressViewModel: AddressViewModel,
    @DrawableRes markerDrawable: Int = R.drawable.marker,
) {
    val context = LocalContext.current
    var permissionIsGranted by remember {
        mutableStateOf(false)
    }
    // To control and observe the map camera
    val cameraPositionState = rememberCameraPositionState()
    val markerState = MarkerState(position = LatLng(0.0, 0.0))

    context.RequestPermission(
        permission = android.Manifest.permission.ACCESS_COARSE_LOCATION,
    ) {
        permissionIsGranted = it
        if (it) {
            Extensions.getMyLocation(context = context) {
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(viewModel.currentLocation.value, 15f)
                markerState.position = it
                addressViewModel.lat = viewModel.currentLocation.value.latitude
                addressViewModel.lng = viewModel.currentLocation.value.longitude
            }
        }
    }

    if (permissionIsGranted) {

        val mapStyleOptions: MapStyleOptions = MapStyleOptions.loadRawResourceStyle(
            context,
            R.raw.styledark
        )
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = false,
                mapStyleOptions = mapStyleOptions
            ),
            uiSettings = MapUiSettings(
                compassEnabled = false,
                zoomControlsEnabled = false
            )
        )
        Box(
            modifier = Modifier
                .padding(bottom = 35.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = markerDrawable),
                contentDescription = ""
            )

            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = {
                    Extensions.getMyLocation(context = context) {
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
                        markerState.position = it

                    }
                }) {
                Image(
                    painter = painterResource(id = R.drawable.mylocation),
                    contentDescription = "",
                )
            }
        };


    }
    UpdateSelectedAddress(
        cameraPositionState,
        addressViewModel,
        updateSelectedAddress = viewModel::updateSelectedAddress
    )

}

@Composable
private fun UpdateSelectedAddress(
    cameraPositionState: CameraPositionState,
    addressViewModel: AddressViewModel,
    updateSelectedAddress: (String, LatLng) -> Unit
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        if (cameraPositionState.isMoving.not() && cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
            val newAddress = GetLocationDetail(context).getCurrentAddress(
                cameraPositionState.position.target.latitude,
                cameraPositionState.position.target.longitude
            ) ?: ""

            addressViewModel.lat = cameraPositionState.position.target.latitude
            addressViewModel.lng = cameraPositionState.position.target.longitude
            updateSelectedAddress(newAddress, cameraPositionState.position.target)
        }
    }
}