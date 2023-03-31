package com.example.mycarrierapp.ui

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    @ApplicationContext private val packageManager: PackageManager
) : ViewModel() {

    private var permissionCallback: ((Boolean) -> Unit)? = null

    fun requestPermission(permission: String, callback: (Boolean) -> Unit) {
        permissionCallback = callback

        if (packageManager.checkPermission(
                permission,
                PACKAGE_NAME
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permissionCallback?.invoke(true)
        } else {
            permissionCallback?.invoke(false)
        }
    }

    fun onPermissionResult(
        requestCode: Int,
        permission: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            val isGranted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            permissionCallback?.invoke(isGranted)
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        private const val PACKAGE_NAME = "com.example.mycarrierapp"
    }
}