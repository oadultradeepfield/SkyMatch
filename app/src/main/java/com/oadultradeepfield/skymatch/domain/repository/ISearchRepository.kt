package com.oadultradeepfield.skymatch.domain.repository

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation

/** Repository interface for searching constellations. */
interface ISearchRepository {
  /**
   * Searches for a constellation by its name.
   *
   * @param name The name of the constellation to search for.
   * @return The constellation with the given name, or null if no constellation with that name
   *   exists.
   */
  suspend fun searchConstellation(name: String): Constellation?
}
