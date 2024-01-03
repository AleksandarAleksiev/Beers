package com.aaleksiev.core.data.di

import com.aaleksiev.core.data.beer.BeerRepository
import com.aaleksiev.core.data.beer.BeerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
  @Binds
  internal abstract fun bindsBeerRepository(beerRepositoryImpl: BeerRepositoryImpl): BeerRepository
}