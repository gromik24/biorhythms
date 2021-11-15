package com.gromik24.biorhythm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class InfoAdapter (private val infoList: List<Info>) : RecyclerView.Adapter<InfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val InfoListItemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.info_list_item, parent, false)

        return InfoViewHolder(InfoListItemView)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val Info = infoList[position]
        holder.bind(Info)
    }

    override fun getItemCount(): Int {
        return infoList.size
    }
}