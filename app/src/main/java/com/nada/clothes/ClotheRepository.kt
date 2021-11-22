package com.nada.clothes

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.nada.clothes.ClotheRepository.SingleTon.clotheList
import com.nada.clothes.ClotheRepository.SingleTon.dataBaseRef
import com.nada.clothes.ClotheRepository.SingleTon.storageReference
import java.net.URI
import java.util.*

class ClotheRepository {
    object SingleTon{
        //donner le rien pour acceder au  bucket
        private val BUCKET_URL: String = "gs://veild-clothes.appspot.com"

        //connecter a notre espace de stockage
        val storageReference =FirebaseStorage.getInstance("gs://veild-clothes.appspot.com").getReferenceFromUrl(BUCKET_URL)

        //se connecter à la  reférence clothes
        var dataBaseRef = FirebaseDatabase.getInstance("https://veild-clothes-default-rtdb.firebaseio.com/").getReference("clothes")

        //créer une liste qui va convertir nos clothes
        val clotheList = arrayListOf<ClotheModel>()
    }

    fun updateData (callback: ()-> Unit){
        //absorber les données depuis le dataBaseRef -> list de clothes
        dataBaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes
                clotheList.clear()
                //recolter la liste
                for(ds in snapshot.children){
                    //construire un objet clothe
                    val clothe = ds.getValue(ClotheModel::class.java)

                    //verifier que la plante n'est pas null
                    if(clothe != null){
                        //ajouter le clothe à notre liste
                        clotheList.add(clothe)
                    }
                }
                //actionner le callback
                callback()
            }

            override fun onCancelled(p0: DatabaseError) {}

        })
    }
    //creer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: URI){
        //verifier que ce fichier n'est pas null
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //demarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{ task ->
            // s'il y a un probleme lors de l'envoi du fichier
                if(task.isSuccessful){
                    task.exception?.let{ throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task ->
                //verifier si tout a bien fonctionner
                if(task.isSuccessful){
                    //recupper l'image
                    val downloadURI = task.result
                }
            }

        }
    }


    //mettre à jour l'objet clothe en BDD
    fun  updateClothe(clothe: ClotheModel) = dataBaseRef.child(clothe.id).setValue(clothe)

    //supprimer une vetement de la base
    fun deleteClothe(clothe: ClotheModel) = dataBaseRef.child(clothe.id).removeValue()
}