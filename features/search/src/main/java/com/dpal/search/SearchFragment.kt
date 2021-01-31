package com.dpal.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dpal.search.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null

    private val adapter = GameAdapter()

    private val viewModel = SearchViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentSearchBinding.inflate(inflater)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.items.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.submitList(viewModel.games(""))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
