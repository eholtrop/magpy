package com.dpal.persistance.sqldelight

import android.content.Context
import com.dpal.data.tag.Tag
import com.dpal.data.tag.TagCache
import com.dpal.libs.optional.Optional
import com.dpal.libs.optional.optional
import com.dpal.persistance.MagpyDatabase
import com.dpal.persistance.TagEntity
import com.example.optional_rx.unwrap
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.rx3.asObservable
import com.squareup.sqldelight.runtime.rx3.mapToList
import io.reactivex.rxjava3.core.Observable

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MagpyDatabase.Schema, context, "com.magpy.db")
    }
}

val defaultMapper: (String, List<String>, String) -> Tag = { id, resources, title ->
    Tag(
        id = id,
        resources = resources.toMutableList(),
        title = title
    )
}

fun <T> Observable<List<T>>.filterEach(predicate: (T) -> Boolean): Observable<List<T>> = this.map { it.filter(predicate) as List<T> }

class SqlDelightTagCache(
    context: Context
) : TagCache {

    val db = MagpyDatabase(
        driver = DriverFactory(context).createDriver(),
        tagEntityAdapter = TagEntity.Adapter(
            resourcesAdapter = object : ColumnAdapter<List<String>, String> {
                override fun decode(databaseValue: String): List<String> =
                    if (databaseValue.isEmpty()) {
                        listOf()
                    } else {
                        databaseValue.split(",")
                    }


                override fun encode(value: List<String>): String =
                    value.joinToString(separator = ",")

            }
        )
    )

    override fun getAll(): Observable<List<Tag>> {
        return db.tagQueries.selectAll(defaultMapper)
            .asObservable()
            .mapToList()
    }

    override fun getForResource(id: String): Observable<List<Tag>> {
        return db.tagQueries.selectAll(defaultMapper)
            .asObservable()
            .mapToList()
            .filterEach { it.resources().contains(id) }
    }

    override fun get(id: String): Observable<Optional<Tag>> {
        return db.tagQueries.get(id, defaultMapper)
            .asObservable()
            .map { it.executeAsOneOrNull().optional() }
    }

    override fun save(tag: Tag): Observable<Tag> {
        db.tagQueries.insert(
            id = tag.id,
            resources = tag.resources(),
            title = tag.title
        )
        return get(tag.id).unwrap()
    }

    override fun delete(tag: Tag): Observable<Tag> {
        db.tagQueries.remove(tag.id)
        return Observable.just(tag)
    }

}