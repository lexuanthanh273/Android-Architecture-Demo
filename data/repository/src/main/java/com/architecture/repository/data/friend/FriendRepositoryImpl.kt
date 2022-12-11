package com.architecture.repository.data.friend

import com.architecture.remote.api.friend.FriendService
import com.architecture.repository.executor.Executor
import com.architecture.repository.util.toUiResource
import com.architecture.domain.friend.FriendRepository
import com.architecture.remote.api.friend.FriendModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendService: FriendService,
    private val friendModelMapper: FriendModelMapper,
    private val executor: Executor
) : FriendRepository {

    override suspend fun getFriends() = withContext(executor.io()) {
        friendService.getFriends().toUiResource { mutableList: MutableList<FriendModel> ->
            mutableList.map { friendModel ->
                friendModelMapper.toData(friendModel)
            }.toMutableList()
        }
    }

}

