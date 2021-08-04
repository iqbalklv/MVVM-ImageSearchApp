package com.miqbalkalevi.imagesearch.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.miqbalkalevi.imagesearch.R
import com.miqbalkalevi.imagesearch.data.UnsplashPhoto
import com.miqbalkalevi.imagesearch.databinding.ItemUnsplashPhotoBinding

class UnsplashPhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    getItem(position)?.let { item -> listener.onItemClick(item) }
                }
            }
        }

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls?.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_photo_24)
                    .into(ivMain)

                tvUsername.text = photo.user?.username
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(photo: UnsplashPhoto)
    }

    class DiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
            oldItem == newItem
    }
}