package com.puntogris.recap.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
class Draft(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    var html: String = "",

    @ColumnInfo
    val date: Date = Date(),

    @ColumnInfo
    val author: String = "",

    @ColumnInfo
    val title: String = ""
):Parcelable