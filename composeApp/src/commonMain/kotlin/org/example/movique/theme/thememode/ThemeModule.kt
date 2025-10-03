package org.example.movique.theme.thememode

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val themeModule = module {
	// Provide Settings instance. If you prefer to configure platform-specific Settings,
	// provide a platform factory from androidMain/iosMain and bind it here.
	single<ObservableSettings> { Settings() as ObservableSettings }
	single<ThemeRepository> { ThemeRepositoryImpl(get()) }
}