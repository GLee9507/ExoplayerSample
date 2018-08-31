package com.glee.exoplayersample

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


    fun setMusicSource(vararg musicSources: MusicSource)
    fun removeMusicSource(vararg musicSources: MusicSource)
    fun removeMusicSource(index: Int)
    fun addMusicSource(index: Int, vararg musicSources: MusicSource)
    fun addMusicSource(vararg musicSources: MusicSource)
    fun clearMusicSource()
}

interface MusicSource {
    val uri: String
}

interface EventListener {
    fun onStateChange(state: Int)
    fun onProgressChange(progress: Long, duration: Long)
    fun onError()
}
