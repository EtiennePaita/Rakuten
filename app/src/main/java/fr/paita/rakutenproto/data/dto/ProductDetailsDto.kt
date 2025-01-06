package fr.paita.rakutenproto.data.dto

import fr.paita.rakutenproto.domain.models.ProductDetails

data class ProductDetailsDto(
    val id: Long,
    val headline: String,
    val newBestPrice: Float,
    val usedBestPrice: Float,
    val images: List<ProductImage>?,
    val description: String, // html
    val globalRating: GlobalRating
)

data class GlobalRating(
    val score: Float,
    val nbReviews: Int
)

data class ProductImage(
    val id: Long,
    val imagesUrls: ImageUrls
)

data class ImageUrls(
    val entry: List<ImageInfo>
)

data class ImageInfo(
    val size: String,
    val url: String
)

fun ProductDetailsDto.toProductDetails() = ProductDetails(
    this.id,
    this.headline,
    this.newBestPrice,
    this.usedBestPrice,
    this.images?.map { it.imagesUrls.entry.first().url },
    this.description,
    this.globalRating.score,
    this.globalRating.nbReviews
)
