package com.puntogris.recap.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.paging.PagingConfig
import java.io.ByteArrayOutputStream

object Utils {

    fun compressImageFromUri(context: Context, uri: Uri): ByteArray {
        val baos = ByteArrayOutputStream()
        BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)).let {
            it.compress(Bitmap.CompressFormat.JPEG, 30, baos)
            it.recycle()
        }
        return baos.toByteArray()
    }


    fun defaultPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 30,
            enablePlaceholders = true,
            maxSize = 200
        )
    }
}