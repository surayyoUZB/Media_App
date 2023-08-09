package com.yoo.mediaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoo.mediaapp.database.Media
import com.yoo.mediaapp.databinding.ItemLayoutBinding

class RvAdapter():RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    var mediaList= mutableListOf<Media>()

    lateinit var onClick: (Media, Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mediaList[position], position)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(media:Media, position:Int){
            binding.name.text= media.name
            binding.time.text=media.time

            itemView.setOnClickListener {
                onClick(media, adapterPosition)
            }

        }



    }
}