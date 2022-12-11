package com.architecture.common.ext

infix fun <T> List<T>.equalsIgnoreOrder(other: List<T>) = this.size == other.size
        && this.toSet() == other.toSet()