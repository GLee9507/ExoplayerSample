package com.glee.exoplayersample

interface Player {
    fun play(index: Int)
    fun pause()
    fun resumePlay()
    fun next()
    fun pre()
    fun stop()
    fun release()


    fun setMusicSource(vararg musicSources: MusicSource)
    fun removeMusicSource(vararg musicSources: MusicSource)
    fun removeMusicSource(index: Int)
    fun addMusicSource(index: Int, vararg musicSources: MusicSource)
    fun addMusicSource(vararg musicSources: MusicSource)
    fun clearMusicSource()
}

interface MusicSource {
    fun getUri(): String
}