package com.nada.clothes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nada.clothes.fragments.homeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //injecter le fragment dans notre boite (fragment_containe)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, homeFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}