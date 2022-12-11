package com.architecture.repository

interface EntityToDataMapper<E, D> {
    fun toData(entity: E): D
}