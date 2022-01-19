package com.watch.anime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding.widget.RxTextView
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class MainActivity : Activity(), EpisodeAdapter.OnItemClickListener {
    private var searchedAnime: MutableList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val autoCompleteTextView = findViewById<View>(R.id.autoTextView) as AutoCompleteTextView
        val epTextView = findViewById<View>(R.id.recycler_view) as RecyclerView

        RxTextView.textChanges(autoCompleteTextView)
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                searchedAnime.clear()
                val adapter: ArrayAdapter<String> =
                    ArrayAdapter<String>(this, android.R.layout.select_dialog_item, searchedAnime)
                val names = Query().execute(autoCompleteTextView.editableText.toString())
                val animes: Array<String> = names.get()
                for (anime in animes) {
                    searchedAnime.add(anime.replace("-", " ").replace("/category/", ""))
                }
                autoCompleteTextView.setAdapter(adapter)
                autoCompleteTextView.postDelayed({
                    autoCompleteTextView.showDropDown()
                }, 10)
            }

        autoCompleteTextView.setOnItemClickListener { parent, arg1, pos, id ->
            val episode = Episode()
            val exampleList = generateEpisodesList(episode.execute(autoCompleteTextView.editableText.toString()).get().toInt(),autoCompleteTextView)
            val rAdapter = EpisodeAdapter(this,exampleList)

            epTextView.adapter = rAdapter
            epTextView.layoutManager = LinearLayoutManager(this)
            epTextView.setHasFixedSize(true)
            autoCompleteTextView.editableText.clear()
        }
    }

    private fun generateEpisodesList(size: Int, autoCompleteTextView:AutoCompleteTextView): ArrayList<Items> {

        val list = ArrayList<Items>()

        for (i in 1 until size +1) {
            val item = Items(R.drawable.ic_android_black_24dp, "Episode $i", autoCompleteTextView.editableText.toString())
            list += item
        }

        return list
    }

    override fun onItemClick(position: Int, exampleList:List<Items>) {

        val clickedItem = exampleList[position]
        Log.d("clicked", clickedItem.text2)

        val episode = clickedItem.text2.replace(" ", "-")+ "-" + clickedItem.text1.replace(" ", "-")
        val embedUrl = EmbedUrl().execute(episode).get()

        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra("video_link", embedUrl)
        startActivity(intent)


    }


}
