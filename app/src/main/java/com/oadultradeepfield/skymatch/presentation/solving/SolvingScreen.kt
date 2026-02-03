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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SolvingScreen(
    imageUris: List<String>,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SolvingViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(imageUris) { viewModel.dispatch(SolvingIntent.Initialize(imageUris)) }

  LaunchedEffect(Unit) {
    viewModel.events.collectLatest { event ->
      when (event) {
        is SolvingEvent.NavigateBack -> onNavigateBack()
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
  val hasCancellableItems = state.items.any { it.solvingStatus.isCancellable() }

  Scaffold(
      modifier = modifier,
      snackbarHost = { SnackbarHost(snackbarHostState) },
      topBar = {
        SolvingTopBar(
            onNavigateBack = onNavigateBack,
            onCancelAll = onCancelAll,
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
                onCancelItem = onCancelItem,
                onDeleteItem = onDeleteItem,
            )
      }
    }
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
    onCancelItem: (Int) -> Unit,
    onDeleteItem: (Int) -> Unit,
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
        onCancel = { onCancelItem(page) },
        onDelete = { onDeleteItem(page) },
    )
  }
}
