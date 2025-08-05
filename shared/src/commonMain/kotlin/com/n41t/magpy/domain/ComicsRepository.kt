package com.n41t.magpy.domain

import ComicIssue
import com.n41t.magpy.data.api.ComicVineApiService
import com.n41t.magpy.util.debug
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate

class ComicsRepository(
    private val apiService: ComicVineApiService = ComicVineApiService()
) {

    fun search(
        name: String,
        limit: Int,
        offset: Int,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
    ): Flow<List<ComicIssue>> {
        return flow {
            emit(
                apiService.getIssues(
                    limit = limit,
                    offset = offset,
                    startDate = startDate,
                    endDate = endDate,
                ).results
            )
        }.debug()
    }
}