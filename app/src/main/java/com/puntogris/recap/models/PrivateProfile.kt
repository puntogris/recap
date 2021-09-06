package com.puntogris.recap.models

import com.google.firebase.Timestamp

class PrivateProfile(
    val uid: String = "",
    val name: String = "",
    val bio: String = "",
    val registered: Timestamp = Timestamp.now(),
    val likesCounter: Int = 0,
    val recapsCounter: Int = 0,
    val email: String = "",
    val photoUrl: String = "",
    val valid: Boolean = true,
)