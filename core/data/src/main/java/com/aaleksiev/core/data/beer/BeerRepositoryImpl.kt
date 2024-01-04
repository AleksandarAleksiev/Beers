package com.aaleksiev.core.data.beer

import com.aaleksiev.core.model.Beer
import com.aaleksiev.punkapi.PunkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class BeerRepositoryImpl @Inject constructor(
  private val punkApi: PunkApi
) : BeerRepository {

  /**
   * Repository functions return flow because if persistent caching is required
   * in the future, where cached data is emitted first
   * and after that an API request is made and fresh data is emitted
   * no change will be required in the consumer of this repository
   */
  override fun beers(): Flow<Result<List<Beer>>> = flow {
    emit(
      runCatching { punkApi.beers() }
    )
  }

  override fun beerDetails(id: Long): Flow<Result<Beer>> = flow {
    emit(
      runCatching { punkApi.beerDetails(id) }
    )
  }
}