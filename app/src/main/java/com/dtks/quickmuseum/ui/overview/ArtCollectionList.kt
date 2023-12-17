package com.dtks.quickmuseum.ui.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.dtks.quickmuseum.R

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ArtCollectionList(
    uiState: OverviewUiState,
    onArtItemClick: (ArtObjectListItem) -> Unit,
    viewModel: OverviewViewModel
) {
    val listState = rememberLazyListState()
    val items = uiState.items
    LazyColumn(
        state = listState,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val groupedItems = items.groupBy { it.creator }
        groupedItems.forEach { (artist, items) ->
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = artist,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            dimensionResource(id = R.dimen.horizontal_margin)
                        )
                    )
                }
            }

            items(items, key = { it.id }) { artObject ->
                ArtItem(
                    modifier = Modifier.animateItemPlacement(),
                    artObject = artObject,
                    onClick = onArtItemClick,
                )
            }
        }

        if (!uiState.isEmpty) {
            item {
                LoadMoreContent(uiState, viewModel)
            }
        }
    }
}