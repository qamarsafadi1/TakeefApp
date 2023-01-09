package com.selsela.takeefapp.ui.address

import android.annotation.SuppressLint
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CalendarView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.marginEnd
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
import com.selsela.takeefapp.ui.common.IconedButton
import com.selsela.takeefapp.ui.common.ListedBottomSheet
import com.selsela.takeefapp.ui.common.SearchBar
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.BorderColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.TextFieldBg
import com.selsela.takeefapp.ui.theme.text11
import com.selsela.takeefapp.ui.theme.text12
import com.selsela.takeefapp.ui.theme.text14
import com.selsela.takeefapp.ui.theme.text20Book
import com.selsela.takeefapp.utils.Extensions.Companion.RequestPermission
import com.selsela.takeefapp.utils.Extensions.Companion.bitmapDescriptor
import com.selsela.takeefapp.utils.Extensions.Companion.getMyLocation
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.withDelay
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AddressView(
    goToSearchView: (String) -> Unit,
    goToReviewOrder: () -> Unit
) {
    Color.White.ChangeStatusBarColor()
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
                TextColor.ChangeStatusBarColor()
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
                    )
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
                                        addressVisible = !addressVisible
                                        {
                                            addressCardVisible = !addressCardVisible
                                        }.withDelay(100)
                                    }, title = "متابعة",
                                    icon = R.drawable.nexticon,
                                    modifier = Modifier.paddingTop(14)
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
                                addressVisible = !addressVisible
                                addressCardVisible = !addressCardVisible
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
                                addressCardVisible = !addressCardVisible

                                coroutineScope.launch {
                                    if (modalSheetState.isVisible)
                                        modalSheetState.hide()
                                    else
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }, title = "حفظ",
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
fun DatePickerView(
    onBack: () -> Unit,
    goToReviewOrder: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                text = stringResource(R.string.select_lbl),
                style = text14,
                color = SecondaryColor,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.clickable {
                    onBack()
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
        }
        Text(
            text = stringResource(R.string.visit_date),
            style = text20Book,
            color = Color.White,
            modifier = Modifier.paddingTop(13.5)
        )
        Calendar()

        var check by remember {
            mutableStateOf(-1)
        }
        PmAmView(check) {
            check = it
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconedButton(
                onClick = {
                    goToReviewOrder()
                },
                icon = R.drawable.forward_arrow,
                modifier = Modifier
                    .paddingTop(5)
                    .requiredWidth(width = 65.dp)
                    .requiredHeight(48.dp)
            )

        }

    }

}

@Composable
fun PmAmView(selectedItem: Int = -1, onCheck: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        repeat(2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(48.dp)
                    .background(TextFieldBg, RoundedCornerShape(11.dp))
                    .border(width = 1.dp, color = BorderColor, RoundedCornerShape(11.dp))
                    .padding(horizontal = 15.dp)
                    .clickable(

                    ) {
                        onCheck(it)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = if (it == 0) "فترة صباحية" else "فترة مسائية",
                    style = text14,
                    color = Color.White
                )

                Text(
                    text = if (it == 0) "08:00 AM - 12:00 PM" else "12:00 PM - 05:00 PM",
                    style = text14,
                    color = SecondaryColor
                )
                Image(
                    painter =
                    if (it == selectedItem)
                        painterResource(id = R.drawable.checked)
                    else painterResource(id = R.drawable.uncheckedrb),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Preview
@Composable
fun Calendar() {
    CompositionLocalProvider(
        LocalLayoutDirection provides
                LayoutDirection.Ltr
    ) {
        val dateFormat = SimpleDateFormat("dd:MM:yyyy", Locale.ENGLISH)
        val cal = java.util.Calendar.getInstance()
        val date = remember { mutableStateOf(dateFormat.format(cal.time)) }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                { CalendarView(ContextThemeWrapper(it, R.style.CalenderViewCustom)) },
                modifier = Modifier.fillMaxWidth(),
                update = { views ->
                    views.firstDayOfWeek = java.util.Calendar.SATURDAY
                    views.setOnDateChangeListener { _, year, month, dayOfMonth ->
                        cal.set(year, month, dayOfMonth)
                        date.value = dateFormat.format(cal.time).toString()
                    }
                })
        }
    }

}

@Composable
private fun CityAreaView(
    onAreaClick: () -> Unit,
    onCityClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        CityView(Modifier.weight(1f)) {
            onCityClick()
        }
        Spacer(modifier = Modifier.width(width = 8.dp))
        AreaView(
            modifier = Modifier.weight(1f)
        ) {
            onAreaClick()
        }
    }
}

@Composable
private fun AreaView(
    modifier: Modifier,
    onAreaClick: () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.city),
            style = text11,
            color = SecondaryColor
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .requiredHeight(46.dp)
                .background(TextFieldBg, RoundedCornerShape(8.dp))
                .border(1.dp, color = BorderColor, RoundedCornerShape(8.dp))
                .clickable(onClick = {
                    onAreaClick()
                })
                .padding(horizontal = 16.dp)

        ) {
            Text(
                stringResource(id = R.string.city_name),
                style = text11,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(id = R.drawable.spinnerarrow),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)

            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CityView(
    weight: Modifier,
    onCityClick: () -> Unit
) {

    Column(weight) {
        Text(
            text = stringResource(R.string.area),
            style = text11,
            color = SecondaryColor
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .requiredHeight(46.dp)
                .background(TextFieldBg, RoundedCornerShape(8.dp))
                .border(1.dp, color = BorderColor, RoundedCornerShape(8.dp))
                .clickable(onClick = {
                    onCityClick()
                })
                .padding(horizontal = 16.dp)

        ) {
            Text(
                stringResource(id = R.string.area_name),
                style = text11,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(id = R.drawable.spinnerarrow),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)

            )
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun CurrentAddressView(

    modifier: Modifier, addressVisible: Boolean
) {
    AnimatedVisibility(
        visible = addressVisible,
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
}

@Composable
private fun Headerview(
    onSearch: (String) -> Unit
) {
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
            SearchView() {
                onSearch(it)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.mylocation),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun SearchView(
    onSearch: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    SearchBar(text,
        onSearch = {
            onSearch(it)
        }) {
        text = it
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

@Composable
fun GoogleMapView() {
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