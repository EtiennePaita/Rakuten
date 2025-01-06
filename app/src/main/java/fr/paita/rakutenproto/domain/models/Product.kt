package fr.paita.rakutenproto.domain.models

data class Product(
    val id: Long,
    val newBestPrice: Float,
    val usedBestPrice: Float,
    val headline: String,
    val reviewsAverageNote: Float,
    val nbReviews: Int,
    val categoryRef: Int,
    val imagesUrls: List<String>,
)
