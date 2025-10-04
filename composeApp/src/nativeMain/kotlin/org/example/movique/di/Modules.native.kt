package org.example.movique.di

import io.ktor.client.engine.darwin.Darwin
import org.example.movique.networking.TmdbClient
import org.example.movique.networking.createHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule = module {
	single { Darwin.create() } // Provides the Darwin engine
	single { createHttpClient(get()) } // Provides the HttpClient using the Darwin engine
	single { TmdbClient(get()) }
}