package com.watch.anime

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class Episode() : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg anime: String?): String {

        val search = anime[0]?.replace(" ", "-")
        val doc: Document = Jsoup.connect("https://gogoanime.film/category/$search").get()
        val episodes = doc.getElementsByClass("active").select("a").attr("ep_end")

        Log.d("------------Episodes-----------", episodes.toString())


        return episodes
    }


    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        // ...
    }
}