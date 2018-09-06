package com.glee.exoplayersample

import android.util.Log
import com.glee.exoplayersample.ape.ExtractorInputWrapper
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegDecoder
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegDecoderException
import com.google.android.exoplayer2.extractor.*

import com.google.android.exoplayer2.extractor.wav.WavExtractor
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.id3.Id3Decoder
import com.google.android.exoplayer2.util.MimeTypes

import java.io.IOException
import java.nio.charset.Charset
import java.util.Arrays


//class ApeHeader constructor(
//        var compressionLevel: Int = 0
//) {
//
//
//}

internal class ApeExtractor : Extractor {
    companion object {
        /**
         * The number of input and output buffers.
         */
        private const val NUM_BUFFERS = 16
        /**
         * The initial input buffer size. Input buffers are reallocated dynamically if this value is
         * insufficient.
         */
        private const val INITIAL_INPUT_BUFFER_SIZE = 960 * 6

        private const val TAG = "MAC"
    }

    private lateinit var output: ExtractorOutput
    private lateinit var track: TrackOutput
    private lateinit var decoder: FfmpegDecoder
    private var macVersion: Int = -1
    private var header: ApeHeader? = null

    @Throws(IOException::class, InterruptedException::class)
    override fun sniff(input: ExtractorInput): Boolean {
//        val tagAndVersion = ByteArray(6)
//        input.peekFully(tagAndVersion, 0, 6, true)
//        macVersion = ByteUtil.getShort(tagAndVersion, 4).toInt()
//        return TAG == String(tagAndVersion, 0, 4, Charset.forName("US-ASCII")).trim()
        val reader = ExtractorInputWrapper.wrap(input)
        val readString = reader.readString(4, Charset.forName("US-ASCII"))
        val readShort = reader.readShort()
        Log.d("gleeex",readString+readShort.toString())
       return true
    }

    override fun init(output: ExtractorOutput) {
        this.output = output
        track = output.track(0, C.TRACK_TYPE_AUDIO)
        output.endTracks()
//        try {
//            decoder = FfmpegDecoder(
//                    NUM_BUFFERS,
//                    NUM_BUFFERS,
//                    INITIAL_INPUT_BUFFER_SIZE,
//                    MimeTypes.AUDIO_APE,
//                    null,
//                    true
//            )
//        } catch (e: FfmpegDecoderException) {
//            e.printStackTrace()
//            throw e
//        }
    }

    @Throws(IOException::class, InterruptedException::class)
    override fun read(input: ExtractorInput, seekPosition: PositionHolder): Int {
        val byteArray = ByteArray(4)
        input.read(byteArray,0,4)
//        Log.d("gleeex1",input.length.toString())
//        Log.d("gleeex2",input.peekPosition.toString())
//        Log.d("gleeex3",input.peekPosition.toString())
//        if (header == null) {
//            header = readHeader(input)
//        }
//        track.format(Format.createAudioSampleFormat(
//                null,
//                MimeTypes.AUDIO_RAW,
//                null,
//
//                ))
//        track.format()
//        decoder.queueInputBuffer()
        return 0
    }

    private fun readHeader(input: ExtractorInput): ApeHeader {
        val apeHeader = ApeHeader()
        if (macVersion < 3980) {//new

        } else {//old

        }
        return apeHeader
    }

    override fun seek(position: Long, timeUs: Long) {

    }

    override fun release() {

    }
}