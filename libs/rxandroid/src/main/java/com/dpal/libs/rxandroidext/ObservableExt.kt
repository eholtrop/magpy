package com.dpal.libs.rxandroidext

import android.widget.Toast
import androidx.fragment.app.Fragment
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

fun <T : Any> Observable<T>.dispose(fragment: Fragment, action: Consumer<T>): Disposable {
    return this.observeOn(AndroidSchedulers.mainThread())
        .autoDispose(AndroidLifecycleScopeProvider.from(fragment))
        .subscribe(
            action,
            {
                Toast.makeText(
                    fragment.requireContext(),
                    it.localizedMessage,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        )
}