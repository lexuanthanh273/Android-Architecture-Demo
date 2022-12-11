package com.architecture.core.ext

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.provider.Settings
import android.widget.Toast

/**
 * Toast extensions.
 */
// Toash extensions
fun Context.showShotToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

private fun Context.showToast(text:String, duration: Int) {
    Toast.makeText(this, text, duration).show()
}

fun Context.isDebug(): Boolean {
    return 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
}

fun Context.openAppNotificationSettings() {
    val intent = Intent().apply {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
    }
    startActivity(intent)
}
