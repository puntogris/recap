package com.puntogris.recap.feature_profile.domain.model

import com.google.firebase.Timestamp

class PrivateProfile(
    val uid: String = "",
    val name: String = "",
    val bio: String = "",
    val username: String = "",
    val registered: Timestamp = Timestamp.now(),
    val email: String = "",
    val valid: Boolean = true
)