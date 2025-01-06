package fr.paita.rakutenproto.domain.models

data class SearchResponse(
    val pageNumber: Int,
    val title: String,
    val resultProductsCount: Int,
    val maxProductsPerPage: Int,
    val maxPageNumber: Int,
    val products: List<Product>
)
