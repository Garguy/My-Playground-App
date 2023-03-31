package com.example.mycarrierapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mycarrierapp.ui.theme.AppTheme
import com.example.mycarrierapp.utils.rememberMapViewWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MapScreen(
    permissionViewModel: PermissionViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSurface),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MapView(permissionViewModel = permissionViewModel)
    }
}

@Composable
fun MapView(
    permissionViewModel: PermissionViewModel
) {
    val mapView = rememberMapViewWithLifecycle()

    val context = LocalContext.current

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
                val map = mapView.getMapAsync { googleMap ->
                    googleMap.mapType = 1
                    // on below line adding zoom controls for map.
                    googleMap.uiSettings.isZoomControlsEnabled = true

                    // Check for location permission and enable location on the map
                    if (ContextCompat.checkSelfPermission(
                            mapView.context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        googleMap.isMyLocationEnabled = true
                    } else {
                        permissionViewModel.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION) { granted ->
                            if (granted) {
                                googleMap.isMyLocationEnabled = true
                            } else {
                                // Permission denied, handle the error
                                Toast.makeText(context, "We need permissions", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
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