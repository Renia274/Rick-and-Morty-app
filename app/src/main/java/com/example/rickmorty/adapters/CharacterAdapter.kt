package com.example.rickmorty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmorty.data.CharacterInfo
import com.example.rickmorty.databinding.CharacterItemBinding


class CharacterAdapter(
    private val data: List<CharacterInfo>,
    private val onItemClick: (CharacterInfo) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CharacterItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = data[position]
        with(holder.binding) {
            CharacterName.text = character.name
            CharacterOrigin.text = character.origin?.name
            CharacterSpecies.text = character.species
            CharacterLocation.text = character.location?.name ?: "Unknown"
            CharacterStatus.text = character.status
            Glide.with(holder.itemView.context)
                .load(character.image)
                .centerCrop()
                .into(CharacterImage)
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            onItemClick(character)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}