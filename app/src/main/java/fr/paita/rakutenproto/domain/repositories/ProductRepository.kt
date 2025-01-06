package fr.paita.rakutenproto.domain.repositories

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.paita.rakutenproto.data.datasources.remote.ProductDataSource
import fr.paita.rakutenproto.data.dto.toProductDetails
import fr.paita.rakutenproto.data.dto.toSearchResponse
import fr.paita.rakutenproto.domain.factory.ExceptionFactory
import fr.paita.rakutenproto.domain.models.AppResult
import fr.paita.rakutenproto.domain.models.ProductDetails
import fr.paita.rakutenproto.domain.models.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val productDataSource: ProductDataSource
) {
    fun getDetails(productId: Long): Flow<AppResult<ProductDetails?>> {
        return flow {
            try {
                emit(AppResult.Loading)
                val response = productDataSource.getDetails(productId)
                if (response.isSuccessful) {
                    val cities = response.body()?.toProductDetails()
                    emit(AppResult.Success(cities))
                } else {
                    Log.d("PRODUCT_DETAILS_ERROR", response.code().toString())
                    emit(AppResult.Error(ExceptionFactory.getErrorFromCode(response.code(), context)))
                }
            } catch (e: Exception) {
                Log.e("PRODUCT_DETAILS_ERROR", e.message, e)
                emit(AppResult.Error(ExceptionFactory.getErrorFrom(e, context)))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun search(keyword: String): Flow<AppResult<SearchResponse?>> {
        return flow {
            try {
                emit(AppResult.Loading)
                val response = productDataSource.search(keyword)
                if (response.isSuccessful) {
                    val cities = response.body()?.toSearchResponse()
                    emit(AppResult.Success(cities))
                } else {
                    Log.d("PRODUCT_SEARCH_ERROR", response.code().toString())
                    emit(AppResult.Error(ExceptionFactory.getErrorFromCode(response.code(), context)))
                }
            } catch (e: Exception) {
                Log.e("PRODUCT_SEARCH_ERROR", e.message, e)
                emit(AppResult.Error(ExceptionFactory.getErrorFrom(e, context)))
            }
        }.flowOn(Dispatchers.IO)
    }
}