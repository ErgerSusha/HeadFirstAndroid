package com.example.erger.tasksonheadfirstandroidkotlin5

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class CaptionedImagesAdapter(private val captions: Array<String?>, private val imageIds: Array<Int?>)
    : RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>() {

    private var listener: Listener? = null

    interface Listener {
        fun onClick(position: Int)
    }

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaptionedImagesAdapter.ViewHolder {
        val cv = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_captioned_image, parent, false) as CardView
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView

        val imageView = cardView.findViewById<View>(R.id.info_image) as ImageView
        val drawable = cardView.resources.getDrawable(imageIds[position]!!)
        val textView = cardView.findViewById<View>(R.id.info_text) as TextView

        imageView.setImageDrawable(drawable)
        imageView.contentDescription = captions[position]
        textView.text = captions[position]
        cardView.setOnClickListener {
            if (listener != null) {
                listener!!.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return captions.size
    }
}