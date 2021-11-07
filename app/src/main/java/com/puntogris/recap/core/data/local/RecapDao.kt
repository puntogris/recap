package com.puntogris.recap.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.puntogris.recap.feature_recap.data.data_source.local.RecapEntity

@Dao
interface RecapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recap: RecapEntity): Long

    @Query("DELETE FROM RecapEntity WHERE id == :recapId")
    suspend fun delete(recapId: String): Int

    @Query("SELECT * FROM RecapEntity")
    fun getDraftsPaged(): PagingSource<Int, RecapEntity>
}