package com.aaleksiev.punkapi.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeerResponse(
    val id: Long,
    val name: String,
    val tagline: String,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String,
    val ingredients: Ingredients,
)

@Serializable
data class Ingredients(
    val malt: List<Ingredient>,
    val hops: List<Ingredient>,
    val yeast: String,
)

@Serializable
data class Ingredient(
    val name: String,
    val amount: Amount,
    val add: String? = null,
    val attribute: String? = null,
)

@Serializable
data class Amount(
    val value: String,
    val unit: String,
)