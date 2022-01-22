package com.watch.anime

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.FileOutputStream


class Episode() : AsyncTask<String, Void, Array<String>>() {
    override fun doInBackground(vararg anime: String?): Array<String> {

        val search = anime[0]?.replace(" ", "-")
        val doc: Document = Jsoup.connect("https://gogoanime.film/category/$search").get()
        val episodes = doc.getElementsByClass("active").select("a").attr("ep_end")
        val imageUrl = doc.getElementsByClass("anime_info_body_bg").select("img").attr("src")






        val episodeAndImage = arrayOf(episodes, imageUrl)   //implicit type declaration


            Log.d("------------Episodes-----------", episodes.toString())


            return episodeAndImage

    }


    override fun onPostExecute(result: Array<String>) {
        super.onPostExecute(result)
        // ...
    }
}