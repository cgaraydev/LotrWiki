package com.example.lotrwiki.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.lotrwiki.databinding.ImageItemViewholderBinding
import com.example.lotrwiki.model.Images


class ImagePagerAdapter(
    private val images: List<Images>
) : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding: ImageItemViewholderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ImageItemViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.binding.pbImageLoading.visibility = View.VISIBLE
        Glide.with(holder.binding.root)
            .load("https://firebasestorage.googleapis.com/v0/b/lotrwiki-2dd76.appspot.com/o/" + image.url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.binding.pbImageLoading.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.binding.pbImageLoading.visibility = View.GONE
                    return false
                }
            })
            .into(holder.binding.ivItemDetails)
        if (image.artist != null) {
            holder.binding.tvImageDescription.setHtmlText(image.artist)
//            holder.binding.tvImageDescription.isSelected = true
//            holder.binding.tvImageDescription.requestFocus()
        } else {
            holder.binding.tvImageDescription.visibility = View.GONE
        }
    }
}
//class ImagePagerAdapter(
//    private val imageUrls: List<String>
//) : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {
//
//    class ImageViewHolder(val binding: ImageItemViewholderBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val binding =
//            ImageItemViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ImageViewHolder(binding)
//    }
//
//    override fun getItemCount() = imageUrls.size
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageUrl = imageUrls[position]
//        holder.binding.pbImageLoading.visibility = View.VISIBLE
//        Glide.with(holder.binding.root)
//            .load(imageUrl)
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    holder.binding.pbImageLoading.visibility = View.GONE
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    holder.binding.pbImageLoading.visibility = View.GONE
//                    return false
//                }
//            })
//            .into(holder.binding.ivItemDetails)
//    }
//}

//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imageUrl = imageUrls[position]
//        holder.binding.pbImageLoading.visibility = View.VISIBLE
//        Glide.with(holder.binding.root)
//            .load(imageUrl)
//            .into(holder.binding.ivItemDetails)
//        holder.binding.pbImageLoading.visibility = View.GONE
//    }