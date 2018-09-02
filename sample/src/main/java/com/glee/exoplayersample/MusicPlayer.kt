package com.glee.exoplayersample

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Patterns
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor
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
    private val concatenatingMediaSource: ConcatenatingMediaSource
    private val musicSourceList: MutableList<MusicSource>
    private var currentIndex: Int = -1

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
        val renderersFactory = DefaultRenderersFactory(applicationContext, EXTENSION_RENDERER_MODE_ON)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector)
//        val playerHandlerThread = HandlerThread("player_thread")
//        playerHandlerThread.start()
//        handler = Handler(playerHandlerThread.looper)
        player.addListener(eventListener)
        concatenatingMediaSource = ConcatenatingMediaSource()
        musicSourceList = mutableListOf()
        player.prepare(concatenatingMediaSource, true, true)
    }


    private val netDataSourceFactory by lazy {
        ExtractorMediaSource.Factory(OkHttpDataSourceFactory(
                Call.Factory {
                    NET.newCall(it)
                }, "exoplayer", bandwidthMeter))
    }

    private val localDataSourceFactory by lazy {
        ExtractorMediaSource.Factory(DefaultDataSourceFactory(applicationContext, "exoplayer")).apply {
            setExtractorsFactory(FlacExtractor.FACTORY)
        }
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


    override fun setMusicSource(vararg musicSources: MusicSource) {
        clearMusicSource()
        addMusicSource(0, musicSources.toList())
    }

    fun setMusicSource(musicSources: List<MusicSource>) {
        clearMusicSource()
        addMusicSource(0, musicSources.toList())
    }


    override fun removeMusicSource(vararg musicSources: MusicSource) {
        for (musicSource in musicSources) {
            val i = musicSourceList.indexOf(musicSource)
            if (i > -1) {
                remove(i)
            }
        }
    }

    override fun removeMusicSource(index: Int) {
        if (concatenatingMediaSource.size > index) {
            remove(index)
        }
    }

    private fun remove(index: Int) {
        concatenatingMediaSource.removeMediaSource(index)
        musicSourceList.removeAt(index)
    }

    override fun addMusicSource(index: Int, vararg musicSources: MusicSource) {
        addMusicSource(index, musicSources.toList())
    }

    override fun addMusicSource(vararg musicSources: MusicSource) {
        addMusicSource(concatenatingMediaSource.size, musicSources.toList())
    }


    private fun addMusicSource(index: Int, musicSources: List<MusicSource>) {
        val list = mutableListOf<MediaSource>()
        for (musicSource in musicSources) {
            val uriStr = musicSource.uri
            val uri = Uri.parse(uriStr).toString()
            //判断是否为网络Uri
//            if (Patterns.WEB_URL.matcher(uriStr).matches()) {
//                list.add(netDataSourceFactory.createMediaSource(uri))
//            } else {
            list.add(localDataSourceFactory.createMediaSource(Uri.parse(uri)))
//            }
        }
        concatenatingMediaSource.addMediaSources(index, list)
        musicSourceList.addAll(index, musicSources)
    }

    override fun clearMusicSource() {
        concatenatingMediaSource.clear()
        musicSourceList.clear()
    }
}


