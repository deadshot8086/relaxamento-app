package com.rgssdeveloper.relaxamento.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.session.PlaybackState.*
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.rgssdeveloper.relaxamento.fragments.MusicPlayFragment

//we first start service, then bind it(not compulsory) for accessing service methods
class MediaPlayerService: Service(), MediaPlayer.OnPreparedListener {
    private val TAG by lazy { this::class.java.simpleName }
    private var mMediaPlayer: MediaPlayer? = null
    private var isPrepared:Boolean = false
    private lateinit var wifiManager:WifiManager
    private lateinit var wifiLock: WifiManager.WifiLock

//      binding turned off
//    private val binder = LocalBinder()
//    inner class LocalBinder : Binder() {
//        // Return this instance of LocalService so clients can call public methods
//        fun getService(): MediaPlayerService = this@MediaPlayerService
//    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
//        return binder
    }
    private fun initPlayer(url:String?){
        mMediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            setOnPreparedListener(this@MediaPlayerService)
            prepareAsync() // prepare async to not block main thread
            isLooping=true
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        }
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "wifi_lock")
    }
    private fun pause(){
        mMediaPlayer!!.pause()
//        wifiLock.release()
    }
    private fun resume(){
        mMediaPlayer!!.start()
//        wifiLock.acquire()
    }
//TODO: Cache it with proxy maybe?
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_PREPARE.toString() -> {
                val storage = FirebaseStorage.getInstance().reference;
                storage.child("${intent.getStringArrayExtra("URL")}.mp3").downloadUrl.addOnSuccessListener(){
                    val url=it.toString()
                    mMediaPlayer?.reset()
                    initPlayer(url)
                }
            }
            ACTION_PLAY.toString() -> {
                if(isPrepared && mMediaPlayer!!.isPlaying.not()){
                    resume()
                }
            }
            ACTION_PAUSE.toString() -> {
                if(isPrepared && mMediaPlayer!!.isPlaying ){
                    pause()
                }
            }
        }
        return START_STICKY
    }

    override fun onPrepared(p0: MediaPlayer?) {
        MusicPlayFragment.stopLoading()
//        MusicPlayFragment.resumeGif()
        p0!!.start()
        isPrepared=true
        wifiLock.acquire()
        //TODO: mobile data lock maybe?
    }

    override fun onDestroy() {
        super.onDestroy()
        isPrepared=false
        mMediaPlayer?.release()
        mMediaPlayer = null
        wifiLock.release()
    }
}