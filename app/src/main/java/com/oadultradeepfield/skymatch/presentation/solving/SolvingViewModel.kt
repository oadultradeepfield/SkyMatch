package com.oadultradeepfield.skymatch.presentation.solving

import android.app.Application
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.oadultradeepfield.skymatch.domain.usecase.history.AddResultsToHistoryUseCase
import com.oadultradeepfield.skymatch.domain.usecase.history.CreateHistoryUseCase
import com.oadultradeepfield.skymatch.domain.usecase.history.DeleteHistoryUseCase
import com.oadultradeepfield.skymatch.domain.usecase.history.RemoveResultsFromHistoryUseCase
import com.oadultradeepfield.skymatch.domain.usecase.solve.CancelSolvingUseCase
import com.oadultradeepfield.skymatch.domain.usecase.solve.ObserveSolvingUseCase
import com.oadultradeepfield.skymatch.domain.usecase.solve.SolveImagesUseCase
import com.oadultradeepfield.skymatch.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/** ViewModel for handling solving screen intents and events. */
@HiltViewModel
class SolvingViewModel
@Inject
constructor(
    private val application: Application,
    private val createHistoryUseCase: CreateHistoryUseCase,
    private val solveImagesUseCase: SolveImagesUseCase,
    private val observeSolvingUseCase: ObserveSolvingUseCase,
    private val cancelSolvingUseCase: CancelSolvingUseCase,
    private val addResultsToHistoryUseCase: AddResultsToHistoryUseCase,
    private val removeResultsFromHistoryUseCase: RemoveResultsFromHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
) : BaseViewModel<SolvingIntent, SolvingState, SolvingEvent>(SolvingState()) {

  private var observeJob: Job? = null

  override suspend fun handleIntent(intent: SolvingIntent) {
    when (intent) {
      is SolvingIntent.Initialize -> handleInitialize(intent.imageUris)
      is SolvingIntent.CancelSolving -> handleCancelSolving(intent.index)
      is SolvingIntent.CancelAll -> handleCancelAll()
      is SolvingIntent.DeleteResult -> handleDeleteResult(intent.index)
    }
  }

  override fun handleError(error: Exception) {
    val errorMessage = error.message ?: "An error occurred during solving."
    setState { copy(error = errorMessage, isLoading = false) }
    viewModelScope.launch { sendEvent(SolvingEvent.ShowError(errorMessage)) }
  }

  private suspend fun handleInitialize(imageUris: List<String>) {
    setState { copy(isLoading = true, error = null) }

    try {
      val historyId = createHistoryUseCase()
      setState { copy(historyId = historyId) }

      val images =
          imageUris.mapNotNull { uri -> readImageBytes(uri)?.let { bytes -> bytes to uri } }
      if (images.size != imageUris.size) {
        handleError(Exception("Failed to read some images"))
        return
      }

      val jobIds = solveImagesUseCase(images).mapNotNull { it.getOrNull() }
      if (jobIds.size != imageUris.size) {
        handleError(Exception("Failed to submit some images"))
        return
      }

      addResultsToHistoryUseCase(historyId, jobIds)
      observeSolvingProgress(jobIds)
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      handleError(e)
    }
  }

  private fun readImageBytes(uriString: String): ByteArray? {
    return try {
      val uri = uriString.toUri()
      application.contentResolver.openInputStream(uri)?.use { it.readBytes() }
    } catch (_: Exception) {
      null
    }
  }

  private fun observeSolvingProgress(jobIds: List<String>) {
    observeJob?.cancel()
    observeJob =
        viewModelScope.launch {
          try {
            observeSolvingUseCase(jobIds).collect { results ->
              setState { copy(items = results.filterNotNull(), isLoading = false) }
            }
          } catch (e: CancellationException) {
            throw e
          } catch (e: Exception) {
            handleError(e)
          }
        }
  }

  private suspend fun handleCancelSolving(index: Int) {
    val item = state.value.items[index]
    if (!item.status.isCancellable()) return
    cancelSolvingUseCase(listOf(item.id))
  }

  private suspend fun handleCancelAll() {
    val cancellableJobIds =
        state.value.items.filter { it.status.isCancellable() }.map { it.id }
    if (cancellableJobIds.isEmpty()) return
    cancelSolvingUseCase(cancellableJobIds)
  }

  private suspend fun handleDeleteResult(index: Int) {
    val currentItems = state.value.items
    val item = currentItems[index]
    val historyId = state.value.historyId!!

    if (item.status.isCancellable()) return

    removeResultsFromHistoryUseCase(historyId, listOf(item.id))

    val updatedItems = currentItems.toMutableList().apply { removeAt(index) }

    if (updatedItems.isEmpty()) {
      deleteHistoryUseCase(historyId)
      sendEvent(SolvingEvent.NavigateBack)
    } else {
      val newIndex = index.coerceAtMost(updatedItems.size - 1)
      setState { copy(items = updatedItems, currentIndex = newIndex) }
    }
  }

  override fun onCleared() {
    super.onCleared()
    observeJob?.cancel()
  }
}
