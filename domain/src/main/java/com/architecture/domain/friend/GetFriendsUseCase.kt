package com.architecture.domain.friend

import com.architecture.domain.UiResource
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(): UiResource<MutableList<Friend>> {
        return friendRepository.getFriends()
    }

    suspend fun getFriendsNonAsync(): UiResource<MutableList<Friend>> {
        return friendRepository.getFriendsNonAsync()
    }
}