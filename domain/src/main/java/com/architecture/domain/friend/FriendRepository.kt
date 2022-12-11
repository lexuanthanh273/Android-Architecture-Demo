package com.architecture.domain.friend

import com.architecture.domain.UiResource

interface FriendRepository {

    suspend fun getFriends(): UiResource<MutableList<Friend>>

}