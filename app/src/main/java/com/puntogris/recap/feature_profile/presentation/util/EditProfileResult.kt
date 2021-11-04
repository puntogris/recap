package com.puntogris.recap.feature_profile.presentation.util

sealed class EditProfileResult {
    object Success : EditProfileResult()
    sealed class Failure : EditProfileResult() {
        object NameLimit : Failure()
        object BioLimit : Failure()
        object PhotoLimit : Failure()
        object AccountIdLimit : Failure()
        object Error : Failure()
    }
}
