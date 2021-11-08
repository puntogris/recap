package com.puntogris.recap.feature_recap.data.data_source.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.puntogris.recap.feature_recap.domain.model.RecapStatus

@Keep
@Entity
data class RecapEntity(

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
    var status: String = RecapStatus.PENDING,

    @ColumnInfo
    var category: String = "",

    @ColumnInfo
    var image: String = "",

    @ColumnInfo
    var deepLink: String = "",

    @ColumnInfo
    var created: Timestamp = Timestamp.now(),

    @Ignore
    val reviewers: List<String> = emptyList()
)
