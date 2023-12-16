package com.dtks.quickmuseum.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dtks.quickmuseum.R

@Composable
fun ArtItem(artObject: ArtObjectListItem, onClick: (ArtObjectListItem) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.list_item_padding),
                vertical = dimensionResource(id = R.dimen.list_item_padding),
            )
            .background(color = Color.Green)
            .clickable { onClick(artObject) }
    ) {
        AsyncImage(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(max = 200.dp)
                .height(dimensionResource(id = R.dimen.list_item_height)),
            model = artObject.imageUrl,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = artObject.title,
            contentScale = ContentScale.FillHeight
        )
        Text(
            text = artObject.title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.horizontal_margin)
            )
        )
    }
}