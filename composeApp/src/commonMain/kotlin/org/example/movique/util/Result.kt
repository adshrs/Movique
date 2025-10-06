package org.example.movique.util

sealed interface Result<out D, out E : Error> {
	data class Success<out D>(val data: D) : Result<D, Nothing>
	data class Error<out E : org.example.movique.util.Error>(val error: E) : Result<Nothing, E>
	data object Loading : Result<Nothing, Nothing>

	val isSuccess: Boolean get() = this is Success
	val isError: Boolean get() = this is Error
	val isLoading: Boolean get() = this is Loading

	fun getOrNull(): D? = when (this) {
		is Success -> data
		else -> null
	}
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
	return when (this) {
		is Result.Success -> Result.Success(map(data))
		is Result.Error -> Result.Error(error)
		is Result.Loading -> Result.Loading
	}
}

inline fun <T, E : Error, R> Result<T, E>.mapSuccess(transform: (T) -> R): Result<R, E> {
	return when (this) {
		is Result.Success -> Result.Success(transform(data))
		is Result.Error -> Result.Error(error)
		is Result.Loading -> Result.Loading
	}
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
	return map { }
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
	if (this is Result.Success) {
		action(data)
	}
	return this
}

inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
	if (this is Result.Error) {
		action(error)
	}
	return this
}

typealias EmptyResult<E> = Result<Unit, E>