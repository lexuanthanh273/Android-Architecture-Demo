package com.architecture.repository

interface RequestMapper<P, R> {
    fun toRequest(param: P): R
}