package org.example.movique

import android.app.Application
import org.example.movique.di.initKoin
import org.example.movique.theme.thememode.themeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
	override fun onCreate() {
		super.onCreate()

		initKoin {
			androidContext(this@App)
		}
	}
}