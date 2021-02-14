package com.dpal.magpy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dpal.magpy.features.search.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Injector.init(this)
    }

    override fun onBackPressed() {
        if (!Injector.appCoordinator.popBackStack()) {
            super.onBackPressed()
        }
    }
}
