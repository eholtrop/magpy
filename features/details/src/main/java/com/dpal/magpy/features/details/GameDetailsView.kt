package com.dpal.magpy.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dpal.domain.details.GameDomain
import dev.chrisbanes.accompanist.coil.CoilImage

@Preview
@Composable
fun DetailsPreview() = DetailsView(
    state = ViewState.Loaded(
        data = GameDomain(
            title = "GTA V",
            subtitle = "Subtitle",
            boxArt = "",
            description = "This game is super cool"
        )
    )
)

@Composable
fun DetailsView(state: ViewState) {
    when (state) {
        is ViewState.Loaded -> {
            Scaffold(
                topBar = { toolbar(state = state.data) },
                content = { content(state = state.data) }
            )
        }
        ViewState.Loading ->
            Text(text = "Loading")
    }
}

@Composable
fun content(state: GameDomain) {
    Column(
        Modifier.padding(16.dp)
    ) {
        Text("Description", style = TextStyle(fontWeight = FontWeight.Bold))
        Divider(
            Modifier.padding(vertical = 2.dp),
            color = colorResource(id = R.color.black)
        )
        Text(state.description)
    }
}

@Composable
fun toolbar(state: GameDomain) {
    Box(
        Modifier
            .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
    ) {
        CoilImage(
            data = state.boxArt,
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
