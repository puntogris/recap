package com.puntogris.recap.models

data class Recap(
    var id : String = "",
    var title: String = "",
    var rating: Int = 0,
    var season: Int = 0,
    var type: String = "",
    var author: String = "",
    var html: String = "",
    var approved: Boolean = false,
    var category: String = "",
    var image: String = ""
)