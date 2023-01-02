package com.selsela.takeefapp.utils

import com.orhanobut.hawk.Hawk


open class LocalData {
    companion object {
        var appLocal: String = Hawk.get("appLocal", "ar")
            set(value) {
                field = value
                Hawk.put("appLocal", value)
            }
    }

}