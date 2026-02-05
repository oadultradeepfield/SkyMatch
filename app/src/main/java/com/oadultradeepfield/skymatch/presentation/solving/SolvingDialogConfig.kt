package com.oadultradeepfield.skymatch.presentation.solving

sealed class SolvingDialogConfig {
  abstract val title: String
  abstract val message: String
  abstract val confirmText: String
  abstract val isDestructive: Boolean

  data class DeleteResult(val index: Int) : SolvingDialogConfig() {
    override val title: String = "Delete Result"
    override val message: String =
        "Are you sure you want to delete this result? This action cannot be undone."
    override val confirmText: String = "Delete"
    override val isDestructive: Boolean = true
  }

  data class CancelSolving(val index: Int) : SolvingDialogConfig() {
    override val title: String = "Cancel Solving"
    override val message: String = "Are you sure you want to cancel this solving process?"
    override val confirmText: String = "Cancel Solving"
    override val isDestructive: Boolean = false
  }

  data class CancelAll(val count: Int) : SolvingDialogConfig() {
    override val title: String = "Cancel All"
    override val message: String = "Are you sure you want to cancel all $count solving processes?"
    override val confirmText: String = "Cancel All"
    override val isDestructive: Boolean = true
  }
}
