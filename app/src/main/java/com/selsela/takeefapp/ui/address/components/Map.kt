package com.selsela.takeefapp.ui.address.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.selsela.takeefapp.R
import com.selsela.takeefapp.utils.Extensions
import com.selsela.takeefapp.utils.Extensions.Companion.RequestPermission


@Composable
fun GoogleMapView(
    @DrawableRes markerDrawable: Int = R.drawable.marker
) {
    val context = LocalContext.current
    var permissionIsGranted by remember {
        mutableStateOf(false)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 15f)
    }
    val markerState = MarkerState(position = LatLng(0.0, 0.0))

    context.RequestPermission(
        permission = android.Manifest.permission.ACCESS_COARSE_LOCATION,
    ) {
        permissionIsGranted = it
        if (it) {
            Extensions.getMyLocation(context = context) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
                markerState.position = it
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
        ) {
            Marker(
                state = markerState,
                icon = Extensions.bitmapDescriptor(
                    context = context,
                    vectorResId = markerDrawable
                )
            )
        }
    }
}