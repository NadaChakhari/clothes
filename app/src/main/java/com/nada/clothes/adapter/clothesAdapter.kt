package com.nada.clothes.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nada.clothes.R

class clothesAdapter :RecyclerView.Adapter<clothesAdapter.ViewHolder>{

    //boite pour ranger tout les composants à controler
    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        //image de la vetements ?
        val clotheImage = view.findViewById<ImageView>(R.id.image_item)

    }
}