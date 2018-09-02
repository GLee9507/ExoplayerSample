package com.glee.exoplayersample

import android.content.Context
import android.support.v4.util.ArrayMap
import android.support.v4.util.ArraySet
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class PlayList constructor(private val context: Context) {
    public val concatenatingMediaSource = ConcatenatingMediaSource(false)
    private val playListSet = ArrayMap<String, MediaSource>()
    private val localDataSourceFactory by lazy {
        ExtractorMediaSource.Factory(DefaultDataSourceFactory(context, "exoplayer"))
    }

    fun add(musicSource: MusicSource) {
        add(concatenatingMediaSource.size, musicSource)
    }

    fun add(index: Int, musicSource: MusicSource) {
        if (playListSet[musicSource.uriStr] == null) {
            val mediaSource = localDataSourceFactory.createMediaSource(musicSource.getUri())
            playListSet[musicSource.uriStr] = mediaSource
            concatenatingMediaSource.addMediaSource(index, mediaSource)
        }
    }

    fun add(musicSources: Collection<MusicSource>) {
        add(concatenatingMediaSource.size, musicSources)
    }

    fun prepare(player: ExoPlayer) {
        concatenatingMediaSource.prepareSourceInternal(player,false)
    }

    fun add(index: Int, musicSources: Collection<MusicSource>) {
        val list = mutableListOf<MediaSource>()
        for (musicSource in musicSources) {
            if (playListSet[musicSource.uriStr] == null) {
                val mediaSource = localDataSourceFactory.createMediaSource(musicSource.getUri())
                playListSet[musicSource.uriStr] = mediaSource
                list.add(mediaSource)
            }
        }
        concatenatingMediaSource.addMediaSources(index, list)
    }

    fun clear() {
        concatenatingMediaSource.clear()
        playListSet.clear()
    }
}