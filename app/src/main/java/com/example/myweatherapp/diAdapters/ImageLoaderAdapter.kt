package com.example.myweatherapp.diAdapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myweatherapp.R

class ImageLoaderAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun bindImage(imgView: AppCompatImageView, imgUrl: String?) {
            imgUrl?.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(imgView.context)
                    .load(imgUrl)
                    .apply(

                        RequestOptions()
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                    )
                    .into(imgView)
            }
        }
    }
}