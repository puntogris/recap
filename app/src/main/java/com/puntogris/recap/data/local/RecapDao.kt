package com.puntogris.recap.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.puntogris.recap.model.Recap

@Dao
interface RecapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recap: Recap): Long

    @Query("DELETE FROM recap WHERE id == :recapId")
    suspend fun delete(recapId: String): Int

    @Query("SELECT * FROM recap")
    fun getDraftsPaged(): PagingSource<Int, Recap>
}