package fr.paita.rakutenproto.domain.models

data class ProductDetails(
    val id: Long,
    val headline: String,
    val newBestPrice: Float,
    val usedBestPrice: Float,
    val imagesUrls: List<String>?,
    val description: String, // html
    val score: Float,
    val nbReviews: Int,
)
