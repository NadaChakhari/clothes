package com.nada.clothes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.nada.clothes.ClotheRepository.SingleTon.clotheList
import com.nada.clothes.MainActivity
import com.nada.clothes.R
import com.nada.clothes.adapter.ClotheItemEchange
import com.nada.clothes.adapter.clothesAdapter

class homeFragment (
    private  val context:MainActivity
        ) : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater?.inflate(R.layout.fragment_home, container, false)



        //recupérer recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = clothesAdapter(context, clotheList, R.layout.item_horizontal_clothes)

        //recupérer le second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = clothesAdapter(context, clotheList, R.layout.item_vertical_clothes)
        verticalRecyclerView.addItemDecoration(ClotheItemEchange())

        return view
    }
}