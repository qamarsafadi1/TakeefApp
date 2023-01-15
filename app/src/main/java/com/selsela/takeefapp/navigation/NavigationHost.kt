package com.selsela.takeefapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.selsela.takeefapp.ui.account.MyAccountView
import com.selsela.takeefapp.ui.address.AddressView
import com.selsela.takeefapp.ui.address.SearchAddressView
import com.selsela.takeefapp.ui.auth.LoginView
import com.selsela.takeefapp.ui.auth.VerifyView
import com.selsela.takeefapp.ui.general.SuccessView
import com.selsela.takeefapp.ui.home.HomeView
import com.selsela.takeefapp.ui.intro.IntroView
import com.selsela.takeefapp.ui.order.OrderDetailsView
import com.selsela.takeefapp.ui.order.OrderRouteView
import com.selsela.takeefapp.ui.order.OrdersView
import com.selsela.takeefapp.ui.order.ReviewOrderView
import com.selsela.takeefapp.ui.order.special.PlaceSpecialOrderView
import com.selsela.takeefapp.ui.order.special.SpecialOrderDetailsView
import com.selsela.takeefapp.ui.order.special.SpecialOrders
import com.selsela.takeefapp.ui.splash.SplashView
import com.selsela.takeefapp.utils.LocalData

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.SPLASH_SCREEN,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.SPLASH_SCREEN) {
            SplashView() {
                if (LocalData.firstLaunch)
                    navActions.navigateToIntro()
                else navActions.navigateToHome()
            }
        }
        composable(Destinations.INTRO_SCREEN) {
            IntroView() {
                navActions.navigateToHome()
            }
        }
        composable(Destinations.HOME_SCREEN) {
            HomeView(goToSpecialOrder = {
                navActions.navigateToSpecialOrder()
            }, goToMyAccount = {
                navActions.navigateToMyAccount()
            }) {
                navActions.navigateToLogin()
            }
        }
        composable(Destinations.LOGIN_SCREEN) {
            LoginView() {
                navActions.navigateToVerify()
            }
        }
        composable(Destinations.VERIFY_SCREEN) {
            VerifyView() {
                navActions.navigateToAddress()
            }
        }
        composable(Destinations.ADDRESS_SCREEN) {
            AddressView(
                goToSearchView = { query ->
                    val queryResult = query.ifEmpty { "none" }
                    navActions.navigateToSearchAddress(queryResult)
                }) {
                navActions.navigateToReviewOrder()
            }
        }
        composable(Destinations.SEARCH_ADDRESS_SCREEN_WITH_ARGUMENT) {
            val query = it.arguments?.getString("query") ?: ""
            SearchAddressView(query)
        }
        composable(Destinations.REVIEW_ORDER) {
            ReviewOrderView {
                navActions.navigateToSuccess()
            }
        }
        composable(Destinations.SUCCESS) {
            SuccessView()
        }
        composable(Destinations.SPECIAL_ORDER) {
            PlaceSpecialOrderView()
        }
        composable(Destinations.MY_ACCOUNT) {
            MyAccountView(
                onBack = {
                    navController.navigateUp()
                },
                goToSpecialOrders = {
                    navActions.navigateToSpecialOrders()
                }
            )
            {
                navActions.navigateToOrders()
            }
        }
        composable(Destinations.ORDERS_SCREEN) {
            OrdersView(
                goToDetails = {
                    navActions.navigateToOrderDetails()
                }
            ) {
                navActions.navigateToOrderRoute()
            }
        }
        composable(Destinations.ORDER_ROUTE_SCREEN) {
            OrderRouteView()
        }
        composable(Destinations.ORDER_DETAILS) {
            OrderDetailsView() {
            }
        }
        composable(Destinations.SPECIAL_ORDERS){
            SpecialOrders(){
                navActions.navigateToSpecialOrderDetails()
            }
        }
        composable(Destinations.SPECIAL_ORDERS_DETAILS){
            SpecialOrderDetailsView()
        }
    }
}