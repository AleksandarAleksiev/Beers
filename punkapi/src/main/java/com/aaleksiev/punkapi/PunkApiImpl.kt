package com.aaleksiev.punkapi

import com.aaleksiev.punkapi.model.request.BeerDetailsRequest
import com.aaleksiev.punkapi.model.request.BeersRequest
import com.aaleksiev.punkapi.model.response.BeerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

internal class PunkApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : PunkApi {
    override suspend fun beers(): List<BeerResponse> =
        httpClient.get(BeersRequest).body()

    override suspend fun beerDetails(request: BeerDetailsRequest): BeerResponse =
        httpClient.get(request).body()
}
