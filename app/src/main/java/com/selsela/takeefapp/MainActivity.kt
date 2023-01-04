package com.selsela.takeefapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.selsela.takeefapp.navigation.Destinations
import com.selsela.takeefapp.navigation.NavigationHost
import com.selsela.takeefapp.ui.splash.ChangeStatusBarColor
import com.selsela.takeefapp.ui.splash.SplashView
import com.selsela.takeefapp.ui.theme.SecondaryColor
import com.selsela.takeefapp.ui.theme.TakeefAppTheme
import com.selsela.takeefapp.ui.theme.text14Meduim
import com.selsela.takeefapp.utils.Extensions.Companion.bindToolbarTitle
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.Extensions.Companion.showingBackButton
import com.selsela.takeefapp.utils.LocalData

class MainActivity : ComponentActivity() {
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeefAppTheme {
                // Handle layout direction based on language
                CompositionLocalProvider(
                    LocalLayoutDirection provides
                            if (LocalData.appLocal == "ar") LayoutDirection.Rtl
                            else LayoutDirection.Ltr
                ) {
                    val navController = rememberNavController()
                    val currentRoute = navController.currentBackStackEntryFlow.collectAsState(
                        initial = navController.currentBackStackEntry
                    )
                    Scaffold(
                        topBar = {
                            if (currentRoute.value?.destination?.route != Destinations.HOME_SCREEN && currentRoute.value?.destination?.route != Destinations.SPLASH_SCREEN && currentRoute.value?.destination?.route != Destinations.INTRO_SCREEN) {
                                Color.White.ChangeStatusBarColor()
                                TopAppBar(
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
                                                        tint = SecondaryColor
                                                    )
                                                }

                                            }
                                        }

                                    },
                                    backgroundColor = Color.White, elevation = 0.dp
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
            }
        }
    }
}
