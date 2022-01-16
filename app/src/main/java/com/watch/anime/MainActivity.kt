package com.watch.anime

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList


class MainActivity : Activity(), EpisodeAdapter.OnItemClickListener {
    private var searchedAnime: MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val autoCompleteTextView = findViewById<View>(R.id.autoTextView) as AutoCompleteTextView
        val button = findViewById<Button>(R.id.btn)
        val epTextView = findViewById<View>(R.id.recycler_view) as RecyclerView


        button.setOnClickListener {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, searchedAnime)
            val names = Query().execute(autoCompleteTextView.getText().toString())
            val animes:Array<String> = names.get()
            for (anime in animes){
                searchedAnime.add(anime.replace("-", " ").replace("/category/",""))
            }

            autoCompleteTextView.setAdapter(adapter)
            autoCompleteTextView.postDelayed({
                autoCompleteTextView.showDropDown()
            }, 10)

            autoCompleteTextView.setOnItemClickListener { parent, arg1, pos, id ->
                Toast.makeText(
                    this,
                    autoCompleteTextView.editableText.toString(),
                    Toast.LENGTH_LONG
                ).show()



                val episode = Episode()
                val exampleList = generateDummyList(episode.execute(autoCompleteTextView.editableText.toString()).get().toInt(),autoCompleteTextView)

                val rAdapter = EpisodeAdapter(exampleList)

                epTextView.adapter = rAdapter
                epTextView.layoutManager = LinearLayoutManager(this)
                epTextView.setHasFixedSize(true)
                }
            }

        }

    private fun generateDummyList(size: Int, autoCompleteTextView:AutoCompleteTextView): ArrayList<Items> {

        val list = ArrayList<Items>()

        for (i in 1 until size) {
            val item = Items(R.drawable.ic_android_black_24dp, "Episode $i", autoCompleteTextView.editableText.toString())
            list += item
        }

        return list
    }

    // TODO: Onlick does not work ://

    override fun onItemClick(position: Int, exampleList:List<Items>, rAdapter:EpisodeAdapter) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleList[position]
        Log.d("clicked", clickedItem.text2)
        rAdapter.notifyItemChanged(position)
    }

}
