package com.n41t.magpy

actual fun Log.d(tag: String?, message: String) {
    android.util.Log.d(tag, message)
}