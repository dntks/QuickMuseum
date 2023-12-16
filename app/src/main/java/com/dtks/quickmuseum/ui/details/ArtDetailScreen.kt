package com.dtks.quickmuseum.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dtks.quickmuseum.R

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
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            artDetails?.let {
                Row {
                    AsyncImage(
                        model = artDetails.imageUrl,
                        contentDescription = artDetails.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        placeholder = painterResource(R.drawable.ic_launcher_background),
                        contentScale = ContentScale.FillWidth

                    )
                }
                artDetails.dating?.let {
                    Row {
                        Text(
                            text =it,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(
                                start = dimensionResource(id = R.dimen.horizontal_margin)
                            )
                        )
                    }
                }
                artDetails.creators.let {
                    Row {
                        Text(
                            text =it.joinToString(),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(
                                start = dimensionResource(id = R.dimen.horizontal_margin)
                            )
                        )
                    }
                }
                artDetails.materials?.let {
                    Row {
                        Text(
                            text =it.joinToString(),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(
                                start = dimensionResource(id = R.dimen.horizontal_margin)
                            )
                        )
                    }
                }
            } ?: Column {
                Text(
                    text = stringResource(id = R.string.art_details_not_found),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.horizontal_margin)
                    )
                )
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