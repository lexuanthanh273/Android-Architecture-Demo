package com.architecture.repository

interface DataToEntityMapper<D, E> {
    fun toEntity(data: D): E
}