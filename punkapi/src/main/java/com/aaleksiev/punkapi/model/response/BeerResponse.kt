package com.aaleksiev.punkapi.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BeerResponse(
    val id: Long,
    val name: String,
    val tagline: String,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String,
    val ingredients: IngredientsResponse,
)

@Serializable
internal data class IngredientsResponse(
    val malt: List<IngredientResponse>,
    val hops: List<IngredientResponse>,
    val yeast: String,
)

@Serializable
internal data class IngredientResponse(
    val name: String,
    val amount: AmountResponse,
    val add: String? = null,
    val attribute: String? = null,
)

@Serializable
internal data class AmountResponse(
    val value: String,
    val unit: String,
)