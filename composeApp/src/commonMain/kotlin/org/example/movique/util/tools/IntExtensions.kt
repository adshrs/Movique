package org.example.movique.util.tools

fun Int.toHourMinuteFormat(): String {
	val hours = this / 60
	val minutes = this % 60
	return when {
		hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
		hours > 0 -> "${hours}h"
		else -> "${minutes}m"
	}
}

fun Int.toSeasonText(): String {
	return if (this == 1) "$this Season" else "$this Seasons"
}