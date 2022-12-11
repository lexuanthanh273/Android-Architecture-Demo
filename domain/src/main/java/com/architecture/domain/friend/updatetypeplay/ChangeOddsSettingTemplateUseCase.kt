package com.architecture.domain.friend.updatetypeplay

import com.architecture.domain.UiResource
import com.architecture.domain.friend.Friend
import com.architecture.domain.friend.FriendRepository
import javax.inject.Inject

class ChangeOddsSettingTemplateUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(friendId: String, changeOddsSettingTemplateFriendParam: ChangeOddsSettingTemplateFriendParam): UiResource<Friend> {
        return friendRepository.updateTypePlayTemplate(friendId,changeOddsSettingTemplateFriendParam)
    }
}