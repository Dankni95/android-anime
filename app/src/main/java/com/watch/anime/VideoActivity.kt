package com.watch.anime

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.io.IOException
import java.util.concurrent.TimeUnit


class VideoActivity : Activity() {
    private var surfaceView: SurfaceView? = null
    private var player: MediaPlayer? = Play().execute("").get()
    private var progressBar: ProgressBar? = null
    var buttonPlay: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.videoview)
        surfaceView = findViewById<View>(R.id.surfaceView) as SurfaceView
        progressBar = findViewById<View>(R.id.progress) as ProgressBar
        buttonPlay = findViewById<View>(R.id.buttonPlay) as Button


        val extras = intent.extras
        val url = extras?.getString("video_link");
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        surfaceView!!.setZOrderOnTop(true)

        // TODO Read up on services, this needs to be moved

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val videoParams: ViewGroup.LayoutParams = surfaceView!!.getLayoutParams()
        videoParams.width = displayMetrics.widthPixels
        videoParams.height = displayMetrics.heightPixels

        hideSystemBars(true)


        try {
            player!!.setDataSource(this, Uri.parse(url))
            surfaceView!!.holder.addCallback(MyCallBack())
            player!!.prepare()
            player!!.setOnPreparedListener {
                progressBar!!.visibility = View.INVISIBLE
                player!!.setVolume(100F, 100F);
                player!!.start()
                player!!.isLooping = true

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        (surfaceView)!!.setOnClickListener {

            //TODO button is not visible :(
            buttonPlay!!.visibility = View.VISIBLE
            buttonPlay?.bringToFront()

            hideSystemBars(false)
            TimeUnit.SECONDS.sleep(3);
            hideSystemBars(true)


        }

        buttonPlay!!.setOnClickListener{
            playPause()
        }


    }
    fun playPause() {
        if (player?.isPlaying == true) {
            player?.start()
            buttonPlay?.setText("Pause")
        } else {
            player?.pause()
            buttonPlay?.setText("Play")
        }
    }

    fun hideSystemBars(toHide:Boolean) {
        if (toHide){
            val windowInsetsController =
                ViewCompat.getWindowInsetsController(window.decorView) ?: return
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // Hide both the status bar and the navigation bar
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        }else{
            val windowInsetsController =
                ViewCompat.getWindowInsetsController(window.decorView) ?: return
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // Hide both the status bar and the navigation bar
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    private inner class MyCallBack : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            player!!.setDisplay(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder) {}
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
            player!!.start()
        }
    }
}


