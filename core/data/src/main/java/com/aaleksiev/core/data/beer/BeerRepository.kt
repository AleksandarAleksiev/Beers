package com.aaleksiev.core.data.beer

import com.aaleksiev.core.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
  fun beers(): Flow<List<Beer>>
  fun beerDetails(id: Long): Flow<Beer>
}