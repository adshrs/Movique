package org.example.movique.di

import io.ktor.client.engine.okhttp.OkHttp
import org.example.movique.networking.TmdbClient
import org.example.movique.networking.createHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule = module {
	single { OkHttp.create() } // Provides the OkHttp engine
	single { createHttpClient(get()) } // Provides the HttpClient using the OkHttp engine
	single { TmdbClient(get()) }
}