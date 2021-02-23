package com.dpal.magpy.features.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.avianapps.drivable.drive
import com.dpal.libs.rxandroidext.dispose
import com.dpal.search.databinding.FragmentSearchBinding
import com.jakewharton.rxbinding4.recyclerview.scrollEvents
import com.jakewharton.rxbinding4.widget.textChanges
import hu.akarnokd.rxjava3.bridge.RxJavaBridge.toV2Observable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.io.InvalidClassException
import kotlin.math.max

class SearchFragment(
    private val viewModel: SearchViewModel,
    private val router: SearchRouter
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

        toV2Observable(binding?.search?.textChanges())
            .map { it.toString() }
            .drive(viewModel.input.query)

        toV2Observable(
            binding?.items?.scrollEvents()
                ?.subscribeOn(AndroidSchedulers.mainThread())
                ?.map {
                    when (val manager = binding?.items?.layoutManager) {
                        is LinearLayoutManager -> manager.findLastVisibleItemPosition()
                        is GridLayoutManager -> manager.findLastVisibleItemPosition()
                        else -> throw InvalidClassException("LayoutManager must be LinearLayoutManager or GridLayoutManager")
                    }
                }
                ?.observeOn(AndroidSchedulers.mainThread())
        )
            .scan(0) { max, value -> max(max, value) }
            .distinct()
            .map { it == binding?.items?.adapter?.itemCount?.minus(1) ?: false }
            .filter { it }
            .map { Unit }
            .drive(viewModel.input.loadMore)
    }

    override fun onStart() {
        super.onStart()

        viewModel.games
            .dispose(this) {
                adapter.submitList(it)
            }

        viewModel.gameClicked
            .dispose(this) {
                router.route(it)
            }

        viewModel.searchActive
            .dispose(this) {
                binding?.searchProgressBar?.visibility = if (it) View.VISIBLE else View.GONE
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


fun <T> Observable<T>.debug(tag: String): Observable<T> {
    return this.doOnNext { Log.d(tag, it.toString()) }
}
