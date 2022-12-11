package com.architecture.remote.api.friend

import com.architecture.remote.*
import retrofit2.http.*

interface FriendService {
    @GET(ApiConstants.Friend.FRIENDS)
    suspend fun getFriends(): GenericResponse<GenericApiResponse<MutableList<FriendModel>>>

}