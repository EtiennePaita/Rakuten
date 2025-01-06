package fr.paita.rakutenproto.domain.factory

import android.content.Context
import fr.paita.rakutenproto.R
import fr.paita.rakutenproto.domain.models.AppException
import java.net.ConnectException

object ExceptionFactory {
    fun getErrorFromCode(errorCode: Int, context: Context): Exception {
        return when (errorCode) {
            400 -> AppException(errorCode, context.getString(R.string.error_400))
            401 -> AppException(errorCode, context.getString(R.string.error_401))
            403 -> AppException(errorCode, context.getString(R.string.error_403))
            404 -> AppException(errorCode, context.getString(R.string.error_404))
            500 -> AppException(errorCode, context.getString(R.string.error_server))
            else -> AppException(errorCode, context.getString(R.string.error_server))
        }
    }

    fun getErrorFrom(e: Exception, context: Context): Exception {
        return when {
            e is ConnectException || e.message == "timeout" -> Exception(context.getString(R.string.no_internet_connection_error))
            else -> Exception(context.getString(R.string.error_server))
        }
    }
}