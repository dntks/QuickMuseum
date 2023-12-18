package com.dtks.quickmuseum.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.dtks.quickmuseum.R
import com.dtks.quickmuseum.ui.EmptyContent
import com.dtks.quickmuseum.ui.loading.ImageLoading
import com.dtks.quickmuseum.ui.loading.LoadingContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtDetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArtDetailViewModel = hiltViewModel(),
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val artDetails = uiState.artDetails
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize(),
        topBar = {
            ArtDetailsTopBar(
                title = artDetails?.title ?: "",
                onBack = onBack,
            )
        },
        floatingActionButton = {}
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        var size by remember { mutableStateOf(IntSize.Zero) }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
                .verticalScroll(state = scrollState)
                .onSizeChanged { size = it }
        ) {
            LoadingContent(
                empty = artDetails == null,
                loading = uiState.isLoading,
                emptyContent = {
                    EmptyContent(R.string.art_details_not_found)
                }
            ) {
                ArtDetailsComposable(artDetails, size)
            }

        }
        uiState.userMessage?.let { userMessage ->
            val snackbarText = stringResource(userMessage)
            LaunchedEffect(viewModel, userMessage, snackbarText) {
                snackbarHostState.showSnackbar(snackbarText)
                viewModel.snackbarMessageShown()
            }
        }
    }
}

@Composable
private fun ArtDetailsComposable(artDetails: ArtDetails?, size: IntSize) {
    artDetails?.let {
        Row {

            val heightToWidthRatio = artDetails.image?.let {
                if (it.height != 0 && it.width != 0) {
                    it.height.toFloat() / it.width.toFloat()
                } else {
                    null
                }
            }
            var imageModifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            heightToWidthRatio?.let {
                with(LocalDensity.current) {
                    val height = heightToWidthRatio.times(size.width).toDp()
                    imageModifier = imageModifier.height(
                        height
                    )
                }
            }
            SubcomposeAsyncImage(
                modifier = imageModifier,
                model = artDetails.image?.url,
                loading = {
                    ImageLoading(
                        Modifier
                            .width(dimensionResource(id = R.dimen.list_item_image_width))
                            .height(dimensionResource(id = R.dimen.list_item_height)),
                    )
                },
                contentDescription = artDetails.title,
                contentScale = ContentScale.FillWidth
            )
        }
        artDetails.dating?.let {
            Row {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.horizontal_margin)
                    )
                )
            }
        }
        Row {
            Text(
                text = artDetails.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.horizontal_margin)
                )
            )
        }
        artDetails.creators.let {
            Row {
                Text(
                    text = it.joinToString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.horizontal_margin)
                    )
                )
            }
        }
        artDetails.materials?.let {
            Row {
                Text(
                    text = it.joinToString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.horizontal_margin)
                    )
                )
            }
        }
    } ?: EmptyContent(R.string.art_details_not_found)
}

