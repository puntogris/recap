package com.puntogris.recap.data.repo.user

import com.puntogris.recap.utils.SimpleResult

interface IUserRepository {
    fun isUserLoggedIn(): Boolean
}