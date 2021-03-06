package com.puntogris.recap.core.utils

object Constants {

    const val CROSS_FADE_DURATION = 350

    //Prefs
    const val PREF_APP_THEME = "pref_app_theme"
    const val LAST_VERSION_CODE = "last_version_code"
    const val DELETE_ACCOUNT = "pref_delete_account"

    //Dynamic Links
    const val DEEP_LINK_PATH = "https://recap.puntogris.com/recaps/"
    const val SQUARE_APP_LOGO_URL = "https://recap.puntogris.com/icons/logo_square.png"
    const val ANDROID_FALL_BACK_URL =
        "https://play.google.com/store/apps/details?id=com.puntogris.recap"
    const val DOMAIN_URI_PREFIX = "https://recap.puntogris.com/recaps"

    //Firebase
    const val USERS_COLLECTION = "users"
    const val PRIVATE_PROFILE_COLLECTION = "private_profile"
    const val RECAPS_COLLECTION = "recaps"
    const val INTERACTIONS_COLLECTION = "interactions"
    const val STATUS_FIELD = "status"
    const val CREATED_FIELD = "created"
    const val LIKED_FIELD = "liked"
    const val UID_FIELD = "uid"
    const val USERNAMES_COLLECTION = "usernames"
    const val REPORTS_COLLECTION = "reports"
    const val RATE_HTTPS_FUNCTION = "rateRecapCallable"

    //Rating
    const val RATING_APPROVE = 1
    const val RATING_REJECT = -1

    //Recap report
    const val INAPPROPRIATE = "INAPPROPRIATE"
    const val MISINFORMATION = "MISINFORMATION"



    const val TIME_EDIT_LOCKED_IN_MS = 864000000
    const val TIME_EDIT_LOCKED_IN_SEC = 864000
}