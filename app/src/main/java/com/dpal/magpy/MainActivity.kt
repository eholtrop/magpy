package com.dpal.magpy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dpal.search.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.content, SearchFragment(Injector.searchViewModel))
            .commit()
    }
}
