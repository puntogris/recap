package com.puntogris.recap.data.local

import androidx.room.Dao
import androidx.room.Insert
import com.puntogris.recap.model.Draft

@Dao
interface DraftDao {

    @Insert
    suspend fun insert(draft: Draft)
}