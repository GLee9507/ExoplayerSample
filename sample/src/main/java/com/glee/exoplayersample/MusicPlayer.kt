package com.glee.exoplayersample

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.util.Patterns
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException


class MusicPlayer(private val applicationContext: Context) : Player {


    private val player: SimpleExoPlayer
    private val handler: Handler
    private val concatenatingMediaSource: ConcatenatingMediaSource
    private val musicSourceList: MutableList<MusicSource>
    private var currentIndex: Int = -1
    private val eventListener = object : com.google.android.exoplayer2.Player.DefaultEventListener() {
        override fun onPlayerError(error: ExoPlaybackException?) {
            super.onPlayerError(error)
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)
        }
    }

    init {
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(applicationContext, trackSelector)
        val playerHandlerThread = HandlerThread("player_thread")
        handler = Handler(playerHandlerThread.looper)
        playerHandlerThread.start()
        player.addListener(eventListener)
        concatenatingMediaSource = ConcatenatingMediaSource()
        concatenatingMediaSource.addEventListener(handler, object : MediaSourceEventListener {
            override fun onMediaPeriodCreated(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
            }

            override fun onMediaPeriodReleased(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
            }

            override fun onLoadStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, loadEventInfo: MediaSourceEventListener.LoadEventInfo?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
            }

            override fun onLoadCompleted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, loadEventInfo: MediaSourceEventListener.LoadEventInfo?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
            }

            override fun onLoadCanceled(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, loadEventInfo: MediaSourceEventListener.LoadEventInfo?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
            }

            override fun onLoadError(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, loadEventInfo: MediaSourceEventListener.LoadEventInfo?, mediaLoadData: MediaSourceEventListener.MediaLoadData?, error: IOException?, wasCanceled: Boolean) {
            }

            override fun onReadingStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {
            }

            override fun onUpstreamDiscarded(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
            }

            override fun onDownstreamFormatChanged(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?, mediaLoadData: MediaSourceEventListener.MediaLoadData?) {
            }
        })
        musicSourceList = mutableListOf()
    }


    private val netDataSourceFactory by lazy {
        ExtractorMediaSource.Factory(OkHttpDataSourceFactory(
                Call.Factory {
                    NET.newCall(it)
                }, "exoplayer", object : TransferListener<DataSource> {
            override fun onTransferStart(source: DataSource?, dataSpec: DataSpec?) {
            }

            override fun onBytesTransferred(source: DataSource?, bytesTransferred: Int) {
            }

            override fun onTransferEnd(source: DataSource?) {
            }
        }))
    }

    private val localDataSourceFactory by lazy { ExtractorMediaSource.Factory(DefaultDataSourceFactory(applicationContext, "exoplayer")) }

    override fun play(index: Int) {
        player.prepare(concatenatingMediaSource)
        player.playWhenReady = true
    }

    override fun pause() {
        player.playWhenReady = false
    }

    override fun resumePlay() {
        player.playWhenReady = true
    }

    override fun next() {
    }

    override fun pre() {
    }

    override fun stop() {
        player.stop()
    }

    override fun release() {
        player.release()
    }


    override fun setMusicSource(vararg musicSources: MusicSource) {
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
        addMusicSource(concatenatingMediaSource.size - 1, musicSources.toList())
    }


    private fun addMusicSource(index: Int, musicSources: List<MusicSource>) {
        val list = mutableListOf<MediaSource>()
        for (musicSource in musicSources) {
            val uriStr = musicSource.getUri()
            val uri = Uri.parse(uriStr)
            //判断是否为网络Uri
            if (Patterns.WEB_URL.matcher(uriStr).matches()) {
                list.add(netDataSourceFactory.createMediaSource(uri))
            } else {
                list.add(localDataSourceFactory.createMediaSource(uri))
            }
        }
        concatenatingMediaSource.addMediaSources(index, list)
        musicSourceList.addAll(index, musicSources)
    }

    override fun clearMusicSource() {
        concatenatingMediaSource.clear()
        musicSourceList.clear()
    }


}


val NET by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    OkHttpClient.Builder()
            .build()
}