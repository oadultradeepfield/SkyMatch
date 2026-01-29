package com.oadultradeepfield.skymatch.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
 * @property initialState The initial state of the ViewModel
 */
abstract class BaseViewModel<I : UiIntent, S : UiState, E : UiEvent>(
    initialState: S
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _events = MutableSharedFlow<E>()
    val events: SharedFlow<E> = _events.asSharedFlow()

    private val intents = Channel<I>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            intents.receiveAsFlow().collect { intent ->
                try {
                    handleIntent(intent)
                } catch (e: Exception) {
                    handleError(e)
                }
            }
        }
    }

    /**
     * Dispatches a UI intent to be processed.
     * Thread-safe and non-blocking.
     *
     * @param intent The UI intent to dispatch
     */
    fun dispatch(intent: I) {
        viewModelScope.launch {
            intents.send(intent)
        }
    }

    /**
     * Updates the UI state immutably using a reducer function.
     *
     * @param reducer Function that transforms current state to new state
     */
    protected fun setState(reducer: S.() -> S) {
        _state.update(reducer)
    }

    /**
     * Emits a one-time UI event (e.g., navigation, toast).
     *
     * @param event The event to emit
     */
    protected suspend fun sendEvent(event: E) {
        _events.emit(event)
    }

    /**
     * Handles incoming UI intents.
     * Override to implement intent processing logic.
     *
     * @param intent The intent to handle
     */
    protected abstract suspend fun handleIntent(intent: I)

    /**
     * Handles errors during intent processing.
     * Override to implement custom error handling.
     *
     * @param error The exception that occurred
     */
    protected abstract fun handleError(error: Exception)
}
