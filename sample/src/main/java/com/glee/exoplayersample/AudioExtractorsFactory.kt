package com.glee.exoplayersample

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.google.android.exoplayer2.ext.flac.FlacExtractor
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.extractor.amr.AmrExtractor
import com.google.android.exoplayer2.extractor.flv.FlvExtractor
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor
import com.google.android.exoplayer2.extractor.ogg.OggExtractor
import com.google.android.exoplayer2.extractor.ts.*
import com.google.android.exoplayer2.extractor.wav.WavExtractor

/**
 * An [ExtractorsFactory] that provides an array of extractors for the following formats:
 *
 *
 *  * MP4, including M4A ([Mp4Extractor])
 *  * fMP4 ([FragmentedMp4Extractor])
 *  * Matroska and WebM ([MatroskaExtractor])
 *  * Ogg Vorbis/FLAC ([OggExtractor]
 *  * MP3 ([Mp3Extractor])
 *  * AAC ([AdtsExtractor])
 *  * MPEG TS ([TsExtractor])
 *  * MPEG PS ([PsExtractor])
 *  * FLV ([FlvExtractor])
 *  * WAV ([WavExtractor])
 *  * AC3 ([Ac3Extractor])
 *  * AMR ([AmrExtractor])
 *  * FLAC (only available if the FLAC extension is built and included)
 *
 */
class AudioExtractorsFactory : ExtractorsFactory {

//    @MatroskaExtractor.Flags
//    private var matroskaFlags: Int = 0
//    @Mp4Extractor.Flags
//    private var mp4Flags: Int = 0
//    @FragmentedMp4Extractor.Flags
//    private var fragmentedMp4Flags: Int = 0
    @Mp3Extractor.Flags
    private var mp3Flags: Int = 0
    @TsExtractor.Mode
    private var tsMode: Int = 0
    @DefaultTsPayloadReaderFactory.Flags
    private var tsFlags: Int = 0

    init {
        tsMode = TsExtractor.MODE_SINGLE_PMT
    }

    /**
     * Sets flags for [MatroskaExtractor] instances created by the factory.
     *
     * @see MatroskaExtractor.MatroskaExtractor
     * @param flags The flags to use.
     * @return The factory, for convenience.
     */
//    @Synchronized
//    fun setMatroskaExtractorFlags(
//            @MatroskaExtractor.Flags flags: Int) = apply {
//        this.matroskaFlags = flags
//    }

    /**
     * Sets flags for [Mp4Extractor] instances created by the factory.
     *
     * @see Mp4Extractor.Mp4Extractor
     * @param flags The flags to use.
     * @return The factory, for convenience.
     */
//    @Synchronized
//    fun setMp4ExtractorFlags(@Mp4Extractor.Flags flags: Int)=apply {
//        this.mp4Flags = flags
//    }

    /**
     * Sets flags for [FragmentedMp4Extractor] instances created by the factory.
     *
     * @see FragmentedMp4Extractor.FragmentedMp4Extractor
     * @param flags The flags to use.
     * @return The factory, for convenience.
     */
//    @Synchronized
//    fun setFragmentedMp4ExtractorFlags(
//            @FragmentedMp4Extractor.Flags flags: Int)=apply {
//        this.fragmentedMp4Flags = flags
//    }

    /**
     * Sets flags for [Mp3Extractor] instances created by the factory.
     *
     * @see Mp3Extractor.Mp3Extractor
     * @param flags The flags to use.
     * @return The factory, for convenience.
     */
    @Synchronized
    fun setMp3ExtractorFlags(@Mp3Extractor.Flags flags: Int)=apply {
        mp3Flags = flags
    }

    /**
     * Sets the mode for [TsExtractor] instances created by the factory.
     *
     * @see TsExtractor.TsExtractor
     * @param mode The mode to use.
     * @return The factory, for convenience.
     */
    @Synchronized
    fun setTsExtractorMode(@TsExtractor.Mode mode: Int)=apply {
        tsMode = mode
    }

    /**
     * Sets flags for [DefaultTsPayloadReaderFactory]s used by [TsExtractor] instances
     * created by the factory.
     *
     * @see TsExtractor.TsExtractor
     * @param flags The flags to use.
     * @return The factory, for convenience.
     */
    @Synchronized
    fun setTsExtractorFlags(@DefaultTsPayloadReaderFactory.Flags flags: Int) = apply {
        tsFlags = flags
    }

    @Synchronized
    override fun createExtractors() = arrayOf(
            Mp3Extractor(mp3Flags),
            Ac3Extractor(),
            OggExtractor(),
            WavExtractor(),
            AmrExtractor(),
            FlacExtractor(),
            ApeExtractor()
    )


}
