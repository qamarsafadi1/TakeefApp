package com.selsela.takeefapp.ui.splash

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.selsela.takeefapp.R
import com.selsela.takeefapp.ui.common.AppLogoImage
import com.selsela.takeefapp.ui.common.LottieAnimationView
import com.selsela.takeefapp.ui.theme.LightBlue
import com.selsela.takeefapp.ui.theme.Purple40
import com.selsela.takeefapp.ui.theme.TextColor
import com.selsela.takeefapp.ui.theme.sloganStyle
import com.selsela.takeefapp.ui.theme.textMeduim
import com.selsela.takeefapp.utils.Extensions.Companion.RequestPermission
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import kotlinx.coroutines.delay
import androidx.compose.runtime.*

@Composable
fun SplashView(
    viewModel: ConfigViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    Color.White.ChangeStatusBarColor()

    SplashContent(onFinish)

    LaunchedEffect(Unit) {
        /**
         * Get fcm token
         */
        receiveToken()
        viewModel.getConfig()
    }
}

@Composable
private fun SplashContent(onFinish: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogoImage()

        Text(
            text = stringResource(R.string.slogan),
            style = sloganStyle,
            modifier = Modifier.padding(top = 19.7.dp)
        )
        ConditionAnimation()
        Row(modifier = Modifier.padding(top = 150.dp)) {
            Text(
                text = stringResource(R.string.quality),
                style = textMeduim,
                color = Purple40,
                fontSize = 19.sp
            )
            Text(
                text = stringResource(R.string.speed),
                style = textMeduim,
                color = TextColor,
                fontSize = 19.sp,
                modifier = Modifier.padding(horizontal = 9.dp)
            )
            Text(
                text = stringResource(R.string.safty),
                style = textMeduim,
                color = LightBlue,
                fontSize = 19.sp
            )
        };
        val context = LocalContext.current
        var permissionIsGranted by remember {
            mutableStateOf(false)
        }
        context.RequestPermission(
            permission = android.Manifest.permission.POST_NOTIFICATIONS,
        ) {
            permissionIsGranted = it
            if (it){
                onFinish()
            }
        }
        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (permissionIsGranted) {
                    delay(6000)
                    onFinish()
                }
            } else {
                delay(6000)
                onFinish()

            }
        }
    }
}

@Composable
fun ConditionAnimation() {
    LottieAnimationView(
        modifier = Modifier
            .requiredHeight(184.dp)
            .padding(top = 42.dp),
        raw = R.raw.splashanimation
    )
}

@Composable
fun Color.ChangeStatusBarColor(
    isDark: Boolean = false
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = this,
        darkIcons = false
    )
    systemUiController.setNavigationBarColor(
        color = this,
        darkIcons = isDark
    )
}

@Composable
fun Color.ChangeNavigationBarColor() {
    val systemUiController = rememberSystemUiController()
    //  SideEffect {
    systemUiController.setNavigationBarColor(
        color = this,
        darkIcons = false
    )
    //}
}

@Composable
fun Color.ChangeStatusBarOnlyColor() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = this,
        darkIcons = false
    )
}

private fun receiveToken() {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Token =>", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            token.log("Token")
            LocalData.fcmToken = token
        })
}