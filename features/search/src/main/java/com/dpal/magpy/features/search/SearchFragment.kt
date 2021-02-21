package com.dpal.magpy.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dpal.search.databinding.FragmentSearchBinding
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import com.avianapps.drivable.drive
import hu.akarnokd.rxjava3.bridge.RxJavaBridge.toV2Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SearchFragment(
    private val viewModel: SearchViewModel,
    private val router: SearchRouter
) : Fragment() {

    private var binding: FragmentSearchBinding? = null

    private val adapter = GameAdapter()

    private val compositeDisposable = CompositeDisposable()

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

        compositeDisposable.add(
            viewModel.games
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.submitList(it)
                }
        )

        compositeDisposable.add(
            viewModel.gameClicked
                .subscribe {
                    router.route(it)
                }
        )

        compositeDisposable.add(
            viewModel.searchActive
                .subscribe {
                    binding?.searchProgressBar?.visibility = if (it) View.VISIBLE else View.GONE
                }
        )
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.setSupportActionBar(binding?.toolbar)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
