package com.architecture.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.architecture.local.data.contact.LocalContactEntity

@Database(
    entities = [
        LocalContactEntity::class,
     ],
    version = 1,
    exportSchema = false
)
abstract class SLAppDatabase: RoomDatabase() {

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, SLAppDatabase::class.java, "SLDB.db")
                .allowMainThreadQueries()
                .build()
    }

}