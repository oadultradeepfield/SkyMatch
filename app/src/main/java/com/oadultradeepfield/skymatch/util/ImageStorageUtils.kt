package com.oadultradeepfield.skymatch.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Copies an image from a content URI to app local storage. */
suspend fun copyImageToStorage(context: Context, uri: Uri): Result<Uri> =
    withContext(Dispatchers.IO) {
        runCatching {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: throw IOException("Failed to open input stream for URI: $uri")

            val bytes = inputStream.use { it.readBytes() }

            val file = File(context.filesDir, "images/${UUID.randomUUID()}.jpg")
            file.parentFile?.mkdirs()
            file.writeBytes(bytes)

            Uri.fromFile(file)
        }
    }

/** Downloads an image from a URL and saves it to app local storage. */
suspend fun downloadImageToStorage(context: Context, url: String): Result<Uri> =
    withContext(Dispatchers.IO) {
        runCatching {
            val bytes = URL(url).openStream().use { it.readBytes() }

            val file = File(context.filesDir, "images/${UUID.randomUUID()}.jpg")
            file.parentFile?.mkdirs()
            file.writeBytes(bytes)

            Uri.fromFile(file)
        }
    }
