package com.puntogris.recap.data.local

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import com.puntogris.recap.model.Recap
import kotlinx.coroutines.flow.Flow

@Dao
interface RecapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recap: Recap): Long

    @Query("DELETE FROM recap WHERE id == :recapId")
    suspend fun delete(recapId: String): Int

    @Query("SELECT * FROM recap")
    fun getDraftsPaged(): PagingSource<Int, Recap>
}