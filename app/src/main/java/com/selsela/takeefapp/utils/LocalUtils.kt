package com.selsela.takeefapp.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.selsela.takeefapp.ui.splash.ConfigViewModel
import com.selsela.takeefapp.utils.Extensions.Companion.getActivity
import com.yariksoffice.lingver.Lingver

object LocalUtils {

    fun Context.setLocale(lang: String) {
        LocalData.appLocal = lang
        Lingver.getInstance().setLocale(this, lang)
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LocalData.appLocal)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}