package com.architecture.repository.di

import com.architecture.domain.file.FileRepository
import com.architecture.domain.friend.FriendRepository
import com.architecture.domain.websocket.WebsocketRepository
import com.architecture.repository.websocket.WebsocketRepositoryImpl
import com.architecture.repository.data.file.FileRepositoryImpl
import com.architecture.repository.data.friend.FriendRepositoryImpl
import com.architecture.repository.executor.DefaultExecutor
import com.architecture.repository.executor.Executor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExecutor(defaultExecutor: DefaultExecutor): Executor

    abstract fun bindFriendRepository(friendRepository: FriendRepositoryImpl): FriendRepository

    @Binds
    abstract fun bindFileRepository(fileRepository: FileRepositoryImpl): FileRepository

    @Binds
    abstract fun bindWebsocketRepository(websocketRepository: WebsocketRepositoryImpl): WebsocketRepository
}