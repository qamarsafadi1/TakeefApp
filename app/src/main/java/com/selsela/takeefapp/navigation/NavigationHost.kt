package com.selsela.takeefapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.selsela.takeefapp.ui.intro.IntroView
import com.selsela.takeefapp.ui.splash.SplashView

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.SPLASH_SCREEN
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.SPLASH_SCREEN){
            SplashView(){
                NavigationActions(navController).navigateToIntro()
            }
        }
        composable(Destinations.INTRO_SCREEN){
            IntroView("Qamar")
        }
    }
}