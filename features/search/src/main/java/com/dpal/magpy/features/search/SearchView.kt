package com.dpal.magpy.features.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dpal.search.R
import dev.chrisbanes.accompanist.coil.CoilImage

@Preview
@Composable
fun SearchToolbarPreview() {
    SearchToolbar(searchChanged = { })
}


@Composable
private fun SearchToolbar(
    query: String = "",
    searchChanged: ((String) -> Unit)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        var query by remember { mutableStateOf(query) }
        TextField(
            value = query,
            onValueChange = {
                query = it
                searchChanged.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = {
                Text(
                    text = "Search"
                )
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search"
                )
            }
        )
    }
}

@Composable
fun SearchView(
    viewModel: SearchViewModel,
    router: SearchRouter
) {

    Column {

        SearchToolbar(
            query = viewModel.searchSubject.value
        ) {
            viewModel.searchSubject.onNext(it)
        }

        val games by viewModel.output.games()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(games) { index, game ->
                if (index == games.size - 1) {
                    viewModel.loadMoreSubject.onNext("")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clip(shape = RoundedCornerShape(size = 8.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .clickable { router.invoke(SearchModels.Event.GameClicked(game.id)) },
                ) {
                    CoilImage(
                        data = game.boxArt,
                        contentDescription = "",
                        fadeIn = true,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(172.dp)
                            .fillMaxWidth()
                    )
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(
                                color = colorResource(id = R.color.shadow)
                            )
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp, bottom = 16.dp)
                    ) {
                        Text(
                            game.name,
                            style = TextStyle(color = Color.White)
                        )
                        Text(
                            game.releaseDate,
                            style = TextStyle(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}
