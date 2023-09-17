package com.example.innogeeks_team_project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.innogeeks_team_project.models.productcard

class homeFrag : Fragment() {


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
        rv.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        rv.setHasFixedSize(true)
        Adapter = homeAdapter(productList)
        rv.adapter = Adapter


        rv2 = view.findViewById(R.id.recycler_view2)
        rv2.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        rv2.setHasFixedSize(true)
        rv2.adapter = Adapter


        val gif: ImageView = view.findViewById(R.id.giffy)
        Glide.with(this).load(R.drawable.giphy).into(gif)
    }

    private fun dataInput() {
        productList = arrayListOf<productcard>()

        imageId = arrayOf(
            R.drawable.item1,
            R.drawable.item2,
            R.drawable.item3,
            R.drawable.item4
        )
        title = arrayOf(
            "RUS Organic Cranberry Juice",
            "Beyond Snack Kerala Banana Chips ",
            "Yoga Bar Multigrain Energy Bar",
            "NOTO Ice Cream"
        )
        details = arrayOf(
            "Experience the pure essence of nature with our Organic Cranberry Juice.",
            "It is made in pure coconut oil.They are rich in antioxidants, vitamin C etc.",
            "Yogabar's Energy Bars contain a nutrient-dense primary ingredient that is generally whole grains, nuts or seeds.",
            "treat your chocolate loving soul with this high-protein, low sugar and truly luscious tub!"
        )
        price = arrayOf(
            getString(R.string.RusPrice),
            "MRP : ₹ 57",
            getString(R.string.price),
            "MRP : ₹ 105"
        )

        for (i in title.indices) {
            val prod = productcard(imageId[i], title[i], details[i], price[i])
            productList.add(prod)
        }
    }
}