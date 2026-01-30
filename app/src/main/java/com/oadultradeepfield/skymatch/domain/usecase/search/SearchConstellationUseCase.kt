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
   * Searches for a constellation by its name.
   *
   * @param name The name of the constellation to search for.
   * @return The constellation with the given name, or null if no constellation with that name
   *   exists.
   */
  suspend operator fun invoke(name: String): Constellation? = repo.searchConstellation(name)
}
