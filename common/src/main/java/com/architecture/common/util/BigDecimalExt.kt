package com.architecture.common.util

import android.icu.math.BigDecimal

fun BigDecimal.isZero() = this.signum() == 0

fun BigDecimal.isNotZero() = this.signum() != 0

fun BigDecimal.isPositive() = this.signum() == 1

fun BigDecimal.isPositiveOrZero() = this.signum() == 0 || this.signum() == 1

fun BigDecimal.isNegative() = this.signum() == -1