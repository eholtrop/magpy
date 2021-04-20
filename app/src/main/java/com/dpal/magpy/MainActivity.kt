package com.dpal.magpy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.dpal.magpy.di.Injector

class MainActivity : AppCompatActivity() {

    private lateinit var onBackPressed: (() -> Boolean)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.init(this)
        setContent {
            onBackPressed = app()
        }
    }

    override fun onBackPressed() {
        if (!onBackPressed.invoke()) {
            super.onBackPressed()
        }
    }
}
