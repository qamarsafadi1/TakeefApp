package com.selsela.takeefapp.utils

import com.orhanobut.hawk.Hawk


open class LocalData {
    companion object {
        var appLocal: String = Hawk.get("appLocal", "ar")
            set(value) {
                field = value
                Hawk.put("appLocal", value)
            }
    var isOnApp: Boolean = Hawk.get("isOnApp", false)
            set(value) {
                field = value
                Hawk.put("isOnApp", value)
            }

        var firstLaunch: Boolean = Hawk.get("firstLaunch", true)
            set(value) {
                field = value
                Hawk.put("firstLaunch", value)
            }
    }

}