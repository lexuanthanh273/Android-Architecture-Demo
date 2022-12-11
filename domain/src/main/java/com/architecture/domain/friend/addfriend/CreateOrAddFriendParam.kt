package com.architecture.domain.friend.addfriend


data class CreateOrAddFriendParam(

    var userId : String,

    var fullname : String,

    var avatar: String? = null,

    var phone: String? = null,

    var oddSettingId: String,
)