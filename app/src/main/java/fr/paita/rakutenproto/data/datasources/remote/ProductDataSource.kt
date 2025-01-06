package fr.paita.rakutenproto.data.datasources.remote

import fr.paita.rakutenproto.data.dto.ProductDetailsDto
import fr.paita.rakutenproto.data.dto.SearchResponseDto
import fr.paita.rakutenproto.data.services.ProductService
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class ProductDataSource @Inject constructor(
    @Named("retrofit") private val retrofit: Retrofit
) {
    suspend fun getDetails(productId: Long): Response<ProductDetailsDto> {
        val service = retrofit.create(ProductService::class.java)
        return service.getDetails(productId)
    }

    suspend fun search(keyword: String): Response<SearchResponseDto> {
        val service = retrofit.create(ProductService::class.java)
        return service.search(keyword)
    }

}