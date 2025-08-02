package com.n41t.magpy

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.n41t.magpy.domain.ComicsRepository
import org.jetbrains.compose.ui.tooling.preview.Preview

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

        val comics by ComicsRepository(

        ).search(query, 20, 0).collectAsStateWithLifecycle()
        LazyColumn(contentPadding = it) {

        }
    }
}
@Composable
@Preview()
fun DashboardPreview() {

}
