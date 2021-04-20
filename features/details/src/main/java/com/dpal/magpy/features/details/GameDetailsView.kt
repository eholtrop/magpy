package com.dpal.magpy.features.details

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.dpal.domain.details.GameDomain
import com.dpal.domain.details.TagDomain
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.Random

class GameDetailsView(
    private val viewModel: GameDetailsViewModel
) {

    private val rnd = Random()

    @Composable
    operator fun invoke() {
        val state by viewModel.details()
            .subscribeAsState(initial = ViewState.Loading)

        Crossfade(targetState = state is ViewState.Loading) {
            when (val s = state) {
                is ViewState.Loaded -> loaded(gameDomain = s.data)
                ViewState.Loading ->
                    Box(
                        Modifier.background(Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            Modifier.align(Alignment.Center)
                        ) {
                            CircularProgressIndicator()
                            Text(
                                text = "Loading"
                            )
                        }
                    }
            }
        }
    }

    @Composable
    fun loaded(gameDomain: GameDomain) {
        Scaffold(
            topBar = { toolbar(state = gameDomain) },
            content = { content(state = gameDomain) },
            floatingActionButton = { fab(gameDomain) }
        )
    }

    @Composable
    fun toolbar(state: GameDomain) {

        Box(
            Modifier.clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
        ) {
            val boxArt by remember { mutableStateOf(state.boxArt) }
            CoilImage(
                data = boxArt,
                contentDescription = "",
                fadeIn = true
            )
            Column(
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.shadow)
                    )
                    .padding(horizontal = 16.dp)
                    .padding(
                        top = 16.dp,
                        bottom = 22.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            ) {
                Text(
                    text = state.title,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = state.subtitle,
                    modifier = Modifier.padding(top = 4.dp),
                    color = Color.White,
                )
            }
        }
    }

    @Composable
    fun content(state: GameDomain) {

        Box(
            Modifier.fillMaxHeight()
        ) {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text("Tags", style = TextStyle(fontWeight = FontWeight.Bold))
                Row(
                    Modifier
                        .padding(top = 4.dp)
                        .animateContentSize()
                ) {
                    state.gameTags.forEach { chip(tag = it) }
                }
                Text(
                    text = "Description",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 12.dp)
                )
                Divider(
                    Modifier.padding(vertical = 2.dp),
                    color = colorResource(id = R.color.black)
                )
                Text(state.description)
            }
        }
    }

    @Composable
    fun fab(state: GameDomain) {

        var showCreateTagDialog by remember { mutableStateOf(false) }

        Crossfade(
            targetState = showCreateTagDialog,
            animationSpec = tween()
        ) {
            when (it) {
                true -> {
                    val tags by remember { mutableStateOf(state.allTags) }

                    LazyColumn(
                        Modifier
                            .width(176.dp)
                            .background(
                                MaterialTheme.colors.primary,
                                RoundedCornerShape(size = 16.dp)
                            )
                    ) {
                        tags.forEach() { tag ->
                            item {
                                ClickableText(
                                    text = AnnotatedString(tag.title),
                                    onClick = {
                                        viewModel.addTag(tag.id).take(1).subscribe()
                                        showCreateTagDialog = false
                                    },
                                    style = TextStyle(
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier
                                        .defaultMinSize(minHeight = 44.dp)
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                )
                            }
                        }
                    }
                }
                false -> {
                    FloatingActionButton(
                        onClick = {
                            showCreateTagDialog = true
                        },
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_tag_add),
                            contentDescription = "Add Tag"
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun chip(tag: TagDomain) {
        val backgroundColor = Color(
            alpha = 255,
            green = rnd.nextInt(256),
            blue = rnd.nextInt(256),
            red = rnd.nextInt(256)
        )
        //  TODO: luminence isnt working properly. need to fix for accesibility
        //  could also add a outline to the text in the tags
        val isDarkColor = ColorUtils.calculateLuminance(backgroundColor.value.toInt()) < 0.5
        ClickableText(
            text = AnnotatedString(tag.title),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = if (isDarkColor) Color.White else Color.Black
            ),
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(
                        size = 8.dp
                    )
                )
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .defaultMinSize(44.dp),
            onClick = {
                viewModel.removeTag(tag.id).take(1).subscribe()
            }
        )
    }
}
