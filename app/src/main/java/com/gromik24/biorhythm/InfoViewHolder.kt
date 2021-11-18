package com.gromik24.biorhythm

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class InfoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(info: Info) {
        val headerTextView: TextView = itemView.findViewById(R.id.header_text_view)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)

        headerTextView.text = info.header
        descriptionTextView.text = info.text

    }
}