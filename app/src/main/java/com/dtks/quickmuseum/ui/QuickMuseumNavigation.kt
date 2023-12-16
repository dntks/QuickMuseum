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

package com.dtks.quickmuseum.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.dtks.quickmuseum.ui.QuickMuseumDestinationsArgs.ART_ID_ARG
import com.dtks.quickmuseum.ui.QuickMuseumDestinationsArgs.USER_MESSAGE_ARG
import com.dtks.quickmuseum.ui.QuickMuseumScreens.ARTS_SCREEN
import com.dtks.quickmuseum.ui.QuickMuseumScreens.ART_DETAILS_SCREEN

private object QuickMuseumScreens {
    const val ARTS_SCREEN = "arts"
    const val ART_DETAILS_SCREEN = "art"
}
object QuickMuseumDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val ART_ID_ARG = "artId"
    const val TITLE_ARG = "title"
}
object QuickMuseumDestinations {
    const val ARTS_ROUTE = "$ARTS_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val ART_DETAILS_ROUTE = "$ART_DETAILS_SCREEN/{$ART_ID_ARG}"
}

/**
 * Models the navigation actions in the app.
 */
class QuickMuseumNavigationActions(private val navController: NavHostController) {

    fun navigateToArtsList(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            ARTS_SCREEN.let {
                if (userMessage != 0) "$it?$USER_MESSAGE_ARG=$userMessage" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }


    fun navigateToTaskDetail(artObjectNumber: String) {
        navController.navigate("$ART_DETAILS_SCREEN/$artObjectNumber")
    }

}
