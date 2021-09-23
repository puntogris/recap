package com.puntogris.recap.data.repo.draft

import com.puntogris.recap.data.local.RecapDao
import javax.inject.Inject

class DraftRepository @Inject constructor(
    private val draftDao: RecapDao
): IDraftRepository{

}