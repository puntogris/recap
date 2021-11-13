package com.puntogris.recap.feature_profile.domain.model

import com.google.firebase.Timestamp

class UpdateProfileData(
    var photoUrl: String = "",
    var name: String = "",
    var bio: String = "",
    var username: String = "",
    var lastEdited: Timestamp = Timestamp.now()
)
