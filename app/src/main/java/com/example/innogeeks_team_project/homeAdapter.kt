package com.example.innogeeks_team_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.innogeeks_team_project.models.productcard

class homeAdapter(private val itemList: ArrayList<productcard>) :
    RecyclerView.Adapter<homeAdapter.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.productImage.setImageResource(currentItem.prodImg)
        holder.productTitle.text = currentItem.prodHeading
        holder.productDetails.text = currentItem.prodDetails
        holder.productPrice.text = currentItem.prodPrice
        holder.buyButton.setOnClickListener {
            Toast.makeText(it.context, "buy now", Toast.LENGTH_SHORT).show()
        }
    }

    class myViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val productImage: ImageView = item.findViewById(R.id.proimg)
        val productTitle: TextView = item.findViewById(R.id.proTitle)
        val productDetails: TextView = item.findViewById(R.id.proDetails)
        val productPrice: TextView = item.findViewById(R.id.proPrice)
        val buyButton: Button = item.findViewById(R.id.buyBtn)
    }

}