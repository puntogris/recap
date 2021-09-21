package com.puntogris.recap.model

class EditProfile(
    val uid: String,
    var imageUri: String? = null,
    var name: String? = null,
    var bio: String? = null,
    var account: String? = null
)