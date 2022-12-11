package com.architecture.domain.friend

import com.architecture.domain.oddssettingtemplate.TypePlaySetting

data class OddsSetting (
    var setterId: String,

    var oddsSettingTemplateId: String,

    var oddsSettingTemplateName: String,

    val mTime: Long,

    var typePlaySettings: MutableList<TypePlaySetting>,
)