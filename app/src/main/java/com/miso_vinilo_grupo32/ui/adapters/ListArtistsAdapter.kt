package com.miso_vinilo_grupo32.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miso_vinilo_grupo32.R
import com.miso_vinilo_grupo32.databinding.ListItemBinding
import com.miso_vinilo_grupo32.models.SimpleArtist

class ListArtistsAdapter : RecyclerView.Adapter<ListArtistsAdapter.ArtistViewHolder>(){

    var artists :List<SimpleArtist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val withDataBinding: ListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false)
        parentContext = parent.context
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            val artist = artists[position]
            it.artistName = artist.name
            Glide.with(parentContext)
                .load(artist.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_downloading_image_foreground)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image_foreground))
                .into(it.artistListImage)
        }

        /*holder.viewDataBinding.root.setOnClickListener {

        }*/
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    class ArtistViewHolder(val viewDataBinding: ListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_item
        }
    }
}