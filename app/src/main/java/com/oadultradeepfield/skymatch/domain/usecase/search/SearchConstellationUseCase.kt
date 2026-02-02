package com.oadultradeepfield.skymatch.domain.usecase.search

import com.oadultradeepfield.skymatch.domain.model.constellation.Constellation
import com.oadultradeepfield.skymatch.domain.repository.ISearchRepository
import javax.inject.Inject

/**
 * Use case for searching constellations by name.
 *
 * @param repo The repository for searching constellations.
 */
class SearchConstellationUseCase
@Inject
constructor(
    private val repo: ISearchRepository,
) {
  /**
   * Searches for constellations matching the given query.
   *
   * @param query The search query to filter constellations by Latin or English name. If empty,
   *   returns all constellations.
   * @return A list of constellations matching the query (case-insensitive).
   */
  suspend operator fun invoke(query: String): List<Constellation> = repo.searchConstellations(query)
}
