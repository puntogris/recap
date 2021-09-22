package com.puntogris.recap.data.repo.draft

import com.puntogris.recap.data.local.DraftDao
import javax.inject.Inject

class DraftRepository @Inject constructor(
    private val draftDao: DraftDao
): IDraftRepository{

}