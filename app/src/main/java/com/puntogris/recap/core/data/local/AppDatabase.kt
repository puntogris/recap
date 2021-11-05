package com.puntogris.recap.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.utils.Converters

@Database(entities = [Recap::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recapDao(): RecapDao
}