package com.nada.clothes

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nada.clothes.adapter.clothesAdapter

class ClothePopup(
    private val currentClothe : ClotheModel,
    private val adapter: clothesAdapter
    ) : Dialog(adapter.context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_clothe_details)
        setupComponents()
        setupCloserButton()
        setupDeletButton()
        setupStarButton()
    }

    private  fun updateStar (button : ImageView) {
        if (currentClothe.liked){
            button.setImageResource(R.drawable.ic_like)
        }else{
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    private fun setupStarButton() {
        //recupperer
        var starButton = findViewById<ImageView>(R.id.star_button)
        updateStar(starButton)
        //interaction
        starButton.setOnClickListener{
            currentClothe.liked = !currentClothe.liked
            updateStar(starButton)
        }
    }

    private fun setupDeletButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            //supprimer la vetement de la base
            val repo = ClotheRepository()
            repo.deleteClothe(currentClothe)
            dismiss()
        }
    }

    private fun setupCloserButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //fermer la fenetre
            dismiss()
        }
    }

    private fun setupComponents() {
       //actualiser l'image de la vetement
        val ClotheImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentClothe.imageUrl)).into(ClotheImage)

        //actualiser le nom de la vetement
        findViewById<TextView>(R.id.popup_vetement_name).text = currentClothe.name

        //actualiser la description de la vetement
        findViewById<TextView>(R.id.popup_vetement_description_subtitle).text = currentClothe.description

        //actualiser le prix du piece de vetement
        findViewById<TextView>(R.id.popup_vetement_price_title).text = currentClothe.Price

        //actuliser l'etat du piece de vetement
        findViewById<TextView>(R.id.popup_vetement_state_title).text = currentClothe.etat
    }
}