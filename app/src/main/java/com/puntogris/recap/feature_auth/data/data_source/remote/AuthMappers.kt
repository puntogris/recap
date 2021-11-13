package com.puntogris.recap.feature_auth.data.data_source.remote

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.feature_profile.domain.model.PrivateProfile
import com.puntogris.recap.feature_profile.domain.model.PublicProfile

fun FirebaseUser.toPrivate(): PrivateProfile {
    return PrivateProfile(
        uid = uid,
        name = requireNotNull(displayName),
        email = requireNotNull(email)
    )
}

fun FirebaseUser.toPublic(): PublicProfile {
    return PublicProfile(
        uid = uid,
        name = requireNotNull(displayName),
        photoUrl = photoUrl.toString()
    )
}