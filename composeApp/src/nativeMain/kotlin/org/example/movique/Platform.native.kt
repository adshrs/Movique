package org.example.movique

import platform.Foundation.NSBundle

actual fun getTmdbApiKey(): String {
	return NSBundle.mainBundle.objectForInfoDictionaryKey("TMDB_API_KEY") as? String
		?: throw IllegalStateException("TMDB_API_KEY not found in Info.plist")
}