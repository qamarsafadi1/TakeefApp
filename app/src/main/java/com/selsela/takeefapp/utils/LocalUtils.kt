package com.selsela.takeefapp.utils

import android.content.Context
import com.yariksoffice.lingver.Lingver

object LocalUtils {

    fun Context.setLocale(lang: String) {
        LocalData.appLocal = lang
        Lingver.getInstance().setLocale(this, lang)


    }
}