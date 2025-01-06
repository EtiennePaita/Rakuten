package fr.paita.rakutenproto.domain.models

/**
 * Model AppResult defines all the types of result the app can get from network
 *
 * @param T
 */
sealed class AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Error(val error: Exception) : AppResult<Nothing>()
    data object Loading : AppResult<Nothing>()
    data object Initial : AppResult<Nothing>()
}