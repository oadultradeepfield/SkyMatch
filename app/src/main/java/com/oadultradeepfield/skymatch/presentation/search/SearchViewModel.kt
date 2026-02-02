package com.oadultradeepfield.skymatch.presentation.search

import androidx.lifecycle.viewModelScope
import com.oadultradeepfield.skymatch.domain.usecase.search.SearchConstellationUseCase
import com.oadultradeepfield.skymatch.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/** ViewModel for handling search intents and events. */
@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val searchConstellationUseCase: SearchConstellationUseCase,
) : BaseViewModel<SearchIntent, SearchState, SearchEvent>(SearchState()) {

  private var searchJob: Job? = null
  private val debounceDelayMs = 300L

  init {
    dispatch(SearchIntent.LoadConstellations)
  }

  override suspend fun handleIntent(intent: SearchIntent) {
    when (intent) {
      is SearchIntent.UpdateQuery -> handleQueryChanged(intent.query)
      is SearchIntent.ClearQuery -> handleClearQuery()
      is SearchIntent.LoadConstellations -> handleLoadConstellations()
    }
  }

  override fun handleError(error: Exception) {
    val errorMessage = error.message ?: "An error occurred while searching."
    setState { copy(error = errorMessage, isLoading = false) }
    viewModelScope.launch { sendEvent(SearchEvent.ShowError(errorMessage)) }
  }

  private fun handleQueryChanged(query: String) {
    setState { copy(query = query) }

    searchJob?.cancel()
    searchJob =
        viewModelScope.launch {
          delay(debounceDelayMs)
          performSearch(query)
        }
  }

  private suspend fun handleClearQuery() {
    setState { copy(query = "") }
    performSearch("")
  }

  private suspend fun handleLoadConstellations() {
    performSearch("")
  }

  private suspend fun performSearch(query: String) {
    setState { copy(isLoading = true, error = null) }

    try {
      val results = searchConstellationUseCase(query)
      setState { copy(constellations = results, isLoading = false) }
    } catch (e: CancellationException) {
      // Ignore cancellation exceptions
      throw e
    } catch (e: Exception) {
      handleError(e)
    }
  }
}
