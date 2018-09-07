package com.glee.exoplayersample

import com.glee.exoplayersample.ape.APEFileInfo
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegDecoder
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegDecoderException
import com.google.android.exoplayer2.extractor.*
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import java.io.IOException
import java.nio.charset.Charset


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
    var apeFileInfo: APEFileInfo? = null

    @Throws(IOException::class, InterruptedException::class)
    override fun sniff(input: ExtractorInput): Boolean {
        val array = ByteArray(4)
        input.peekFully(array, 0, 4, true)
        val isApe = String(array, Charset.forName("US-ASCII")).trim() == "MAC"
        if (isApe) {
            apeFileInfo = APEFileInfo.read(input)
        }
        return isApe
    }

    override fun init(output: ExtractorOutput) {
//        val mediaFormat = Format.createAudioSampleFormat(null,
//                MimeTypes.AUDIO_RAW, null,
//                streamInfo.bitRate(),
//                streamInfo.maxDecodedFrameSize(),
//                streamInfo.channels,
//                streamInfo.sampleRate,
//                getPcmEncoding(streamInfo.bitsPerSample),
//                /* encoderDelay= */ 0,
//                /* encoderPadding= */ 0, null, null,
//                /* selectionFlags= */ 0, null,
//                if (isId3MetadataDisabled) null else id3Metadata)/* id= *//* codecs= *//* initializationData= *//* drmInitData= *//* language= */

        this.output = output
        track = output.track(0, C.TRACK_TYPE_AUDIO)
        apeFileInfo?.let {
            track.format(Format.createAudioSampleFormat(
                    null,
                    MimeTypes.AUDIO_RAW,
                    null,
                    it.nDecompressedBitrate,
                    it.nTotalFrames,
                    it.nChannels,
                    it.nSampleRate,
                    Util.getPcmEncoding(it.nBitsPerSample),
                    null, null, 0, null
            ))
        }
        output.endTracks()
        try {
            decoder = FfmpegDecoder(
                    NUM_BUFFERS,
                    NUM_BUFFERS,
                    INITIAL_INPUT_BUFFER_SIZE,
                    MimeTypes.AUDIO_APE,
                    null,
                    true
            )
        } catch (e: FfmpegDecoderException) {
            e.printStackTrace()
            throw e
        }
    }


    @Throws(IOException::class, InterruptedException::class)
    override fun read(input: ExtractorInput, seekPosition: PositionHolder): Int {

//        val byteArray = ByteArray(4)
//        input.read(byteArray, 0, 4)
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


    override fun seek(position: Long, timeUs: Long) {

    }

    override fun release() {

    }
}