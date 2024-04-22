package com.example.exoplayer

import android.net.Uri
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlayerController(
    private val activity: PlayerActivity,
    private val playerView: PlayerView,
    private val playbackStateListener: Player.Listener
) : DefaultLifecycleObserver {
    // 视频播放器
    internal var player: SimpleExoPlayer? = null

    // 视频资源
    private val mediaItem = listOf(
        MediaItem.fromUri(Uri.parse("android.resource://com.example.exoplayer/" + R.raw.xiaoetest)),
        MediaItem.fromUri(Uri.parse("android.resource://com.example.exoplayer/" + R.raw.wakeuptest))
    )

    // 初始化播放参数
    private var playWhenready = true
    private var currentWindow = 0
    private var currentIndex = 0
    private var playbackPosition = 0L
    fun initializePlayer() {
        Log.d("isInitPlayerSuccess", "success")
        val trackSelector = DefaultTrackSelector(activity).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        player = SimpleExoPlayer.Builder(activity)
            .setTrackSelector(trackSelector)
            .build()
        with(player){
            this?.addMediaItems(mediaItem)
            this?.playWhenReady = playWhenready
            this?.seekTo(currentWindow, playbackPosition)
            this?.addListener(playbackStateListener)
            playerView.player = this
        }
        player?.prepare()
    }

    fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenready = this.playWhenReady
            removeListener(playbackStateListener)
            release()
        }
        player = null
    }
}