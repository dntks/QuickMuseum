/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtks.quickmuseum.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dtks.quickmuseum.ui.details.ArtDetailScreen
import com.dtks.quickmuseum.ui.navigation.QuickMuseumDestinationsArgs.USER_MESSAGE_ARG
import com.dtks.quickmuseum.ui.overview.OverviewScreen
import com.dtks.quickmuseum.ui.overview.OverviewViewModel

@Composable
fun QuickMuseumNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = QuickMuseumDestinations.ARTS_ROUTE,
    navActions: QuickMuseumNavigationActions = remember(navController) {
        QuickMuseumNavigationActions(navController)
    },
    viewModel: OverviewViewModel = hiltViewModel(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            QuickMuseumDestinations.ARTS_ROUTE,
            arguments = listOf(
                navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
            )
        ) {
            OverviewScreen(
                    onArtItemClick = { art -> navActions.navigateToArtDetail(art.objectNumber) },
                    viewModel = viewModel
                )
        }
        composable(QuickMuseumDestinations.ART_DETAILS_ROUTE) {
            ArtDetailScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}