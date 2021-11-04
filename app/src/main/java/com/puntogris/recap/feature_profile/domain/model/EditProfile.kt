package com.puntogris.recap.feature_profile.domain.model

class EditProfile(
    val uid: String,
    var imageUri: String? = null,
    var name: String? = null,
    var bio: String? = null,
    var account: String? = null
)