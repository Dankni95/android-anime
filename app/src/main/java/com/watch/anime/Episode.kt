package com.watch.anime

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class Episode() : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg anime: String?): String {

        val search = anime[0]?.replace(" ", "-")
        val doc: Document = Jsoup.connect("https://gogoanime.film/category/$search").get()

        return doc.getElementsByClass("active").select("a").attr("ep_end")
    }


    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        // ...
    }
}