package fr.paita.rakutenproto.data.dto

import fr.paita.rakutenproto.domain.models.SearchResponse

data class SearchResponseDto(
    val pageNumber: Int,
    val title: String,
    val resultProductsCount: Int,
    val maxProductsPerPage: Int,
    val maxPageNumber: Int,
    val products: List<ProductDto>
)

fun SearchResponseDto.toSearchResponse() = SearchResponse(
    this.pageNumber,
    this.title,
    this.resultProductsCount,
    this.maxProductsPerPage,
    this.maxPageNumber,
    this.products.map { it.toProduct() }
)