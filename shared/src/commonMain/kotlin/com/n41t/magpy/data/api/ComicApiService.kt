package com.n41t.magpy.data.api

import ComicIssue
import ComicVineResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ComicVineApiService(private val httpClient: HttpClient) {

    private val baseUrl = "https://comicvine.gamespot.com/api"

    suspend fun getIssues(
        apiKey: String = "",
        filter: String? = null,
        sort: String? = null,
        limit: Int? = null,
        offset: Int? = null
    ): ComicVineResponse<List<ComicIssue>> {
        return httpClient.get("$baseUrl/issues") {
            parameter("api_key", apiKey)
            parameter("format", "json")
            filter?.let { parameter("filter", it) }
            sort?.let { parameter("sort", it) }
            limit?.let { parameter("limit", it) }
            offset?.let { parameter("offset", it) }
        }.body()
    }
}
