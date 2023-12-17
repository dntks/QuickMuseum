package com.dtks.quickmuseum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.dtks.quickmuseum.ui.QuickMuseumNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuickMuseumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                QuickMuseumNavGraph()
            }
        }
    }
}