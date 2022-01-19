package com.watch.anime

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.ProgressBar
import java.io.IOException


class VideoActivity : Activity() {
    private var surfaceView: SurfaceView? = null
    private var player: MediaPlayer? = null
    private var holder: SurfaceHolder? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videoview)
        surfaceView = findViewById<View>(R.id.surfaceView) as SurfaceView
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        val extras = intent.extras
        val url = extras?.getString("video_link");

        player = MediaPlayer()
        try {
            player!!.setDataSource(this, Uri.parse(url))
            surfaceView!!.holder.addCallback(MyCallBack())
            player!!.prepare()
            player!!.setOnPreparedListener {
                progressBar!!.visibility = View.INVISIBLE
                player!!.start()
                player!!.isLooping = true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private inner class MyCallBack : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            player!!.setDisplay(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder) {}
    }
}


