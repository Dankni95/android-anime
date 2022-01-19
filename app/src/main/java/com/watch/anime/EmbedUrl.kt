package com.watch.anime

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.bind.DatatypeConverter


class EmbedUrl() : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg ep: String): String {
        val episode = ep[0]
        Log.d("url",episode)

        val doc: Document = Jsoup.connect("https://gogoanime.film/${episode}").get()
        val link =doc.getElementsByClass("play-video").select("iframe").attr("src")
        Log.d("------------link-----------", link.toString())

        val docu: Document = Jsoup.connect("https:${link}").get()

        val location = docu.location()
        val id = location.replace("https://gogoplay.io/streaming.php?id=", "").split("&")[0]

        val result = DatatypeConverter.printBase64Binary(Encrypt.encrypt(id.toByteArray()+"\n".toByteArray()))


        val urlParameters = "id=$result&time=69420691337800813569"
        val postData: ByteArray = urlParameters.toByteArray()
        val postDataLength = postData.size
        val request = "https://gogoplay.io/encrypt-ajax.php"
        val url = URL(request)
        val conn = url.openConnection() as HttpURLConnection

        conn.doOutput = true
        conn.instanceFollowRedirects = false
        conn.requestMethod = "POST"
        conn.addRequestProperty("X-Requested-With", "XMLHttpRequest")
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.setRequestProperty("charset", "utf-8")
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength))
        conn.useCaches = false

        DataOutputStream(conn.outputStream).use { wr -> wr.write(postData) }

        val data = conn.inputStream.bufferedReader().readText()
        val json = JSONObject(data)

        // TODO Selects hardcoded quality
        val urls = JSONObject(json.getJSONArray("source")[2].toString())

        Log.d("FILE", urls.getString("file"))


        return urls.getString("file").toString()


    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        // ...
    }



}