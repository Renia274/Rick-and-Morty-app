package com.example.rickmorty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.data.EpisodeInfo
import com.example.rickmorty.databinding.EpisodeListItemBinding
import com.example.rickmorty.navigation.EpisodeNavClickListener

class EpisodesAdapter(
    val data: List<EpisodeInfo>,
    private val clickListener: EpisodeNavClickListener
) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = EpisodeListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            EpisodeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.episodeTitle.setText(data[position].name)
        holder.binding.episodeNumber.setText(data[position].episode)
        holder.itemView.setOnClickListener {
            clickListener.onEpisodeNavCLick(data[position].id)
        }
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) 0 else data.size
    }
}