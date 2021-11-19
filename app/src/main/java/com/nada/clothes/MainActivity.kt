package com.nada.clothes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nada.clothes.fragments.homeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //charger notre ClotheRepository
        val repo = ClotheRepository()

        //mettre à jour liste clothe
        repo.updateData{
            //injecter le fragment dans notre boite (fragment_containe)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, homeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }


    }
}