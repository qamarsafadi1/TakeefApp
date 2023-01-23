package com.selsela.takeefapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import com.selsela.takeefapp.navigation.Destinations
import com.selsela.takeefapp.navigation.NavigationHost
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TakeefAppTheme
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.navigation.Navigation.bindToolbarTitle
import com.selsela.takeefapp.navigation.Navigation.showingBackButton
import com.selsela.takeefapp.ui.common.ElasticButton
import com.selsela.takeefapp.ui.order.AddedCostSheet
import com.selsela.takeefapp.utils.ModifiersExtension.paddingTop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    )
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeefAppTheme {
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                val paySheet = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
                    skipHalfExpanded = true
                )

                CompositionLocalProvider(
                    LocalMutableContext provides remember { mutableStateOf(context) },
                ) {
                    CompositionLocalProvider(
                        LocalContext provides LocalMutableContext.current.value,
                    ) {
                        // Handle layout direction based on language
                        CompositionLocalProvider(
                            LocalLayoutDirection provides
                                    if (LocalData.appLocal == "ar") LayoutDirection.Rtl
                                    else LayoutDirection.Ltr
                        ) {
                            val navController = rememberNavController()
                            val currentRoute =
                                navController.currentBackStackEntryFlow.collectAsState(
                                    initial = navController.currentBackStackEntry
                                )
                            FitSystemWindow(currentRoute)
                            Box(Modifier.fillMaxSize()) {
                                Scaffold(
                                    topBar = {
                                        if (currentRoute.value?.destination?.route != Destinations.HOME_SCREEN && currentRoute.value?.destination?.route != Destinations.SPLASH_SCREEN
                                            && currentRoute.value?.destination?.route != Destinations.INTRO_SCREEN
                                            && currentRoute.value?.destination?.route != Destinations.ADDRESS_SCREEN
                                            && currentRoute.value?.destination?.route != Destinations.MY_ACCOUNT
                                            && currentRoute.value?.destination?.route != Destinations.ORDER_DETAILS_ARGS
                                            && currentRoute.value?.destination?.route != Destinations.PROFILE_SCREEN
                                        ) {
                                            if (currentRoute.value?.destination?.route != Destinations.VERIFY_SCREEN)
                                                Color.White.ChangeStatusBarColor()
                                            else TextColor.ChangeStatusBarColor(true)

                                            CenterAlignedTopAppBar(
                                                title = {
                                                    currentRoute.value?.let {
                                                        val title =
                                                            navController.bindToolbarTitle(
                                                                currentRoute = it
                                                            )
                                                        Text(
                                                            text = title,
                                                            style = text14Meduim,
                                                            modifier = Modifier.fillMaxWidth(),
                                                            textAlign = TextAlign.Center,
                                                            color = if (it.destination.route == Destinations.NOTIFICATION_SCREEN) Color.White
                                                            else TextColor
                                                        )
                                                    }
                                                },
                                                navigationIcon = {
                                                    currentRoute.value?.let {
                                                        val isShowing =
                                                            navController.showingBackButton(
                                                                it
                                                            )
                                                        if (isShowing) {
                                                            IconButton(onClick = {
                                                                navController.navigateUp()
                                                            }) {
                                                                Icon(
                                                                    painter = painterResource(id = R.drawable.backbutton),
                                                                    contentDescription = "",

                                                                    tint =
                                                                    SecondaryColor
                                                                )
                                                            }

                                                        }
                                                    }

                                                },
                                                actions = {
                                                    BindMenuItems(currentRoute)
                                                },
                                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                                    containerColor =
                                                    if (
                                                        currentRoute.value?.destination?.route != Destinations.VERIFY_SCREEN &&
                                                        currentRoute.value?.destination?.route != Destinations.NOTIFICATION_SCREEN
                                                    )
                                                        Color.White
                                                    else TextColor
                                                )
                                            )
                                        }
                                    }
                                ) {
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        color = Color.White
                                    ) {
                                        NavigationHost(navController)
                                    }
                                }

                                val sheetState = rememberModalBottomSheetState(
                                    initialValue = ModalBottomSheetValue.Hidden,
                                    confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
                                    skipHalfExpanded = true
                                )
                                AddedCostSheet(sheetState = sheetState) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BindMenuItems(currentRoute: State<NavBackStackEntry?>) {

    }

    @Composable
    private fun FitSystemWindow(currentRoute: State<NavBackStackEntry?>) {
        "${currentRoute.value?.destination?.route}".log("ROUTE")
        if (currentRoute.value?.destination?.route == Destinations.MY_ACCOUNT
            || currentRoute.value?.destination?.route == Destinations.ADDRESS_SCREEN
            || currentRoute.value?.destination?.route == Destinations.ORDER_DETAILS_ARGS
            || currentRoute.value?.destination?.route == Destinations.PROFILE_SCREEN
        ) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        } else {
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }

}

val LocalMutableContext = staticCompositionLocalOf<MutableState<Context>> {
    error("LocalMutableContext not provided")
}
