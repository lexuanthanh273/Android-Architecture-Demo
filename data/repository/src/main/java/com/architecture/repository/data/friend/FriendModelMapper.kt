package com.architecture.repository.data.friend

import com.architecture.domain.friend.*
import com.architecture.remote.api.friend.FriendModel
import com.architecture.repository.ModelMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendModelMapper @Inject constructor() : ModelMapper<FriendModel, Friend> {
    override fun toData(model: FriendModel): Friend {
        return Friend(
            id = model.id,
            userId = model.userId,
            fullname = model.fullname,
            avatar = model.avatar,
            phone = model.phone,
        )
    }
}