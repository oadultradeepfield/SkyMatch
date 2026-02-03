package com.oadultradeepfield.skymatch.domain.model.solve

/**
 * Represents the status of a solving process.
 *
 * @param displayName The human-readable name of the solving status.
 */
enum class SolvingStatus(val displayName: String) {
  QUEUED("Queued"),
  IDENTIFYING_OBJECTS("Identifying Objects..."),
  GETTING_MORE_DETAILS("Getting More Details..."),
  FINDING_INTERESTING_INFO("Finding Interesting Info..."),
  SUCCESS("Success"),
  FAILURE("Failed"),
  CANCELLED("Cancelled");

  /** Returns true if the solving process can be cancelled in this status. */
  fun isCancellable(): Boolean =
      when (this) {
        QUEUED,
        IDENTIFYING_OBJECTS,
        GETTING_MORE_DETAILS,
        FINDING_INTERESTING_INFO -> true
        SUCCESS,
        FAILURE,
        CANCELLED -> false
      }
}
