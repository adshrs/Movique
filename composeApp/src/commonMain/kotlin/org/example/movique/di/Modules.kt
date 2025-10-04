package org.example.movique.di

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import org.example.movique.data.repository.TmdbRepository
import org.example.movique.networking.TmdbClient
import org.example.movique.networking.createHttpClient
import org.example.movique.theme.thememode.ThemeRepository
import org.example.movique.theme.thememode.ThemeRepositoryImpl
import org.example.movique.viewmodel.HomeViewModel
import org.example.movique.viewmodel.SearchViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
	// Provide Settings and ThemeRepository
	single<ObservableSettings> { Settings() as ObservableSettings }
	single<ThemeRepository> { ThemeRepositoryImpl(get()) }

	// Provide HTTP Clients
	single { createHttpClient(get()) }

	// Provide Networking Clients
	single { TmdbClient(get()) }

	// Provide Repositories
	single { TmdbRepository(get()) }

	// Provide ViewModels
	viewModelOf(::HomeViewModel)
	viewModelOf(::SearchViewModel)
}