package com.puntogris.recap.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

object Utils {

    fun compressImageFromUri(context: Context, uri: Uri): ByteArray {
        val baos = ByteArrayOutputStream()
        BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)).let {
            it.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            it.recycle()
        }
        return baos.toByteArray()
    }
}