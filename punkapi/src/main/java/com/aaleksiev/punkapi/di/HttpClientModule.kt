package com.aaleksiev.punkapi.di

import android.util.Log
import com.aaleksiev.punkapi.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
internal class HttpClientModule {
    private fun cioHttpClient(
        httpClientConfig: HttpClientConfig<CIOEngineConfig>.() -> Unit
    ) = HttpClient(CIO) {
        // Set a timeout of 10 seconds
        engine {
            requestTimeout = 10_000
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    encodeDefaults = true
                    isLenient = true
                    allowSpecialFloatingPointValues = true
                    allowStructuredMapKeys = true
                    prettyPrint = true
                    useArrayPolymorphism = false
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Resources)
        install(Logging) {
            level = when {
                BuildConfig.DEBUG -> LogLevel.ALL
                else -> LogLevel.NONE
            }
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("HTTP", message)
                }
            }
        }
        httpClientConfig(this)
    }

    @Provides
    internal fun httpClient() = cioHttpClient {
        defaultRequest {
            url(BuildConfig.PUNK_BASE_URL)
            addCommonHeaders()
        }
    }

    private fun DefaultRequest.DefaultRequestBuilder.addCommonHeaders() {
        header("Content-Type", "application/json")
    }
}