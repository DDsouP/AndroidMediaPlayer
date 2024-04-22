package com.example.exoplayer

import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlayerController(
    private val activity: PlayerActivity,
    private val playerView: PlayerView
) : DefaultLifecycleObserver {
    // 视频播放器
    internal var player: SimpleExoPlayer? = null

    // 视频资源
    private val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://com.example.exoplayer/"+ R.raw.xiaoetest))

    // 初始化播放参数
    private var playWhenready = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    fun initializePlayer() {
        Log.d("isInitPlayerSuccess", "success")
        player = SimpleExoPlayer.Builder(activity)
            .build()
        player?.setMediaItem(mediaItem)
        player?.playWhenReady = playWhenready
        player?.seekTo(currentWindow, playbackPosition)
        playerView.player = player

        player?.prepare()
    }

    fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenready = this.playWhenReady
            release()
        }
        player = null
    }
}