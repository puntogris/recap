package com.puntogris.recap.feature_auth.data.data_source.remote

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.feature_profile.domain.model.PrivateProfile
import com.puntogris.recap.feature_profile.domain.model.PublicProfile

fun FirebaseUser.toNewPrivateProfile(): PrivateProfile {
    return PrivateProfile(
        uid = uid,
        email = requireNotNull(email)
    )
}

fun FirebaseUser.toNewPublicProfile(username: String): PublicProfile {
    return PublicProfile(
        uid = uid,
        username = username,
        name = requireNotNull(displayName),
        photoUrl = photoUrl.toString(),
        lastEdited = Timestamp(0,0)
    )
}