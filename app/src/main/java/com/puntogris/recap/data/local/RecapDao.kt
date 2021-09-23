package com.puntogris.recap.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.puntogris.recap.model.Recap

@Dao
interface RecapDao {

    @Insert
    suspend fun insert(recap: Recap)
}