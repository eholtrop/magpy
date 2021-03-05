package com.dpal.libs.rxcache

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import java.util.concurrent.atomic.AtomicReference

class ObservableCache<T>(
    private val obs: Observable<T>
) : Observable<T>() {

    private var value = AtomicReference<T>()

    override fun subscribeActual(observer: Observer<in T>?) {
        obs.doOnLifecycle(
            { observer?.onSubscribe(it) },
            { }
        )
            .subscribe(
                {
                    if (value.get() != it) {
                        value.set(it)
                        observer?.onNext(value.get())
                    }
                },
                { observer?.onError(it) },
                { observer?.onComplete() }
            )
    }

    fun clear() = value.set(null)
}
