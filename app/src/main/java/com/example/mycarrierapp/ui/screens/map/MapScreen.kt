package com.example.mycarrierapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mycarrierapp.ui.theme.AppTheme
import com.example.mycarrierapp.utils.rememberMapViewWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MapScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSurface),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MapView()
    }
}

@Composable
fun MapView() {
    val mapView = rememberMapViewWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // on below line adding a map view to it.
        AndroidView({ mapView }) { mapView ->
            // on below line launching our map view
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.getMapAsync {
                    it.mapType = 1
                    // on below line adding zoom controls for map.
                    it.uiSettings.isZoomControlsEnabled = true
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SearchScreenPreviewLight() {
    AppTheme {
        MapScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreviewDark() {
    AppTheme {
        MapScreen()
    }
}