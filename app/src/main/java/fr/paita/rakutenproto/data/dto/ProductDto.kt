package fr.paita.rakutenproto.data.dto

import fr.paita.rakutenproto.domain.models.Product

data class ProductDto(
    val id: Long,
    val newBestPrice: Float,
    val usedBestPrice: Float,
    val headline: String,
    val reviewsAverageNote: Float,
    val nbReviews: Int,
    val categoryRef: Int,
    val imagesUrls: List<String>,
)

fun ProductDto.toProduct() = Product(
    this.id,
    this.newBestPrice,
    this.usedBestPrice,
    this.headline,
    this.reviewsAverageNote,
    this.nbReviews,
    this.categoryRef,
    this.imagesUrls
)