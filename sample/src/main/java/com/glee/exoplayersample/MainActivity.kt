package com.glee.exoplayersample

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegLibrary
import com.google.android.exoplayer2.extractor.Extractor
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor
import com.google.android.exoplayer2.extractor.wav.WavExtractor
import com.google.android.exoplayer2.util.MimeTypes
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val musicPlayer = MusicPlayer(applicationContext)
        val path = Environment.getExternalStorageDirectory().canonicalPath + "/mc/Kalimba."
//        val path = "android:resource://com.glee.exoplayersample/"

        musicPlayer.setMusicSource(
//                {
//                    val locales = assets.locales
//                    val list = mutableListOf<Music>()
//                    for (locale in locales) {
//                        list.add(Music(locale))
//                    }
//                    list
//                }.invoke()
//                Music(path+"mp3"),
//                Music(path+"aac"),
//                Music(path+"ac3"),
                //no support
//                Music(path+"aiff"),
//                Music(path+"amr"),
                //no support
//                Music(path+"au"),
//                Music(path+"m4a"),
//                Music(path+"mka"),
//                Music(path+"mp2"),
//                Music(path+"ogg"),
                //no support
//                Music(path+"ra"),
//                Music(path+"wav")
                //no support
//                Music(path+"wma")
                Music(path+"flac")
//                Music(path + "ape")


        )
Log.d("glee9507",FfmpegLibrary.getVersion())
        Log.d("glee9507", FfmpegLibrary.supportsFormat(MimeTypes.AUDIO_FLAC).toString() + "--" + FfmpegLibrary.ffmpegHasDecoder ("ape"))
        play.setOnClickListener {
            musicPlayer.play(0)
        }
        next.setOnClickListener {
            musicPlayer.next()
        }
        pre.setOnClickListener {
            musicPlayer.pre()
        }
    }
}


data class Music(override val uri: String) : MusicSource