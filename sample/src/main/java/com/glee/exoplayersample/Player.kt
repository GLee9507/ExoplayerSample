package com.glee.exoplayersample

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.time.Duration

interface Player {


    fun play()
    fun play(index: Int)
    fun pause()
    fun next()
    fun pre()
    fun stop()
    fun release()
    fun seekTo(position: Long)


    fun setMusicSource(musicSources: Collection<MusicSource>)
    fun removeMusicSource(musicSources: Collection<MusicSource>)
    fun removeMusicSource(index: Int)
    fun addMusicSource(index: Int, musicSources: Collection<MusicSource>)
    fun addMusicSource(musicSources: Collection<MusicSource>)
    fun clearMusicSource()
}

interface MusicSource {
    val uriStr: String
    fun getUri(): Uri = Uri.parse(uriStr)
}

interface EventListener {
    fun onStateChange(state: Int)
    fun onProgressChange(progress: Long, duration: Long)
    fun onError()
}
