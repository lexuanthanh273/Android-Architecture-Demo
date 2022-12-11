package com.architecture.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ScreenType : Parcelable {

    abstract val name: String

    sealed class SplashFlow : ScreenType() {

        @Parcelize
        object Splash : SplashFlow() {
            override val name: String
                get() = "Splash"
        }
    }

    sealed class LoginFlow : ScreenType() {

        @Parcelize
        object Login : LoginFlow() {
            override val name: String
                get() = "Login"
        }

        @Parcelize
        object EnterOtp : LoginFlow() {
            override val name: String
                get() = "Enter otp"
        }
    }
}
