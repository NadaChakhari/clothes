package com.nada.clothes.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.nada.clothes.ClotheModel
import com.nada.clothes.ClotheRepository
import com.nada.clothes.ClotheRepository.SingleTon.downloadUri
import com.nada.clothes.MainActivity
import com.nada.clothes.R
import java.util.*

class AddClotheFragment(
    private val context : MainActivity
): Fragment() {

    private var uploadedImage:ImageView? = null
    private var file:Uri? = null

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

        //recupperer le boutton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener{ sedForm(view) }

        return view
    }

    private fun sedForm(view : View) {
        val repo = ClotheRepository()
        repo.uploadImage(file!!){
            val clotheName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val clotheDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val clothePrice= view.findViewById<EditText>(R.id.price_input).text.toString()
            val clotheState = view.findViewById<Spinner>(R.id.state_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri
            //creer un noulvel  objet ClotheModel
            val clothe = ClotheModel(
                UUID.randomUUID().toString(),
                clotheName,
                clotheDescription,
                downloadImageUrl.toString(),
                clothePrice,
                clotheState
            )


            //envoyer au bdd
            repo.insertClothe(clothe)
        }


    }

    private fun pickupImage() {
        val intent  = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){
            //verifier les données sont nulles
            if(data == null || data.data == null) return
            //recupperer l'image
            val file = data.data

            //mettre à jour l'apperçu de l'image
            uploadedImage?.setImageURI(file)



        }
    }
}