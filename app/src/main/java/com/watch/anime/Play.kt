package com.watch.anime

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.AsyncTask


class Play : AsyncTask<String, Void, MediaPlayer>() {
    override fun doInBackground(vararg p0:String): MediaPlayer {

        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }

        return mediaPlayer
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: MediaPlayer) {
        super.onPostExecute(result)
        // ...
    }
}