package com.architecture.core.bindingadapter.insetter

import androidx.annotation.IntDef

@IntDef(flag = true, value = [Side.NONE, Side.LEFT, Side.TOP, Side.RIGHT, Side.BOTTOM, Side.ALL])
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION
)
annotation class Sides
