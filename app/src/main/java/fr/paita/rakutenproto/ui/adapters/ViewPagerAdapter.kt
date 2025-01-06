package fr.paita.rakutenproto.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.paita.rakutenproto.databinding.ItemImageBinding

class ViewPagerAdapter: ListAdapter<Int, RecyclerView.ViewHolder>(DiffCallBack()) {
    private var imageList: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return imageList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ImageViewHolder = holder as ImageViewHolder

        imageList?.get(holder.bindingAdapterPosition)?.let { url ->
            Glide.with(viewHolder.itemBinding.pImage)
                .load(url)
                .centerInside()
                .into(viewHolder.itemBinding.pImage)
        }
    }

    fun setData(data: List<String>?) {
        imageList = data
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(val itemBinding: ItemImageBinding): RecyclerView.ViewHolder(itemBinding.root)


    private class DiffCallBack: DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

    }
}