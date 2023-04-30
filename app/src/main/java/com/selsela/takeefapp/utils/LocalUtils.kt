package com.selsela.takeefapp.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.yariksoffice.lingver.Lingver

object LocalUtils {

    fun Context.setLocale(lang: String) {
        LocalData.appLocal = lang
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(LocalData.appLocal)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}