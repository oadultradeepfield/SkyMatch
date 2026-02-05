package com.oadultradeepfield.skymatch.presentation.solving

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oadultradeepfield.skymatch.presentation.ui.component.ConfirmationDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SolvingScreen(
    imageUris: List<String>,
    historyId: String?,
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SolvingViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(imageUris, historyId) {
    if (historyId != null) {
      viewModel.dispatch(SolvingIntent.Resume(historyId))
    } else if (imageUris.isNotEmpty()) {
      viewModel.dispatch(SolvingIntent.Initialize(imageUris))
    }
  }

  LaunchedEffect(Unit) {
    viewModel.events.collectLatest { event ->
      when (event) {
        is SolvingEvent.NavigateBack -> onNavigateBack()
        is SolvingEvent.NavigateToHistory -> onNavigateToHistory()
        is SolvingEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
      }
    }
  }

  SolvingScreenContent(
      state = state,
      snackbarHostState = snackbarHostState,
      onNavigateBack = onNavigateBack,
      onCancelAll = { viewModel.dispatch(SolvingIntent.CancelAll) },
      onCancelItem = { index -> viewModel.dispatch(SolvingIntent.CancelSolving(index)) },
      onDeleteItem = { index -> viewModel.dispatch(SolvingIntent.DeleteResult(index)) },
      modifier = modifier,
  )
}

@Composable
private fun SolvingScreenContent(
    state: SolvingState,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit,
    onCancelAll: () -> Unit,
    onCancelItem: (Int) -> Unit,
    onDeleteItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
  var dialogConfig by remember { mutableStateOf<SolvingDialogConfig?>(null) }
  val hasCancellableItems = state.items.any { it.status.isCancellable() }
  val cancellableCount = state.items.count { it.status.isCancellable() }

  Scaffold(
      modifier = modifier,
      snackbarHost = { SnackbarHost(snackbarHostState) },
      topBar = {
        SolvingTopBar(
            onNavigateBack = onNavigateBack,
            onCancelAllRequest = { dialogConfig = SolvingDialogConfig.CancelAll(cancellableCount) },
            showCancelAll = hasCancellableItems,
        )
      },
  ) { paddingValues ->
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        contentAlignment = Alignment.Center,
    ) {
      when {
        state.isLoading && state.items.isEmpty() -> CircularProgressIndicator()
        state.items.isEmpty() -> EmptyStateText()
        else ->
            SolvingPager(
                items = state.items,
                onCancelItemRequest = { index ->
                  dialogConfig = SolvingDialogConfig.CancelSolving(index)
                },
                onDeleteItemRequest = { index ->
                  dialogConfig = SolvingDialogConfig.DeleteResult(index)
                },
            )
      }
    }
  }

  dialogConfig?.let { config ->
    ConfirmationDialog(
        title = config.title,
        message = config.message,
        confirmText = config.confirmText,
        isDestructive = config.isDestructive,
        onDismiss = { dialogConfig = null },
        onConfirm = {
          when (config) {
            is SolvingDialogConfig.DeleteResult -> onDeleteItem(config.index)
            is SolvingDialogConfig.CancelSolving -> onCancelItem(config.index)
            is SolvingDialogConfig.CancelAll -> onCancelAll()
          }
          dialogConfig = null
        },
    )
  }
}

@Composable
private fun EmptyStateText(modifier: Modifier = Modifier) {
  Text(
      text = "No images to solve",
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = modifier,
  )
}

@Composable
private fun SolvingPager(
    items: List<com.oadultradeepfield.skymatch.domain.model.solve.SolvingResult>,
    onCancelItemRequest: (Int) -> Unit,
    onDeleteItemRequest: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
  val scope = rememberCoroutineScope()
  val pagerState = rememberPagerState(pageCount = { items.size })

  HorizontalPager(
      state = pagerState,
      modifier = modifier.fillMaxSize(),
  ) { page ->
    SolvingImagePage(
        item = items[page],
        pageIndex = page,
        totalPages = items.size,
        onPreviousPage = {
          scope.launch { if (page > 0) pagerState.animateScrollToPage(page - 1) }
        },
        onNextPage = {
          scope.launch { if (page < items.size - 1) pagerState.animateScrollToPage(page + 1) }
        },
        onCancelRequest = { onCancelItemRequest(page) },
        onDeleteRequest = { onDeleteItemRequest(page) },
    )
  }
}
