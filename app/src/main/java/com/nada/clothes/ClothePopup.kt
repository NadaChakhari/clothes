package com.nada.clothes

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import com.nada.clothes.adapter.clothesAdapter

class ClothePopup(
    private  val adapter: clothesAdapter
    ): Dialog(adapter.context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_clothe_details)
    }
}