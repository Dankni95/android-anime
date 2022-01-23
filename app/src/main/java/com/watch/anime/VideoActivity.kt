package com.watch.anime

import android.app.Activity
import android.os.Bundle
import android.view.*
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import java.io.IOException


class VideoActivity : Activity() {
    private var surfaceView: PlayerView? = null
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videoview)
        surfaceView = findViewById<View>(R.id.surfaceView) as PlayerView
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        player = ExoPlayer.Builder(surfaceView!!.context).build()
        surfaceView!!.setPlayer(player)


        val extras = intent.extras
        val url = extras?.getString("video_link");

        val window: Window = getWindow()

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setNavigationBarColor(getResources().getColor(R.color.black));


        try {

            val mediaItem: MediaItem? = url?.let { MediaItem.fromUri(it) }

            if (mediaItem != null) {
                player!!.setMediaItem(mediaItem)
            }
            player!!.prepare()
            player!!.play()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



    override fun onStop() {
        super.onStop()
        player?.pause()
    }


    override fun onDestroy() {
        if (player != null) {
            player!!.stop()
            player!!.release()
            player = null
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        if (!player?.isPlaying()!!) {
            player!!.play()
        }
    }
}


