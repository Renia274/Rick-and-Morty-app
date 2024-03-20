package com.example.rickmorty.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmorty.R
import com.example.rickmorty.data.CharacterInfo
import com.example.rickmorty.databinding.CharacterItemBinding



class CharacterAdapter(val data: List<CharacterInfo>): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CharacterItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.CharacterName.setText(data[position].name)
        holder.binding.CharacterOrigin.setText(data[position].origin?.name)
        holder.binding.CharacterSpecies.setText(data[position].species)
        holder.binding.CharacterLocation.setText(data[position].location?.name ?: "Unknown")
        holder.binding.CharacterStatus.apply{
            setText(data[position].status)
            setTextColor(when(data[position].status?.lowercase()){
                "alive"-> Color.GREEN
                "dead" -> Color.RED
                "unknown"-> Color.YELLOW
                else->Color.WHITE
            })
        }
        Glide.with(holder.itemView.context)
            .load(data[position].image)
            .centerCrop()
            .into(holder.binding.CharacterImage)


    }

    override fun getItemCount(): Int {
        return if(data.isEmpty()) 0 else data.size
    }
}