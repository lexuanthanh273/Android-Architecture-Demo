package com.architecture.repository

interface ModelMapper<M, D> {
    fun toData(model: M): D
}