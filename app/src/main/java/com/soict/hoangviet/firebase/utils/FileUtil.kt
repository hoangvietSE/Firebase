package com.soict.hoangviet.firebase.utils

import android.net.Uri
import android.webkit.MimeTypeMap
import com.soict.hoangviet.firebase.application.BaseApplication
import java.io.File

object FileUtil {
    fun getFile(fileUri: Uri): File {
        return File(fileUri.path)
    }

    fun getFileExtension(uri: Uri): String {
        val contentResolver = BaseApplication.instance.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }
}