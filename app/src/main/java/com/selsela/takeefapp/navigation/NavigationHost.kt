package com.selsela.takeefapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.selsela.takeefapp.ui.aboutapp.AboutAppView
import com.selsela.takeefapp.ui.account.MyAccountView
import com.selsela.takeefapp.ui.address.AddressView
import com.selsela.takeefapp.ui.address.AddressViewModel
import com.selsela.takeefapp.ui.address.SearchAddressView
import com.selsela.takeefapp.ui.auth.LoginView
import com.selsela.takeefapp.ui.auth.VerifyView
import com.selsela.takeefapp.ui.general.SuccessView
import com.selsela.takeefapp.ui.home.HomeView
import com.selsela.takeefapp.ui.home.HomeViewModel
import com.selsela.takeefapp.ui.intro.IntroView
import com.selsela.takeefapp.ui.notification.NotificationView
import com.selsela.takeefapp.ui.order.OrderDetailsView
import com.selsela.takeefapp.ui.order.OrderRouteView
import com.selsela.takeefapp.ui.order.OrdersView
import com.selsela.takeefapp.ui.order.ReviewOrderView
import com.selsela.takeefapp.ui.order.special.PlaceSpecialOrderView
import com.selsela.takeefapp.ui.order.special.SpecialOrderDetailsView
import com.selsela.takeefapp.ui.order.special.SpecialOrders
import com.selsela.takeefapp.ui.profile.ProfileScreen
import com.selsela.takeefapp.ui.splash.SplashView
import com.selsela.takeefapp.ui.support.SupportScreen
import com.selsela.takeefapp.ui.terms.TermsView
import com.selsela.takeefapp.ui.wallet.WalletScreen
import com.selsela.takeefapp.utils.LocalData

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.SPLASH_SCREEN,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {

    // TODO: refactor this code with Kotlin reflection ::
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
            val viewModel = hiltViewModel<HomeViewModel>()

            HomeView(
                viewModel,
                goToSpecialOrder = {
                    navActions.navigateToSpecialOrder()
                }, goToMyAccount = {
                    navActions.navigateToMyAccount()
                }) {
                if (LocalData.accessToken.isNullOrEmpty())
                    navActions.navigateToLogin()
                else navActions.navigateToAddress()
            }
        }
        composable(Destinations.LOGIN_SCREEN) {
            LoginView(
                goToTerms = {
                    navActions.navigateToTermsScreen()
                },
                goToSupport = {
                    navActions.navigateToSupport()
                },
                goToHome = {
                    navActions.navigateToHome()
                }
            ) {
                navActions.navigateToVerify()
            }
        }
        composable(Destinations.VERIFY_SCREEN) {
            VerifyView() {
                navActions.navigateToHome()
            }
        }
        composable(Destinations.ADDRESS_SCREEN) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Destinations.HOME_SCREEN)
            }

            val parentViewModel = hiltViewModel<HomeViewModel>(parentEntry)
            AddressView(parentViewModel,
                onBack = { navController.navigateUp() },
                goToSearchView = { query ->
                    val queryResult = query.ifEmpty { "none" }
                    navActions.navigateToSearchAddress(queryResult)
                }) {
                navActions.navigateToReviewOrder()
            }
        }
        composable(Destinations.SEARCH_ADDRESS_SCREEN_WITH_ARGUMENT) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Destinations.HOME_SCREEN)
            }
            val parentViewModel = hiltViewModel<HomeViewModel>(parentEntry)
            val addressEntry = remember(it) {
                navController.getBackStackEntry(Destinations.ADDRESS_SCREEN)
            }
            val addressViewModel = hiltViewModel<AddressViewModel>(addressEntry)
            val query = it.arguments?.getString("query") ?: ""
            SearchAddressView(query,parentViewModel,addressViewModel)
        }
        composable(Destinations.REVIEW_ORDER) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Destinations.HOME_SCREEN ?: "")
            }
            val parentViewModel = hiltViewModel<HomeViewModel>(parentEntry)
            ReviewOrderView(parentViewModel) {
                navActions.navigateToSuccess()
            }
        }
        composable(Destinations.SUCCESS) {
            SuccessView(
                goOrders = {
                    navActions.navigateToOrdersWithoutBackStack()
                }
            ) {
                navActions.navigateToHome()
            }
        }
        composable(Destinations.SPECIAL_ORDER) {
            PlaceSpecialOrderView() {
                navActions.navigateToHome()
            }
        }
        composable(Destinations.MY_ACCOUNT) {
            MyAccountView(
                onBack = navController::navigateUp,
                goToLogin = {
                    navActions.navigateToLogin()
                },
                goToSpecialOrders = {
                    navActions.navigateToSpecialOrders()
                },
                goToNotification = {
                    navActions.navigateToNotification()
                },
                goToAboutApp = navActions::navigateToAboutApp,
                goToTerms = {
                    navActions.navigateToTermsScreen()
                },
                goToSupport = {
                    navActions.navigateToSupport()
                },
                goToProfile = {
                    navActions.navigateToProfile()
                },
                goToWallet = {
                    navActions.navigateToWallet()
                }
            )
            {
                navActions.navigateToOrders(it)
            }
        }
        composable(Destinations.ORDERS_SCREEN_ARGS) {
            val caseID = it.arguments?.getString("case") ?: ""

            OrdersView(
                caseID.toInt(),
                goToDetails = { id ->
                    navActions.navigateToOrderDetails(id)
                }
            ) {
                navActions.navigateToOrderRoute()
            }
        }
        composable(Destinations.ORDER_ROUTE_SCREEN) {
            OrderRouteView()
        }
        composable(Destinations.ORDER_DETAILS_ARGS) {
            val id = it.arguments?.getString("id") ?: ""
            OrderDetailsView(id.toInt()) {
                navController.navigateUp()
            }
        }
        composable(Destinations.SPECIAL_ORDERS) {
            SpecialOrders() {
                navActions.navigateToSpecialOrderDetails(it)
            }
        }
        composable(Destinations.SPECIAL_ORDERS_ARGS) {
            val id = it.arguments?.getString("id") ?: ""
            SpecialOrderDetailsView(id.toInt())
        }
        composable(Destinations.NOTIFICATION_SCREEN) {
            NotificationView()
        }
        composable(Destinations.ABOUT_APP_SCREEN) {
            AboutAppView()
        }
        composable(Destinations.TERMS) {
            TermsView()
        }
        composable(Destinations.TECHNICAL_SUPPORT) {
            SupportScreen()
        }
        composable(Destinations.PROFILE_SCREEN) {
            ProfileScreen() {
                navController.navigateUp()
            }
        }
        composable(Destinations.WALLET_SCREEN) {
            WalletScreen()
        }
    }
}