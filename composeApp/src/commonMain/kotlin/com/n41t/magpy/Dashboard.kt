package com.n41t.magpy

import ComicIssue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.n41t.magpy.domain.ComicsRepository
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun Dashboard() {

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                }
            )
        }
    ) {

        val comics by ComicsRepository().search(
            query,
            50,
            0,
            startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(DatePeriod(days = 41)),
            endDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
        ).collectAsStateWithLifecycle(emptyList())
        LazyColumn(
            contentPadding = it
        ) {
            items(comics) { item ->
                ComicRow(item)
            }
        }
    }
}

@Composable
fun ComicRow(
    comicIssue: ComicIssue
) {
    Row {
        AsyncImage(
            modifier = Modifier.height(128.dp)
                .aspectRatio(2f/3f),
            model = comicIssue.image?.thumbUrl,
            contentDescription = comicIssue.name
        )
        Column {
            Text("#${comicIssue.issueNumber} ${comicIssue.name}")
            Text(comicIssue.storeDate ?: "")
        }
    }
}

@Composable
@Preview()
fun DashboardPreview() {

}
