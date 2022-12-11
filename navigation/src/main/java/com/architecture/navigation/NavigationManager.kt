package com.architecture.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle

object NavigationManager {
    const val KEY_SCREEN_TYPE = "key_screen"

    object Auth {
        const val AUTH_ACTIVITY_CLASS = "com.test.auth.AuthActivity"
    }

    fun navigateToAuthActivity(context: Context, screenType: ScreenType = ScreenType.LoginFlow.Login) {
        val intent = Intent(
            context,
            Class.forName(Auth.AUTH_ACTIVITY_CLASS)
        )
        val bundle = Bundle().apply {
            putParcelable(KEY_SCREEN_TYPE, screenType)
        }
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun navigateToHomeActivity(context: Context) {
        val intent = Intent(
            context,
            Class.forName("com.test.home.HomeActivity")
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

}