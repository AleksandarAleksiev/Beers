package com.aaleksiev.punkapi.model.request

import io.ktor.resources.Resource

@Resource("/v2/beers/")
data class BeerDetailsRequest(val id: Long)
