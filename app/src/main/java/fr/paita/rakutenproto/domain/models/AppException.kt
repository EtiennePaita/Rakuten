package fr.paita.rakutenproto.domain.models

data class AppException(
    val errorCode: Int,
    val errorMessage: String
): Exception(errorMessage)