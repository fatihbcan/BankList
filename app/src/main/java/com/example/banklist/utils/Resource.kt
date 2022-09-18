package com.example.banklist.utils


sealed class Resource<out T> {
    abstract val data: T?

    data class Success<out T>(override val data: T) : Resource<T>()
    data class Error<out T>(val throwable: Throwable? = null, override val data: T? = null) : Resource<T>()
    data class Loading<out T>(override val data: T? = null) : Resource<T>()

    inline fun <R> fold(
        onSuccess: (value: T) -> R,
        onError: (throwable: Throwable?, data: T?) -> R,
        onLoading: (data: T?) -> R,
    ): R {
        return when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(throwable, data)
            is Loading -> onLoading(data)
        }
    }

    inline fun <R> map(
        mapper: (value: T) -> R
    ): Resource<R> {
        return when (this) {
            is Success -> Success(mapper(data))
            is Error -> Error(throwable, data?.let(mapper))
            is Loading -> Loading(data?.let(mapper))
        }
    }

    inline fun <R> map(
        onSuccess: (value: T) -> Resource<R>,
        onError: (throwable: Throwable?, data: T?) -> Resource<R>,
        onLoading: (data: T?) -> Resource<R>,
    ): Resource<R> {
        return when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(throwable, data)
            is Loading -> onLoading(data)
        }
    }

    inline fun onSuccess(action: (value: T) -> Unit): Resource<T> {
        if (this is Success) {
            action(data)
        }
        return this
    }

    inline fun onError(action: (throwable: Throwable?, data: T?) -> Unit): Resource<T> {
        if (this is Error) {
            action(throwable, data)
        }
        return this
    }

    inline fun onLoading(action: (data: T?) -> Unit): Resource<T> {
        if (this is Loading) {
            action(data)
        }
        return this
    }
}