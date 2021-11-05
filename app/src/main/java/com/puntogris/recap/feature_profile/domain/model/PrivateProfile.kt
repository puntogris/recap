package com.puntogris.recap.feature_profile.domain.model

import com.google.firebase.Timestamp

class PrivateProfile(
    val uid: String = "",
    val name: String = "",
    val bio: String = "",
    val accountId: String = "",
    val registered: Timestamp = Timestamp.now(),
    val email: String = "",
    val valid: Boolean = true,
    val accountIdEdited: Timestamp = Timestamp.now(),
    val nameEdited: Timestamp = Timestamp.now(),
    val photoEdited: Timestamp = Timestamp.now(),
    val bioEdited: Timestamp = Timestamp.now()
) {
    fun canEditName() = nameEdited < Timestamp.now()

    fun canEditPhoto() = photoEdited < Timestamp.now()

    fun canEditAccountId() = accountIdEdited < Timestamp.now()

    fun canEditBio() = bioEdited < Timestamp.now()
}