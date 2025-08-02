package com.n41t.magpy.domain

import ComicIssue
import com.n41t.magpy.data.api.ComicVineApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ComicsRepository(
    private val apiService: ComicVineApiService
) {

    fun search(name: String, limit: Int, offset: Int): Flow<List<ComicIssue>> {
        return flow {
            emit(
                apiService.getIssues(
                    filter = name,
                    limit = limit,
                    offset = offset
                ).results
            )
        }
    }
}