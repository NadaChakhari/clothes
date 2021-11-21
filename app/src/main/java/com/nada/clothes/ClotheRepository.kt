package com.nada.clothes

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nada.clothes.ClotheRepository.SingleTon.clotheList
import com.nada.clothes.ClotheRepository.SingleTon.dataBaseRef

class ClotheRepository {
    object SingleTon{
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
    //mettre à jour l'objet clothe en BDD
    fun  updateClothe(clothe: ClotheModel) = dataBaseRef.child(clothe.id).setValue(clothe)

    //supprimer une vetement de la base
    fun deleteClothe(clothe: ClotheModel) = dataBaseRef.child(clothe.id).removeValue()
}