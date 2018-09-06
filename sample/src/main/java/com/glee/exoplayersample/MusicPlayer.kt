package com.glee.exoplayersample

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
//import com.google.android.exoplayer2.ext.flac.FlacExtractor
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import okhttp3.Call
import okhttp3.OkHttpClient


class MusicPlayer constructor(private val applicationContext: Context) : Player {


    private val player: SimpleExoPlayer
    //    internal val handler: Handler
//    private val concatenatingMediaSource: ConcatenatingMediaSource
//    private val musicSourceList: MutableList<MusicSource>
    private var currentIndex: Int = -1
    private val playList: PlayList
    private val NET by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
                .build()
    }
    private val eventListener = object : com.google.android.exoplayer2.Player.DefaultEventListener() {
        override fun onPlayerError(error: ExoPlaybackException?) {
            super.onPlayerError(error)
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
            Log.d("glee9507", playWhenReady.toString() + "--" + playbackState)
        }
    }

    private val bandwidthMeter: DefaultBandwidthMeter = DefaultBandwidthMeter()

    init {
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
//        val renderersFactory = DefaultRenderersFactory(applicationContext, EXTENSION_RENDERER_MODE_ON)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(AudioOnlyRenderersFactory(applicationContext), trackSelector)
//        val playerHandlerThread = HandlerThread("player_thread")
//        playerHandlerThread.start()
//        handler = Handler(playerHandlerThread.looper)
        player.addListener(eventListener)
        playList = PlayList(applicationContext)
//        concatenatingMediaSource = ConcatenatingMediaSource()
//        musicSourceList = mutableListOf()
        player.prepare(playList.concatenatingMediaSource, true, true)
//        playList.prepare(player)


    }


    private val netDataSourceFactory by lazy {
        ExtractorMediaSource.Factory(OkHttpDataSourceFactory(
                Call.Factory {
                    NET.newCall(it)
                }, "exoplayer", bandwidthMeter))
                .setExtractorsFactory(AudioExtractorsFactory())
    }

    private val localDataSourceFactory by lazy {
        ExtractorMediaSource.Factory(
                DefaultDataSourceFactory(applicationContext, "exoplayer")
        ).setExtractorsFactory(AudioExtractorsFactory())

    }


    override fun pause() {

        player.playWhenReady = false
    }


    override fun play() {
        player.playWhenReady = true
    }

    override fun play(index: Int) {
        player.seekTo(index, 0)
        currentIndex = index
        player.playWhenReady = true
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    override fun next() {
        player.seekTo(++currentIndex, 0)
    }

    override fun pre() {
        if (currentIndex > 0) {
            player.seekTo(--currentIndex, 0)
        }
    }

    override fun stop() {
        player.stop()
    }

    override fun release() {
        player.release()
        clearMusicSource()
    }

    override fun setMusicSource(musicSources: Collection<MusicSource>) {
        clearMusicSource()
        playList.add(musicSources)
    }

    override fun removeMusicSource(musicSources: Collection<MusicSource>) {
    }

    override fun removeMusicSource(index: Int) {
    }

    override fun addMusicSource(index: Int, musicSources: Collection<MusicSource>) {
        playList.add(index, musicSources)
    }

    override fun addMusicSource(musicSources: Collection<MusicSource>) {
        playList.add(musicSources)
    }

    override fun clearMusicSource() {
        playList.clear()
    }
}


