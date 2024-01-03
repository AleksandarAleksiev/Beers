package com.aaleksiev.beer.ui

import com.aaleksiev.core.model.Amount
import com.aaleksiev.core.model.Beer
import com.aaleksiev.core.model.Ingredient
import com.aaleksiev.core.model.Ingredients

object Dummy {
  val beers = listOf(
    Beer(
      id = 1,
      name = "Fake Lager",
      tagline = "A Real Bitter Experience.",
      description = "A light, crisp and bitter IPA brewed with English and American hops. " +
        "A small batch brewed only once.",
      imageUrl = "keg",
      ingredients = Ingredients(
        malt = listOf(
          Ingredient(
            name = "Maris Otter Extra Pale",
            amount = Amount(
              value = "3.3",
              unit = "kilograms"
            )
          )
        ),
        hops = listOf(
          Ingredient(
            name = "Fuggles",
            amount = Amount(
              value = "25",
              unit = "grams"
            )
          )
        ),
        yeast = Ingredient(
          name = "Wyeast 1056 - American Ale",
        )
      )
    )
  )
}