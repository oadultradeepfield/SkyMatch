package com.oadultradeepfield.skymatch.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oadultradeepfield.skymatch.di.DispatcherProvider
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel implementing MVI architecture pattern.
 *
 * @param I The type of UI intents (user actions)
 * @param S The type of UI states (screen state)
 * @param E The type of UI events (one-time events like navigation, toasts)
 * @param initialState The initial state of the ViewModel
 * @param dispatchers Injected dispatcher provider for testability
 */
abstract class BaseViewModel<I : UiIntent, S : UiState, E : UiEvent>(
    initialState: S,
    protected val dispatchers: DispatcherProvider,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _events = Channel<E>(Channel.BUFFERED)
    val events: Flow<E> = _events.receiveAsFlow()

    private val intents = Channel<I>(Channel.BUFFERED)

    init {
        viewModelScope.launch(dispatchers.default) {
            intents.receiveAsFlow().collect { intent ->
                try {
                    handleIntent(intent)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    handleError(e)
                }
            }
        }
    }

    /**
     * Dispatches a UI intent to be processed. Thread-safe and non-blocking.
     */
    fun dispatch(intent: I) {
        viewModelScope.launch { intents.send(intent) }
    }

    /**
     * Updates the UI state immutably using a reducer function.
     */
    protected fun setState(reducer: S.() -> S) {
        _state.update(reducer)
    }

    /**
     * Sends a one-time UI event (e.g., navigation, toast).
     */
    protected suspend fun sendEvent(event: E) {
        _events.send(event)
    }

    /**
     * Handles incoming UI intents. Override to implement intent processing logic.
     */
    protected abstract suspend fun handleIntent(intent: I)

    /**
     * Handles errors during intent processing. Override to implement custom error handling.
     */
    protected abstract fun handleError(error: Exception)
}
