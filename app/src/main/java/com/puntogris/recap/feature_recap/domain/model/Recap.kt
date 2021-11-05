package com.puntogris.recap.feature_recap.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Keep
@Entity
@Parcelize
data class Recap(

    @PrimaryKey
    var id: String = "",

    @ColumnInfo
    var title: String = "",

    @ColumnInfo
    var rating: Int = 0,

    @ColumnInfo
    var season: Int = 0,

    @ColumnInfo
    var episode: Int = 0,

    @ColumnInfo
    var type: String = "",

    @ColumnInfo
    var language: String = "",

    @ColumnInfo
    var author: String = "",

    @ColumnInfo
    var body: String = "",

    @ColumnInfo
    var approved: Boolean = false,

    @ColumnInfo
    var category: String = "",

    @ColumnInfo
    var image: String = "",

    @ColumnInfo
    var deepLink: String = "",

    @ColumnInfo
    var created: Timestamp = Timestamp.now()

) : Parcelable
