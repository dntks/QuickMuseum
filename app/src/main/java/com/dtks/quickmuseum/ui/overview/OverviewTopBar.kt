package com.dtks.quickmuseum.ui.overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dtks.quickmuseum.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
    title = {

        Text(text = stringResource(id = R.string.app_name))    },
    navigationIcon = {
        IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.menu_back))
        }
    },
    actions = {
    },
    modifier = Modifier.fillMaxWidth()
    )
}