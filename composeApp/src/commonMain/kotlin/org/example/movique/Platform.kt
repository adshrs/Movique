package org.example.movique

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getTmdbApiKey(): String