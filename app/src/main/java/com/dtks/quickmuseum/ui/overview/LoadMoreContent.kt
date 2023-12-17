package com.dtks.quickmuseum.ui.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.dtks.quickmuseum.R

@Composable
fun LoadMoreContent(
    uiState: OverviewUiState,
    viewModel: OverviewViewModel
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.generic_padding))) {

        if (!uiState.isLoading) {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.loadNextPage()
                }) {
                Text(
                    text = stringResource(
                        id =
                        if (uiState.isLoading)
                            R.string.loading
                        else R.string.load_more
                    )
                )
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(dimensionResource(id = R.dimen.progress_indicator_size)),
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}