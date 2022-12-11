package com.architecture.domain.friend.addfriend

import com.architecture.domain.UiResource
import com.architecture.domain.friend.Friend
import com.architecture.domain.friend.FriendRepository
import javax.inject.Inject

class CreateOrAddFriendUserCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(createOrAddFriendParam: CreateOrAddFriendParam): UiResource<Friend> {
        return friendRepository.createOrAddFriend(createOrAddFriendParam)
    }
}