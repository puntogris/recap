package com.puntogris.recap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.puntogris.recap.model.Recap
import com.puntogris.recap.utils.Converters

@Database(entities = [
    Recap::class
                     ], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recapDao(): RecapDao
}