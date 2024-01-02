package com.aaleksiev.punkapi.di

import com.aaleksiev.punkapi.PunkApi
import com.aaleksiev.punkapi.PunkApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PunkApiModule {
    @Binds
    internal abstract fun bindsPunkApi(polygonApiImpl: PunkApiImpl): PunkApi
}