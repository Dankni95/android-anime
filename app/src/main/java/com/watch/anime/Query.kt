package com.watch.anime

import android.os.AsyncTask
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class Query() : AsyncTask<String, Void, Array<String>>() {
    override fun doInBackground(vararg anime: String?): Array<String> {
            val search = anime[0]?.replace(" ", "-")
            val searchedAnime: MutableList<String> = ArrayList()

            val doc: Document = Jsoup.connect("https://gogoanime.film/search.html?keyword=$search").get()
            val animes = doc.getElementsByClass("name")

            for (name in animes) {
                searchedAnime.add(name.select("a[href]").attr("href").toString())
            }
            return searchedAnime.toTypedArray()
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: Array<String>?) {
        super.onPostExecute(result)
        // ...
    }
}