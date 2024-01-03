package com.aaleksiev.punkapi

import com.aaleksiev.core.model.Amount
import com.aaleksiev.core.model.Beer
import com.aaleksiev.core.model.Ingredient
import com.aaleksiev.core.model.Ingredients
import com.aaleksiev.punkapi.model.request.BeerDetailsRequest
import com.aaleksiev.punkapi.model.request.BeersRequest
import com.aaleksiev.punkapi.model.response.BeerResponse
import com.aaleksiev.punkapi.model.response.IngredientResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

internal class PunkApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : PunkApi {
    override suspend fun beers(): List<Beer> =
        httpClient
            .get(BeersRequest)
            .body<List<BeerResponse>>()
            .map { it.asBeer() }


    override suspend fun beerDetails(id: Long): Beer =
        httpClient
            .get(BeerDetailsRequest(id))
            .body<List<BeerResponse>>()
            .first()
            .asBeer()

    private fun BeerResponse.asBeer() = Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        imageUrl = imageUrl,
        ingredients = Ingredients(
            malt = ingredients.malt.map { it.asIngredient() },
            hops = ingredients.hops.map { it.asIngredient() },
            yeast = ingredients.yeast,
        )
    )

    private fun IngredientResponse.asIngredient() = Ingredient(
        name = name,
        add = add,
        attribute = attribute,
        amount = Amount(
            value = amount.value,
            unit = amount.unit,
        ),
    )
}
