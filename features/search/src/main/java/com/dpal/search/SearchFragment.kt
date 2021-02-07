package com.dpal.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dpal.domain.game.Search
import com.dpal.search.databinding.FragmentSearchBinding
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import com.avianapps.drivable.drive
import hu.akarnokd.rxjava3.bridge.RxJavaBridge;
import hu.akarnokd.rxjava3.bridge.RxJavaBridge.toV2Observable
import io.reactivex.rxjava3.core.Observable

class SearchFragment(
    private val viewModel: SearchViewModel
) : Fragment() {

    private var binding: FragmentSearchBinding? = null

    private val adapter = GameAdapter()


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

        toV2Observable(binding!!.search.textChanges())
            .map { it.toString() }
            .drive(viewModel.input.query)
    }

    override fun onStart() {
        super.onStart()
        viewModel.games
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is Search.Data -> adapter.submitList(it.games)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.setSupportActionBar(binding?.toolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
