package com.aaleksiev.punkapi

import com.aaleksiev.punkapi.model.request.BeerDetailsRequest
import com.aaleksiev.punkapi.model.response.BeerResponse

interface PunkApi {
    suspend fun beers(): List<BeerResponse>
    suspend fun beerDetails(request: BeerDetailsRequest): BeerResponse
}