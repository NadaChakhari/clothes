package com.nada.clothes.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.nada.clothes.ClotheRepository
import com.nada.clothes.MainActivity
import com.nada.clothes.R

class AddClotheFragment(
    private val context : MainActivity
): Fragment() {

    private var uploadedImage:ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_clothe, container, false)

        //recupperer uploadedImage pour associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //recupperer le bouton  pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //lorsqu'on clique dessus ca ouvre les image du telephone
        pickupImageButton.setOnClickListener{pickupImage()}

        return view
    }

    private fun pickupImage() {
        val intent  = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture" ), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){
            //verifier les données sont nulles
            if(data == null || data.data == null) return
            //recupperer l'image
            val selectedImage = data.data

            //mettre à jour l'apperçu de l'image
            uploadedImage?.setImageURI(selectedImage)

            //heberger sur le bucket
            val repo = ClotheRepository(
                repo.uploadImage(selectedImage!!)
            )

        }
    }
}