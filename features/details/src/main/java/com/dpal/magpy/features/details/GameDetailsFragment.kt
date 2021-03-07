package com.dpal.magpy.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class GameDetailsFragment(
    val viewModel: GameDetailsViewModel
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val state by viewModel.details()
                    .subscribeAsState(initial = ViewState.Loading)

                DetailsView(state)
            }
        }
    }
}
