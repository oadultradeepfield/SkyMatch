package com.oadultradeepfield.skymatch.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.net.URL
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Copies an image from a content URI (e.g., from photo picker) to app local storage.
 *
 * @param context The application context.
 * @param uri The content URI from the photo picker.
 * @return The file URI pointing to the saved image.
 */
suspend fun copyImageToStorage(context: Context, uri: Uri): Uri = withContext(Dispatchers.IO) {
    val bytes = context.contentResolver.openInputStream(uri)!!.use { it.readBytes() }
    val file = File(context.filesDir, "images/${UUID.randomUUID()}.jpg").apply { parentFile?.mkdirs() }
    file.writeBytes(bytes)
    Uri.fromFile(file)
}

/**
 * Downloads an image from a URL and saves it to app local storage.
 *
 * @param context The application context.
 * @param url The image URL.
 * @return The file URI pointing to the saved image.
 */
suspend fun downloadImageToStorage(context: Context, url: String): Uri = withContext(Dispatchers.IO) {
    val bytes = URL(url).openStream().use { it.readBytes() }
    val file = File(context.filesDir, "images/${UUID.randomUUID()}.jpg").apply { parentFile?.mkdirs() }
    file.writeBytes(bytes)
    Uri.fromFile(file)
}
