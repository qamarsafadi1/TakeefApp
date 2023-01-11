package com.selsela.takeefapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.selsela.takeefapp.navigation.Destinations
import com.selsela.takeefapp.navigation.NavigationHost
import com.selsela.takeefapp.ui.intro.ChangeStatusBarColorWhiteNav
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TakeefAppTheme
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.Extensions.Companion.bindToolbarTitle
import com.selsela.takeefapp.utils.Extensions.Companion.showingBackButton
import com.selsela.takeefapp.utils.LocalData

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeefAppTheme {
                // Handle layout direction based on language
                val context = LocalContext.current
                CompositionLocalProvider(
                    LocalMutableContext provides remember { mutableStateOf(context) },
                ) {
                    CompositionLocalProvider(
                        LocalContext provides LocalMutableContext.current.value,
                    ) {
                        CompositionLocalProvider(
                            LocalLayoutDirection provides
                                    if (LocalData.appLocal == "ar") LayoutDirection.Rtl
                                    else LayoutDirection.Ltr
                        ) {
                            val navController = rememberNavController()
                            val currentRoute = navController.currentBackStackEntryFlow.collectAsState(
                                initial = navController.currentBackStackEntry
                            )
                            if (currentRoute.value?.destination?.route == Destinations.MY_ACCOUNT
                                || currentRoute.value?.destination?.route == Destinations.ADDRESS_SCREEN
                            ) {
                                WindowCompat.setDecorFitsSystemWindows(window, false)
                            } else {
                                WindowCompat.setDecorFitsSystemWindows(window, true)

                            }

                            Scaffold(
                                topBar = {
                                    if (currentRoute.value?.destination?.route != Destinations.HOME_SCREEN && currentRoute.value?.destination?.route != Destinations.SPLASH_SCREEN
                                        && currentRoute.value?.destination?.route != Destinations.INTRO_SCREEN
                                        && currentRoute.value?.destination?.route != Destinations.ADDRESS_SCREEN
                                        && currentRoute.value?.destination?.route != Destinations.MY_ACCOUNT
                                    ) {
                                        if (currentRoute.value?.destination?.route != Destinations.VERIFY_SCREEN)
                                            Color.White.ChangeStatusBarColor()
                                        else TextColor.ChangeStatusBarColor(true)

                                        CenterAlignedTopAppBar(
                                            title = {
                                                currentRoute.value?.let {
                                                    val title =
                                                        navController.bindToolbarTitle(currentRoute = it)
                                                    Text(
                                                        text = title,
                                                        style = text14Meduim,
                                                        modifier = Modifier.fillMaxWidth(),
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            },
                                            navigationIcon = {
                                                currentRoute.value?.let {
                                                    val isShowing = navController.showingBackButton(
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
                                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                                containerColor =
                                                if (
                                                    currentRoute.value?.destination?.route != Destinations.VERIFY_SCREEN
                                                )
                                                    Color.White
                                                else TextColor
                                            )
                                        )
                                    }
                                }
                            ) {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = Color.White
                                ) {
                                    NavigationHost(navController)
                                }
                            }

                        }
                        // your app
                    }
                }

            }
        }
    }

}

val LocalMutableContext = staticCompositionLocalOf<MutableState<Context>> {
    error("LocalMutableContext not provided")
}
