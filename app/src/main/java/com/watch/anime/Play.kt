package com.watch.anime

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri


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