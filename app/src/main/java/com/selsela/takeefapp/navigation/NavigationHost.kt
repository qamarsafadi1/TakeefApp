package com.selsela.takeefapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.selsela.takeefapp.ui.address.AddressView
import com.selsela.takeefapp.ui.address.SearchAddressView
import com.selsela.takeefapp.ui.auth.LoginView
import com.selsela.takeefapp.ui.auth.VerifyView
import com.selsela.takeefapp.ui.home.HomeView
import com.selsela.takeefapp.ui.intro.IntroView
import com.selsela.takeefapp.ui.splash.SplashView
import com.selsela.takeefapp.utils.LocalData

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.SPLASH_SCREEN
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.SPLASH_SCREEN) {
            SplashView() {
                if (LocalData.firstLaunch)
                    NavigationActions(navController).navigateToIntro()
                else NavigationActions(navController).navigateToHome()
            }
        }
        composable(Destinations.INTRO_SCREEN) {
            IntroView() {
                NavigationActions(navController).navigateToHome()
            }
        }
        composable(Destinations.HOME_SCREEN) {
            HomeView() {
                NavigationActions(navController).navigateToLogin()
            }
        }
        composable(Destinations.LOGIN_SCREEN) {
            LoginView() {
                NavigationActions(navController).navigateToVerify()
            }
        }
        composable(Destinations.VERIFY_SCREEN) {
            VerifyView(){
                NavigationActions(navController).navigateToAddress()
            }
        }
        composable(Destinations.ADDRESS_SCREEN) {
            AddressView(){ query -> 
                val queryResult = query.ifEmpty { "none" }
                NavigationActions(navController).navigateToSearchAddress(queryResult)
            }
        }
        composable(Destinations.SEARCH_ADDRESS_SCREEN_WITH_ARGUMENT){
            val query = it.arguments?.getString("query") ?: ""
            SearchAddressView(query)
        }
    }
}