package com.aaleksiev.punkapi.model.request

import io.ktor.resources.Resource

@Resource("/v2/beers/{id}")
internal data class BeerDetailsRequest(val id: Long)
