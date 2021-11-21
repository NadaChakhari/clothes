package com.nada.clothes.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nada.clothes.*

class clothesAdapter(
    val context: MainActivity,
    private val clotheList: List<ClotheModel>,
    private  val layoutId: Int) :RecyclerView.Adapter<clothesAdapter.ViewHolder>(){

    //boite pour ranger tout les composants à controler
    class ViewHolder(view : View): RecyclerView.ViewHolder(view){
        //image de la vetements ?
        val clotheImage = view.findViewById<ImageView>(R.id.image_item)
        val clotheName: TextView? = view.findViewById(R.id.name_item)
        val clotheDescription: TextView?= view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recupperer les information des vetements
        val currentClothe = clotheList[position]

        //recupperer le repository
        val repo = ClotheRepository()

        //utiliser glide pour récupperer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentClothe.imageUrl)).into(holder.clotheImage)

        //mettre à jour le nom de la clothe
        holder.clotheName?.text = currentClothe.name

        //mettre à jour la description de la clothe
        holder.clotheDescription?.text = currentClothe.description

        //vérifier si le clothe a été liké
        if (currentClothe.liked){
            holder.starIcon.setImageResource(R.drawable.ic_like)
        }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unlik)
        }

        //rajouter une interaction avec cette etoile
        holder.starIcon.setOnClickListener{
            //inverse si le button est like ou non
            currentClothe.liked = !currentClothe.liked
            //mettre à jour l'objet clothe
            repo.updateClothe(currentClothe)
        }

       //interaction lors du clic sur une piece de vetements
        holder.itemView.setOnClickListener{
            //afficher la popup
            ClothePopup(currentClothe, this).show()
        }
    }

    override fun getItemCount(): Int = clotheList.size
}