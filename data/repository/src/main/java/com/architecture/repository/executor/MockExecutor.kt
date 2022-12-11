package com.architecture.repository.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MockExecutor: Executor {
    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun default(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}