package com.oadultradeepfield.skymatch.domain.repository

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation

/** Repository interface for searching constellations. */
interface ISearchRepository {
  /**
   * Searches for constellations matching the given query.
   *
   * @param query The search query to filter constellations by Latin or English name.
   * @return A list of constellations matching the query (case-insensitive).
   */
  suspend fun searchConstellations(query: String): List<Constellation>
}
