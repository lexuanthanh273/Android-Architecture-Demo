package com.architecture.local.data.contact

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalContact")
data class LocalContactEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "photoUri")
    var photoUri: String?,

    @ColumnInfo(name = "phone")
    var phone: String= "",
)