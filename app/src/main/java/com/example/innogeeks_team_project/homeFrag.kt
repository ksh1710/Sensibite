package com.example.innogeeks_team_project

import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class homeFrag : Fragment() {
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!

    private lateinit var Adapter: homeAdapter
    private lateinit var rv: RecyclerView
    private lateinit var rv2: RecyclerView
    private lateinit var productList: ArrayList<productcard>

    lateinit var imageId: Array<Int>
    lateinit var title: Array<String>
    lateinit var details: Array<String>
    lateinit var price: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInput()
        rv = view.findViewById(R.id.recycler_view1)
        rv.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)
        rv.setHasFixedSize(true)
        Adapter = homeAdapter(productList)
        rv.adapter = Adapter


        rv2 = view.findViewById(R.id.recycler_view2)
        rv2.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)
        rv2.setHasFixedSize(true)
        rv2.adapter = Adapter


        val gif:ImageView = view.findViewById(R.id.giffy)
        Glide.with(this).load(R.drawable.giphy).into(gif)
    }

    private fun dataInput() {
        productList = arrayListOf<productcard>()

        imageId = arrayOf(
            R.drawable.almond,
            R.drawable.almond,
            R.drawable.almond,
            R.drawable.almond
        )
        title = arrayOf(
            getString(R.string.str1),
            getString(R.string.str2),
            getString(R.string.str3),
            getString(R.string.str4)
        )
        details = arrayOf(
            getString(R.string.det),
            getString(R.string.det),
            getString(R.string.det),
            getString(R.string.det)
        )
        price = arrayOf(
            getString(R.string.price),
            getString(R.string.price),
            getString(R.string.price),
            getString(R.string.price)
        )

        for (i in title.indices) {
            val prod = productcard(imageId[i], title[i], details[i], price[i])
            productList.add(prod)
        }
        Log.d("idk", productList.toString())
    }
}