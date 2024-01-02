package com.aaleksiev.common.model

data class Beer(
    val id: Long,
    val name: String,
    val tagline: String,
    val description: String,
    val imageUrl: String,
    val ingredients: Ingredients,
)

data class Ingredients(
    val malt: List<Ingredient>,
    val hops: List<Ingredient>,
    val yeast: String,
)

data class Ingredient(
    val name: String,
    val amount: Amount,
    val add: String? = null,
    val attribute: String? = null,
)

data class Amount(
    val value: String,
    val unit: String,
)