package com.n41t.magpy.data.api

import ComicIssue
import ComicVineResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json

class ComicVineApiService(
    private val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
    }
) {

    private val baseUrl = "https://comicvine.gamespot.com/api"

    suspend fun getIssues(
        apiKey: String = "",
        sort: String? = null,
        limit: Int? = null,
        offset: Int? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
    ): ComicVineResponse<List<ComicIssue>> {
        return httpClient.get("$baseUrl/issues") {
            parameter("api_key", apiKey)
            parameter("format", "json")
            limit?.let { parameter("limit", it) }
            offset?.let { parameter("offset", it) }
            parameter("sort", "store_date:desc")
            if (startDate != null && endDate != null) {
                parameter("filter", "store_date:${startDate.format()}|${endDate.format()}")
            }
        }.body()
    }
}

fun LocalDate.format() = "$year-$month-$day"