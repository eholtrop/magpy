package com.dpal.data.tag

import com.dpal.libs.optional.Optional
import io.reactivex.rxjava3.core.Observable
import java.lang.NullPointerException
import java.util.*

interface TagCache {
    fun getAll(): Observable<List<Tag>>
    fun getForResource(id: String): Observable<List<Tag>>
    fun get(id: String): Observable<Optional<Tag>>
    fun save(tag: Tag): Observable<Tag>
    fun delete(tag: Tag): Observable<Tag>
}

interface TagRepository {

    companion object {
        fun instance(
            cache: TagCache
        ) = TagRepositoryImpl(cache) as TagRepository
    }

    fun getAll(): Observable<List<Tag>>
    fun getForResource(id: String): Observable<List<Tag>>

    fun saveForResource(
        tagId: String,
        resourceId: String
    ): Observable<Tag>

    fun removeForResource(
        tagId: String,
        resourceId: String
    ): Observable<Tag>
}

internal class TagRepositoryImpl(
    private val cache: TagCache
) : TagRepository {

    override fun getAll(): Observable<List<Tag>> {
        return cache.getAll()
    }

    override fun getForResource(id: String): Observable<List<Tag>> {
        return cache.getForResource(id)
    }

    override fun saveForResource(tagId: String, resourceId: String): Observable<Tag> {
        return cache.get(tagId)
            .map {
                when (it) {
                    is Optional.Error -> Tag(
                        id = UUID.randomUUID().toString(),
                        title = "Undefined",
                        resources = mutableListOf(resourceId)
                    )
                    is Optional.Value -> {
                        it.value.resources.add(resourceId)
                        it.value
                    }
                }
            }
            .switchMap { cache.save(it) }
    }

    override fun removeForResource(tagId: String, resourceId: String): Observable<Tag> {
        return cache.get(tagId)
            .map {
                when (it) {
                    is Optional.Error -> throw NullPointerException()
                    is Optional.Value -> {
                        it.value.resources.remove(resourceId)
                        it.value
                    }
                }
            }
            .switchMap { cache.save(it) }
    }

}