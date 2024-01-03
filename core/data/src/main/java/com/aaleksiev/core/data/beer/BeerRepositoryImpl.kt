package com.aaleksiev.core.data.beer

import com.aaleksiev.core.model.Beer
import com.aaleksiev.punkapi.PunkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class BeerRepositoryImpl @Inject constructor(
  private val punkApi: PunkApi
) : BeerRepository {
  override fun beers(): Flow<List<Beer>> = flow {
    emit(punkApi.beers())
  }

  override fun beerDetails(id: Long): Flow<Beer> = flow {
    emit(punkApi.beerDetails(id))
  }
}