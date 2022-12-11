package com.architecture.remote

object ApiConstants : BaseApiConstants() {

    object Friend {
        const val FRIENDS = "/api/friends"
    }

    object RefreshToken {
        const val REFRESH_TOKEN = "/api/auth/refresh_token"
    }
}