package com.architecture.remote.websocket.di

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.lifecycle.LifecycleRegistry
import javax.inject.Inject

class LoggedInLifecycle constructor(
    authStatusRepository: AuthStatusRepository,
    private val lifecycleRegistry: LifecycleRegistry
) : Lifecycle by lifecycleRegistry {

    @Inject constructor(authStatusRepository: AuthStatusRepository) : this(authStatusRepository, LifecycleRegistry())

    init {
        authStatusRepository.observeAuthStatus()
            .map {
                when (it) {
                    AuthStatus.LOGGED_IN -> Lifecycle.State.Started
                    AuthStatus.LOGGED_OUT -> Lifecycle.State.Stopped.WithReason()
                }
            }
            .subscribe(lifecycleRegistry)
    }
}
