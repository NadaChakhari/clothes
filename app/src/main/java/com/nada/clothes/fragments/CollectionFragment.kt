package com.nada.clothes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nada.clothes.ClotheRepository.SingleTon.clotheList
import com.nada.clothes.MainActivity
import com.nada.clothes.R
import com.nada.clothes.adapter.ClotheItemEchange
import com.nada.clothes.adapter.clothesAdapter

class CollectionFragment(
    private  val context : MainActivity
    ): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        //recupperer my recycleview
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = clothesAdapter(context, clotheList.filter{ it.liked}, R.layout.item_vertical_clothes)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)

        collectionRecyclerView.addItemDecoration(ClotheItemEchange())

        return view
    }

}