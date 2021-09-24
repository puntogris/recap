package com.puntogris.recap.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.puntogris.recap.model.Recap

@Dao
interface RecapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recap: Recap): Long
}