package com.dpal.magpy.features.details

import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.Fragment
import coil.load
import com.dpal.magpy.features.details.databinding.FragmentDetailsBinding

class DetailsFragment(
    val id: String,
    val imageUrl: String
): Fragment() {

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentDetailsBinding.inflate(inflater)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbar?.clipToOutline = true
        binding?.image?.clipToOutline = true
        binding?.image?.load(imageUrl)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}