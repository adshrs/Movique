package org.example.movique.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
	CoroutineScope(Dispatchers.IO).launch {
		startKoin {
			config?.invoke(this)
			modules(sharedModule, platformModule)
		}
	}
}