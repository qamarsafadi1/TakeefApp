package com.selsela.takeefapp.ui.address

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.selsela.takeefapp.ui.common.EditTextAddress
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.common.SearchBar
import com.selsela.takeefapp.ui.common.Spinner
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.utils.Extensions.Companion.RequestPermission
import com.selsela.takeefapp.utils.Extensions.Companion.bitmapDescriptor
import com.selsela.takeefapp.utils.Extensions.Companion.getMyLocation
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Preview
@Composable
fun AddressView() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState =
    BottomSheetState(BottomSheetValue.Collapsed)
    )


    Color.White.ChangeStatusBarColor()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        GoogleMapView()
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 34.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.53f)
        ) {
            BackButton()
            Card(
                modifier = Modifier
                    .paddingTop(16)
                    .fillMaxWidth()
                    .requiredHeight(48.dp),
                elevation = 6.dp,
                shape = RoundedCornerShape(6.dp)
            ) {
                SearchView()
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.mylocation),
                    contentDescription = ""
                )
            }

        }

        var addressVisible by remember {
            mutableStateOf(true)
        }
        AnimatedVisibility(
            visible = addressVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .paddingTop(16)
                .fillMaxWidth()
                .fillMaxHeight(0.47f)
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
                elevation = 9.dp,
                shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 25.dp)
                            .align(Alignment.TopStart)
                            .fillMaxWidth()
                            .requiredHeight(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "",
                            modifier = Modifier.padding(end = 6.6.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f)
                        )
                        {
                            Text(
                                text = stringResource(R.string.current_location),
                                style = text11,
                                color = SecondaryColor
                            )
                            Text(
                                text = "اسم المنطقة , المدينة , الحي ",
                                style = text12,
                                color = TextColor,
                                modifier = Modifier.paddingTop(3)
                            )
                        }
                        var isFav by remember {
                            mutableStateOf(false)
                        }
                        IconButton(onClick = { isFav = !isFav }) {
                            Image(
                                painter = painterResource(
                                    id =
                                    if (isFav) R.drawable.unfav else R.drawable.fav
                                ),
                                contentDescription = ""
                            )
                        }

                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .paddingTop(16)
                .fillMaxWidth()
                .fillMaxHeight(0.37f)
                .align(Alignment.BottomCenter),
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.area),
                            style = text11,
                            color = SecondaryColor
                        )
                        Spinner(
                            placeHolder = stringResource(R.string.area_name)
                        )
                    }
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.city),
                            style = text11,
                            color = SecondaryColor
                        )
                        Spinner(
                            placeHolder = stringResource(R.string.city_name)
                        )
                    }
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
                val coroutineScope = rememberCoroutineScope()

                ElasticButton(
                    onClick = { addressVisible = !addressVisible
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()

                        }
                       }, title = "متابعة",
                    icon = R.drawable.nexticon,
                    modifier = Modifier.paddingTop(14)
                )

                // Creating a Bottom Sheet


            }

        }


    }
}

@Composable
private fun SearchView() {
    var text by remember {
        mutableStateOf("")
    }
    SearchBar(text) {
        text = it
    }
}

@Composable
private fun BackButton() {
    IconButton(
        onClick = { /*TODO*/ },
    ) {
        Image(
            painter = painterResource(id = R.drawable.circleback),
            contentDescription = ""
        )
    }
}

@Composable
private fun GoogleMapView() {
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
            getMyLocation(context = context) {
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
                icon = bitmapDescriptor(
                    context = context,
                    vectorResId = R.drawable.marker
                )
            )
        }
    }
}