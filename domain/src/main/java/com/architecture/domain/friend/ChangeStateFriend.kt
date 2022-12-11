package com.architecture.domain.friend


sealed class ChangeStateFriend {
    abstract val state: String

    object UnFriend : ChangeStateFriend() {
        override val state: String
            get() = "unfriend"
    }

    object CancelUnfriend : ChangeStateFriend() {
        override val state: String
            get() = "cancel_unfriend"
    }

    object None : ChangeStateFriend() {
        override val state: String
            get() = "none"

    }

    companion object {
        fun getUnfriendStateBy(state: String) = when (state) {
            UnFriend.state -> UnFriend
            CancelUnfriend.state -> CancelUnfriend
            else -> None
        }
    }
}