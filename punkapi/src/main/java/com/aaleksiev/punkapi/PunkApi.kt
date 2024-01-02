package com.aaleksiev.punkapi

import com.aaleksiev.common.model.Beer

interface PunkApi {
    suspend fun beers(): List<Beer>
    suspend fun beerDetails(id: Long): Beer
}