package com.github.dawidowoc.quotesapp.debug

import android.app.Activity

class DebugModeService() {
    fun isDebugMode(activity: Activity): Boolean =
        activity.intent.getBooleanExtra("debug.mode", false)
}