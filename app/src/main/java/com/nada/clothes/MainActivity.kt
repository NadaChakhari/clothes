package com.nada.clothes

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.nada.clothes.fragments.homeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(homeFragment(this), R.string.home_page_title)

        //importer le BottomNavigationView
        val navigationView = findViewById<BottomNavigationItemView>(R.id.navigation_view)
        navigationView.OnNavigationItemSelectedLostener {
            when(it.itemId){
                R.id.home_page -> {
                    loadFragment(homeFragment(this), R.string.home_page_title)
                    return@OnNavigationItemSelectedLostener true
                }
                R.id.collection_page -> {
                    loadFragment(homeFragment(this), R.string.collection_page_title)
                    return@OnNavigationItemSelectedLostener true
                }
                R.id.add_clothe_page -> {
                    loadFragment(homeFragment(this), R.string.add_clothes_page_title)
                    return@OnNavigationItemSelectedLostener true
                }
                else -> false
            }
        }




    }

    private  fun loadFragment(fragment: Fragment, string: Int){
        //charger notre ClotheRepository
        val repo = ClotheRepository()

        //actualiser le titre de la page
            findViewById<TextView>(R.id.page_title).text = resources.getString(string)

            //mettre à jour liste clothe
            repo.updateData{
            //injecter le fragment dans notre boite (fragment_containe)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}