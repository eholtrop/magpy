package com.dpal.magpy

import androidx.fragment.app.FragmentManager

sealed class AppState {
    object Search : AppState()
    data class Details(val imageUrl: String) : AppState()
}

class AppCoordinator(
    private val supportFragmentManager: FragmentManager
) {

    var state: AppState = AppState.Search
        set(value) {
            when (value) {
                AppState.Search -> {
                    supportFragmentManager.beginTransaction()
                        .add(
                            R.id.content,
                            Injector.searchFragment
                        )
                        .commit()
                }
                is AppState.Details -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.content,
                            Injector.detailsFragment(value.imageUrl)
                        )
                        .addToBackStack("details")
                        .commit()
                }
            }
            field = value
        }

    init {
        state = AppState.Search
    }

    fun popBackStack(): Boolean {
        return if (supportFragmentManager.findFragmentByTag("details") != null) {
            supportFragmentManager.popBackStackImmediate()
            true
        } else {
            false
        }
    }
}
