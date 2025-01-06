package fr.paita.rakutenproto.ui.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.paita.rakutenproto.R
import fr.paita.rakutenproto.databinding.ItemProductBinding
import fr.paita.rakutenproto.domain.models.Product
import fr.paita.rakutenproto.ui.utils.setSafeOnClickListener

interface ProductListener {
    fun onProductClick(productId: Long)
}

class ProductAdapter(private val listener: ProductListener): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private var products: List<Product>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ProductViewHolder = holder as ProductViewHolder

        products?.get(holder.bindingAdapterPosition)?.let { product ->
            viewHolder.itemBinding.productLayout.setSafeOnClickListener {
                Log.d("Click", "Product click")
                listener.onProductClick(product.id)
            }
            Glide.with(viewHolder.itemBinding.productImg)
                .load(product.imagesUrls.lastOrNull())
                .centerInside()
                .into(viewHolder.itemBinding.productImg)

            viewHolder.itemBinding.productTitle.text = product.headline
            viewHolder.itemBinding.nbReviewsTextView.text = "${product.nbReviews}"
            viewHolder.itemBinding.averageNoteTextView.text = viewHolder.itemBinding.averageNoteTextView.context.getString(R.string.note, product.reviewsAverageNote)
            viewHolder.itemBinding.newBestPriceTextView.text = "${product.newBestPrice} €"
            viewHolder.itemBinding.usedBestPriceTextView.text = "${product.usedBestPrice} €"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(products: List<Product>?) {
        this.products = products
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(val itemBinding: ItemProductBinding): RecyclerView.ViewHolder(itemBinding.root)
}