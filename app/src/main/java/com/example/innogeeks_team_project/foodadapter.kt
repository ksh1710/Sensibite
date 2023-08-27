package com.example.innogeeks_team_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// class foodAdapter(
//    private val foodList: List<food>) : RecyclerView.Adapter<foodAdapter.FoodViewHolder>() {
//
//class FoodViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
//val foodImageView: ImageView = itemView.findViewById(R.id.imagev1)
//val foodNameTv: TextView = itemView.findViewById(R.id.textv1)
//}

//     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
//         val View = LayoutInflater.from(parent.context).inflate(R.layout.activity_each_item , parent , false )
//         var view = null
//         return FoodViewHolder(view) }
//     override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
//        val food = foodList[position]
//holder.foodImageView.setImageResource(food.foodImage)
//holder.foodNameTv.text = food.foodName
//}    override fun getItemCount(): Int {
//        return foodList.size
