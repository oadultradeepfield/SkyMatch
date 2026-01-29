package com.oadultradeepfield.skymatch.domain.model.solve

/**
 * Represents the status of a solving process.
 *
 * @param displayName The human-readable name of the solving status.
 */
enum class SolvingStatus(val displayName: String) {
  QUEUED("Queued"),
  IDENTIFYING_OBJECTS("Identifying Objects"),
  GETTING_MORE_DETAILS("Getting More Details"),
  FINDING_INTERESTING_INFO("Finding Interesting Info"),
  SUCCESS("Success"),
  FAILURE("Failure"),
  CANCELLED("Cancelled"),
}
