package com.selsela.takeefapp.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.selsela.takeefapp.R
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
import com.selsela.takeefapp.utils.Extensions.Companion.showError
import com.selsela.takeefapp.utils.LocalData

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.SPLASH_SCREEN,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {
    val uri = "https://airconditioner.com"

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.SPLASH_SCREEN) {
            SplashView {
                if (LocalData.firstLaunch)
                    navActions.navigateToIntro()
                else navActions.navigateToHome()
            }
        }
        composable(Destinations.INTRO_SCREEN) {
            IntroView(goToHome = navActions::navigateToHome)
        }
        composable(Destinations.HOME_SCREEN) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeView(
                viewModel,
                goToSpecialOrder = navActions::navigateToSpecialOrder,
                goToMyAccount = navActions::navigateToMyAccount,
                goToNotification = navActions::navigateToNotification
            ) {
                if (LocalData.accessToken.isNullOrEmpty()) {
                    navActions.navigateToLogin()
                } else navActions.navigateToAddress()
            }
        }
        composable(Destinations.LOGIN_SCREEN) {
            LoginView(
                goToTerms = navActions::navigateToTermsScreen,
                goToSupport = navActions::navigateToSupport,
                goToHome = navActions::navigateToHome,
                goToVerify = navActions::navigateToVerify
            )
        }
        composable(Destinations.VERIFY_SCREEN) {
            VerifyView(goToAddress = navActions::navigateToHome)
        }
        composable(Destinations.ADDRESS_SCREEN) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Destinations.HOME_SCREEN)
            }
            val sharedViewModel = hiltViewModel<HomeViewModel>(parentEntry)
            AddressView(
                sharedViewModel,
                onBack = navController::navigateUp,
                goToSearchView = { query ->
                    val queryResult = query.ifEmpty { "none" }
                    navActions.navigateToSearchAddress(queryResult)
                },
                goToReviewOrder = navActions::navigateToReviewOrder
            )
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
            SearchAddressView(
                query, parentViewModel, addressViewModel,
                onSelect = navController::navigateUp
            )
        }
        composable(Destinations.REVIEW_ORDER) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Destinations.HOME_SCREEN)
            }
            val parentViewModel = hiltViewModel<HomeViewModel>(parentEntry)
            ReviewOrderView(
                parentViewModel,
                goTo = navActions::navigateToSuccess
            )
        }
        composable(Destinations.SUCCESS) {
            SuccessView(
                goOrders = navActions::navigateToOrdersWithoutBackStack,
                goHome = navActions::navigateToHome
            )
        }
        composable(Destinations.SPECIAL_ORDER) {
            PlaceSpecialOrderView(
                onClose = navActions::navigateToHome
            )
        }
        composable(Destinations.MY_ACCOUNT) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Destinations.HOME_SCREEN)
            }
            val parentViewModel = hiltViewModel<HomeViewModel>(parentEntry)

            MyAccountView(
                homeViewModel = parentViewModel,
                onBack = navController::navigateUp,
                goToLogin = navActions::navigateToLogin,
                goToSpecialOrders = navActions::navigateToSpecialOrders,
                goToNotification = navActions::navigateToNotification,
                goToAboutApp = navActions::navigateToAboutApp,
                goToTerms = navActions::navigateToTermsScreen,
                goToSupport = navActions::navigateToSupport,
                goToProfile = navActions::navigateToProfile,
                goToWallet = navActions::navigateToWallet,
                goToOrder = navActions::navigateToOrders
            )
        }
        composable(Destinations.ORDERS_SCREEN_ARGS) {
            val caseID = it.arguments?.getString("case") ?: ""
            val context = LocalContext.current
            OrdersView(
                caseID.toInt(),
                onBack = navController::navigateUp,
                goToDetails = navActions::navigateToOrderDetails
            ) { latLng, supervisorLatLbg ->
                try {
                    val mapUri =
                        Uri.parse("http://maps.google.com/maps?saddr=${latLng.latitude},${latLng.longitude} &daddr=${supervisorLatLbg.latitude},${supervisorLatLbg.longitude} &dirflg=d")
                    val intent = Intent(Intent.ACTION_VIEW, mapUri)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                } catch (e: Exception) {
                    context.showError(context.getString(R.string.please_download_app))
                }
            }
        }
        composable(Destinations.ORDER_ROUTE_SCREEN) {
            OrderRouteView()
        }
        composable(
            Destinations.ORDER_DETAILS_ARGS,
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/id={id}" })
        ) {
            val id = it.arguments?.getString("id") ?: ""
            OrderDetailsView(id.toInt(),
            onBack = navController::navigateUp)
        }
        composable(Destinations.SPECIAL_ORDERS) {
            SpecialOrders(goToDetails = navActions::navigateToSpecialOrderDetails)
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
            ProfileScreen(
                onBack = navController::navigateUp
            )
        }
        composable(Destinations.WALLET_SCREEN) {
            WalletScreen()
        }
    }
}