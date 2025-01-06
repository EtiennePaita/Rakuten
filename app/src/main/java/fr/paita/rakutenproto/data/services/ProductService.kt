package fr.paita.rakutenproto.data.services

import fr.paita.rakutenproto.data.dto.ProductDetailsDto
import fr.paita.rakutenproto.data.dto.SearchResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("/products/details")
    suspend fun getDetails(@Query("id") productId: Long): Response<ProductDetailsDto>

    @GET("/products/search")
    suspend fun search(@Query("keyword") keyword: String): Response<SearchResponseDto>
}