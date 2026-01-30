package com.oadultradeepfield.skymatch.presentation.upload

import android.net.Uri
import com.oadultradeepfield.skymatch.presentation.base.UiState

/**
 * Data class representing the state of the upload process.
 *
 * @param selectedImages The list of URIs of the selected images for upload.
 * @param isLoading Indicates whether the upload process is currently loading.
 * @param error The error message, if any, encountered during the upload process.
 * @param canStartSolving Indicates whether the solving process can be started.
 */
data class UploadState(
    val selectedImages: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val canStartSolving: Boolean = false,
) : UiState
