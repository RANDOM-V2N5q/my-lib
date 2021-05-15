package com.example.mylib

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ItemAdapter(var list: ArrayList<ItemData>, var itemTouchHelper: ItemTouchHelper, var urv: UniversalRecyclerview): RecyclerView.Adapter<ItemAdapter.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        fun setTitle(title: String) {
            itemView.findViewById<TextView>(R.id.title).text = title
        }

        fun setSubtitle(title: String) {
            itemView.findViewById<TextView>(R.id.subtitle).text = title
        }

        fun setRate(rate: Double) {
            itemView.findViewById<RatingBar>(R.id.ratingBar).rating = rate.toFloat()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item, parent, false)
        val holder =   Holder(view)

        holder.itemView.findViewById<ImageView>(R.id.dragHandle).setOnTouchListener { view, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                itemTouchHelper.startDrag(holder)
            }
            return@setOnTouchListener true
        }

        return holder
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setTitle(list[position].title)
        holder.setSubtitle(list[position].subtitle)
        holder.setRate(list[position].rate)

        holder.itemView.setOnClickListener {
            urv.onItemClick(position)
        }
    }

}