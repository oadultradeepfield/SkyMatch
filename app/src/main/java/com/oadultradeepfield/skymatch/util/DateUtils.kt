package com.oadultradeepfield.skymatch.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Formats an [Instant] to a relative date string.
 *
 * @param instant The instant to format.
 * @return "Today" if the instant is today, "Yesterday" if it was yesterday, or a formatted date
 *   like "27 Jul" otherwise.
 */
fun formatRelativeDate(instant: Instant): String {
  val zone = ZoneId.systemDefault()
  val date = instant.atZone(zone).toLocalDate()
  val today = LocalDate.now(zone)
  val yesterday = today.minusDays(1)

  return when (date) {
    today -> "Today"
    yesterday -> "Yesterday"
    else -> {
      val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)
      date.format(formatter)
    }
  }
}
