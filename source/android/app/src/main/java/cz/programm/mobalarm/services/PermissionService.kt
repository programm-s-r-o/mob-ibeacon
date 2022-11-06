package cz.programm.mobalarm.services

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionService(val context: Context) {
    val permissions = arrayOf(BLUETOOTH_ADMIN, BLUETOOTH, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, VIBRATE, BLUETOOTH_SCAN, BLUETOOTH_ADVERTISE)


    fun checkPermissions(activity: Activity) {
        if (permissions.any {
                ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            }) {
            ActivityCompat.requestPermissions(activity, permissions, 3689)
        }
    }
}