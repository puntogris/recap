package com.puntogris.recap.feature_auth.data.data_source.remote

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.feature_profile.domain.model.PrivateProfile
import com.puntogris.recap.feature_profile.domain.model.PublicProfile

fun FirebaseUser.toPrivate(username: String): PrivateProfile {
    return PrivateProfile(
        uid = uid,
        username = username,
        name = requireNotNull(displayName),
        email = requireNotNull(email)
    )
}

fun FirebaseUser.toPublic(username: String): PublicProfile {
    return PublicProfile(
        uid = uid,
        username = username,
        name = requireNotNull(displayName),
        photoUrl = photoUrl.toString()
    )
}