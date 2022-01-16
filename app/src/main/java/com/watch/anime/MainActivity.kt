package com.watch.anime

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button


class MainActivity : Activity() {
    var searchedAnime: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val autoCompleteTextView = findViewById<View>(R.id.autoTextView) as AutoCompleteTextView
        val button = findViewById<Button>(R.id.btn)

        button.setOnClickListener {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, searchedAnime)

            val names = Query().execute(autoCompleteTextView.getText().toString())

            val animes:Array<String> = names.get()
            for (anime in animes){
                searchedAnime.add(anime.replace("-", " ").replace("/category/",""))
            }

            autoCompleteTextView.setAdapter(adapter)
            autoCompleteTextView.postDelayed(Runnable {
                autoCompleteTextView.showDropDown()
            }, 10)
        }






    }

}