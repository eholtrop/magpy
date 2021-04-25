package com.example.optional_rx

import com.dpal.libs.optional.Optional
import io.reactivex.rxjava3.core.Observable

fun <T> Observable<Optional<T>>.unwrap(): Observable<T> = this
    .filter { it is Optional.Value<T> }
    .map { (it as Optional.Value<T>).value }
