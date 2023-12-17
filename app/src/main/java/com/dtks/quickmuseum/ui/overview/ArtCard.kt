package com.dtks.quickmuseum.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.dtks.quickmuseum.R
import com.dtks.quickmuseum.ui.loading.ImageLoading

@Composable
fun ArtItem(
    modifier: Modifier,
    artObject: ArtObjectListItem,
    onClick: (ArtObjectListItem) -> Unit,
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(
            horizontal = dimensionResource(id = R.dimen.list_item_padding),
            vertical = dimensionResource(id = R.dimen.list_item_padding),
        )
        .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
        .background(color = MaterialTheme.colorScheme.surface)
        .clickable { onClick(artObject) }) {
        Row {
            Text(
                text = artObject.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier
                    .widthIn(max = dimensionResource(id = R.dimen.list_item_text_width))
                    .padding(
                        dimensionResource(id = R.dimen.horizontal_margin)
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = dimensionResource(id = R.dimen.list_item_image_width))
                    .height(dimensionResource(id = R.dimen.list_item_height))
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterEnd)
                        .height(
                            dimensionResource(id = R.dimen.list_item_height)
                        ),
                    model = artObject.imageUrl,
                    loading = {
                        ImageLoading(
                            Modifier
                                .width(dimensionResource(id = R.dimen.list_item_image_width))
                                .height(dimensionResource(id = R.dimen.list_item_height)),
                        )
                    },
                    contentDescription = artObject.title,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewArtCard() {
    ArtItem(modifier = Modifier, artObject = ArtObjectListItem(
        id = "Jim",
        objectNumber = "af",
        title = "Regional manager",
        creator = "Ricky Gervais",
        imageUrl = null
    ), onClick = {})
}