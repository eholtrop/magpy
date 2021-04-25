package com.dpal.magpy.tagcache

import com.dpal.data.tag.Tag
import com.dpal.data.tag.TagCache
import com.dpal.libs.optional.Optional
import com.dpal.libs.optional.optional
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

internal typealias TagId = String

class InMemoryTagCache : TagCache {

    private val cache = BehaviorSubject.createDefault(
        hashMapOf<TagId, Tag>(
            UUID.randomUUID().toString().let { id ->
                id to Tag(
                    id,
                    "Owned",
                    mutableListOf()
                )
            },
            UUID.randomUUID().toString().let { id ->
                id to Tag(
                    id,
                    "Want",
                    mutableListOf()
                )
            },
        )
    )

    override fun getAll(): Observable<List<Tag>> {
        return cache.hide().map { it.values.toList() }
    }

    override fun getForResource(id: String): Observable<List<Tag>> {
        return cache
            .map {
                it.values.filter { it.resources().contains(id) }
            }
    }

    override fun get(id: String): Observable<Optional<Tag>> {
        return cache.map { cache -> cache.values.firstOrNull { it.id == id }.optional() }
    }

    override fun save(tag: Tag): Observable<Tag> {
        val value = cache.value
        value[tag.id] = tag
        cache.onNext(value)
        return Observable.just(tag)
    }

    override fun delete(tag: Tag): Observable<Tag> {
        TODO("Not yet implemented")
    }
}
