package com.example.innogeeks_team_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.Shapeable

class MyAdapter(private val foodList: ArrayList<food>) : RecyclerView.Adapter<MyAdapter.MyViewHolder> {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = foodList[position]
        holder.banana.setImageResource(currentItem.)
        holder.textView2.text = currentItem.
    }

    override fun getItemCount(): Int {
       return foodList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
      val banana : ShapeableImageView = itemView.findViewById(R.id.banana)
        val textView2 : TextView = itemView.findViewById(R.id.textView2)
    }
}