package org.example.movique

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getTmdbApiKey(): String = BuildConfig.TMDB_API_KEY