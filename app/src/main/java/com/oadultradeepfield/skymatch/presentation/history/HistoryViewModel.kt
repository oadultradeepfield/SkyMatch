package com.oadultradeepfield.skymatch.presentation.history

import androidx.lifecycle.viewModelScope
import com.oadultradeepfield.skymatch.domain.usecase.history.DeleteHistoryUseCase
import com.oadultradeepfield.skymatch.domain.usecase.history.ObserveHistoryUseCase
import com.oadultradeepfield.skymatch.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

/** ViewModel for handling history intents and events. */
@HiltViewModel
class HistoryViewModel
@Inject
constructor(
    private val observeHistoryUseCase: ObserveHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
) : BaseViewModel<HistoryIntent, HistoryState, HistoryEvent>(HistoryState()) {

  init {
    observeHistories()
  }

  private fun observeHistories() {
    viewModelScope.launch {
      setState { copy(isLoading = true, error = null) }
      try {
        observeHistoryUseCase().collect { histories ->
          setState { copy(histories = histories, isLoading = false) }
        }
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        handleError(e)
      }
    }
  }

  override suspend fun handleIntent(intent: HistoryIntent) {
    when (intent) {
      is HistoryIntent.LoadHistories -> observeHistories()
      is HistoryIntent.DeleteHistory -> handleDeleteHistory(intent.historyId)
    }
  }

  override fun handleError(error: Exception) {
    val errorMessage = error.message ?: "An error occurred while loading histories."
    setState { copy(error = errorMessage, isLoading = false) }
    viewModelScope.launch { sendEvent(HistoryEvent.ShowError(errorMessage)) }
  }

  private suspend fun handleDeleteHistory(historyId: String) {
    try {
      deleteHistoryUseCase(historyId)
    } catch (e: CancellationException) {
      throw e
    } catch (e: Exception) {
      handleError(e)
    }
  }
}
