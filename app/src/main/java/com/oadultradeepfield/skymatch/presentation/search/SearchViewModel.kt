package com.oadultradeepfield.skymatch.presentation.search

import androidx.lifecycle.viewModelScope
import com.oadultradeepfield.skymatch.config.AppConfig
import com.oadultradeepfield.skymatch.di.DispatcherProvider
import com.oadultradeepfield.skymatch.domain.usecase.search.SearchConstellationUseCase
import com.oadultradeepfield.skymatch.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/** ViewModel for handling search intents and events. */
@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val searchConstellationUseCase: SearchConstellationUseCase,
    dispatchers: DispatcherProvider,
) : BaseViewModel<SearchIntent, SearchState, SearchEvent>(SearchState(), dispatchers) {
  private val queryFlow = MutableSharedFlow<String>(replay = 1)

  init {
    viewModelScope.launch {
      queryFlow
          .debounce(AppConfig.Network.DEBOUNCE_DELAY_MS)
          .distinctUntilChanged()
          .collectLatest { query -> performSearch(query) }
    }
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
    viewModelScope.launch { queryFlow.emit(query) }
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
      throw e
    } catch (e: Exception) {
      handleError(e)
    }
  }
}
