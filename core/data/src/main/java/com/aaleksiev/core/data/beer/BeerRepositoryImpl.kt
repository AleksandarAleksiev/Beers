package com.aaleksiev.core.data.beer

import com.aaleksiev.core.model.Beer
import com.aaleksiev.punkapi.PunkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class BeerRepositoryImpl @Inject constructor(
  private val punkApi: PunkApi
) : BeerRepository {
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