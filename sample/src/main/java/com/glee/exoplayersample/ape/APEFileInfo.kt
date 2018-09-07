package com.glee.exoplayersample.ape

import com.google.android.exoplayer2.extractor.ExtractorInput
import java.nio.charset.Charset

class APEFileInfo {
    var nVersion: Int = 0                // file version number * 1000 (3.93 = 3930)
    var nCompressionLevel: Int = 0       // the compression level
    var nFormatFlags: Int = 0            // format flags
    var nTotalFrames: Int = 0            // the total number frames (frames are used internally)
    var nBlocksPerFrame: Int = 0         // the samples in a frame (frames are used internally)
    var nFinalFrameBlocks: Int = 0        // the number of samples in the final frame
    var nChannels: Int = 0                // audio channels
    var nSampleRate: Int = 0             // audio samples per second
    var nBitsPerSample: Int = 0          // audio bits per sample
    var nBytesPerSample: Int = 0         // audio bytes per sample
    var nBlockAlign: Int = 0             // audio block align (channels * bytes per sample)
    var nWAVHeaderBytes: Int = 0         // header bytes of the original WAV
    var nWAVDataBytes: Int = 0           // data bytes of the original WAV
    var nWAVTerminatingBytes: Int = 0    // terminating bytes of the original WAV
    var nWAVTotalBytes: Int = 0          // total bytes of the original WAV
    var nAPETotalBytes: Int = 0          // total bytes of the APE file
    var nTotalBlocks: Int = 0            // the total number audio blocks
    var nLengthMS: Int = 0               // the length in milliseconds
    var nAverageBitrate: Int = 0         // the kbps (i.e. 637 kpbs)
    var nDecompressedBitrate: Int = 0    // the kbps of the decompressed audio (i.e. 1440 kpbs for CD audio)
    var nPeakLevel: Int = 0                // the peak audio level (-1 if unknown)

    var nJunkHeaderBytes: Int = 0        // used for ID3v2, etc.
    var nSeekTableElements: Int = 0        // the number of elements in the seek table(s)

    var spSeekByteTable: IntArray? = null      // the seek table (byte)
    var spSeekBitTable: ByteArray? = null      // the seek table (bits -- legacy)
    var spWaveHeaderData: ByteArray? = null        // the pre-audio header data
    var spAPEDescriptor = APEDescriptor()     // the descriptor (only with newer files)

    companion object {
        const val MAC_FORMAT_FLAG_CREATE_WAV_HEADER = 32
        const val WAVE_HEADER_BYTES = 44
        internal fun read(input: ExtractorInput) =
                with(input) {
                    val apeFileInfo = APEFileInfo()
                    input.skip(4)
                    apeFileInfo.nVersion = input.readUnsignedShort()
                    if (apeFileInfo.nVersion >= 3890) {
                        val apeDescriptor = apeFileInfo.spAPEDescriptor
//                        apeFileInfo.spAPEDescriptor = apeDescriptor
                        apeDescriptor.nVersion = apeFileInfo.nVersion
                        apeDescriptor.cID = "MAC "
                        input.skip(2)
                        apeDescriptor.nDescriptorBytes = readUnsignedInt()
                        apeDescriptor.nHeaderBytes = readUnsignedInt()
                        apeDescriptor.nSeekTableBytes = readUnsignedInt()
                        apeDescriptor.nHeaderDataBytes = readUnsignedInt()
                        apeDescriptor.nAPEFrameDataBytes = readUnsignedInt()
                        apeDescriptor.nAPEFrameDataBytesHigh = readUnsignedInt()
                        apeDescriptor.nTerminatingDataBytes = readUnsignedInt()
                        apeDescriptor.cFileMD5 = ByteArray(16).apply {
                            readFully(this, 0, 16)
                        }

                        if ((apeFileInfo.spAPEDescriptor.nDescriptorBytes - APEDescriptor.APE_DESCRIPTOR_BYTES) > 0) {
                            skip((apeFileInfo.spAPEDescriptor.nDescriptorBytes - APEDescriptor.APE_DESCRIPTOR_BYTES).toInt())
                        }

                        val apeHeaderNew = APEHeaderNew.read(input)
                        if ((apeFileInfo.spAPEDescriptor.nHeaderBytes - APEHeaderNew.APE_HEADER_BYTES) > 0) {
                            input.skip((apeFileInfo.spAPEDescriptor.nHeaderBytes - APEHeaderNew.APE_HEADER_BYTES).toInt())
                        }
                        // fill the APE info structure
                        apeFileInfo.nVersion = apeFileInfo.spAPEDescriptor.nVersion
                        apeFileInfo.nCompressionLevel = apeHeaderNew.nCompressionLevel
                        apeFileInfo.nFormatFlags = apeHeaderNew.nFormatFlags
                        apeFileInfo.nTotalFrames = apeHeaderNew.nTotalFrames.toInt()
                        apeFileInfo.nFinalFrameBlocks = apeHeaderNew.nFinalFrameBlocks.toInt()
                        apeFileInfo.nBlocksPerFrame = apeHeaderNew.nBlocksPerFrame.toInt()
                        apeFileInfo.nChannels = apeHeaderNew.nChannels
                        apeFileInfo.nSampleRate = apeHeaderNew.nSampleRate.toInt()
                        apeFileInfo.nBitsPerSample = apeHeaderNew.nBitsPerSample
                        apeFileInfo.nBytesPerSample = apeFileInfo.nBitsPerSample / 8
                        apeFileInfo.nBlockAlign = apeFileInfo.nBytesPerSample * apeFileInfo.nChannels
                        apeFileInfo.nTotalBlocks = (if (apeHeaderNew.nTotalFrames == 0.toLong()) 0 else (apeHeaderNew.nTotalFrames - 1) * apeFileInfo.nBlocksPerFrame + apeHeaderNew.nFinalFrameBlocks).toInt()
                        apeFileInfo.nWAVHeaderBytes = if (apeHeaderNew.nFormatFlags and MAC_FORMAT_FLAG_CREATE_WAV_HEADER > 0) WAVE_HEADER_BYTES else apeFileInfo.spAPEDescriptor.nHeaderDataBytes.toInt()
                        apeFileInfo.nWAVTerminatingBytes = apeFileInfo.spAPEDescriptor.nTerminatingDataBytes.toInt()
                        apeFileInfo.nWAVDataBytes = apeFileInfo.nTotalBlocks * apeFileInfo.nBlockAlign
                        apeFileInfo.nWAVTotalBytes = apeFileInfo.nWAVDataBytes + apeFileInfo.nWAVHeaderBytes + apeFileInfo.nWAVTerminatingBytes
                        apeFileInfo.nAPETotalBytes = input.length.toInt()
                        apeFileInfo.nLengthMS = (apeFileInfo.nTotalBlocks * 1000L / apeFileInfo.nSampleRate).toInt()
                        apeFileInfo.nAverageBitrate = if (apeFileInfo.nLengthMS <= 0) 0 else (apeFileInfo.nAPETotalBytes * 8L / apeFileInfo.nLengthMS).toInt()
                        apeFileInfo.nDecompressedBitrate = apeFileInfo.nBlockAlign * apeFileInfo.nSampleRate * 8 / 1000
                        apeFileInfo.nSeekTableElements = (apeFileInfo.spAPEDescriptor.nSeekTableBytes / 4).toInt()
                        apeFileInfo.nPeakLevel = -1


                        // get the seek tables (really no reason to get the whole thing if there's extra)
                        apeFileInfo.spSeekByteTable = IntArray(apeFileInfo.nSeekTableElements)
                        for (i in 0 until apeFileInfo.nSeekTableElements)
                            apeFileInfo.spSeekByteTable!![i] = input.readInt()

                        // get the wave header
                        if (apeHeaderNew.nFormatFlags and MAC_FORMAT_FLAG_CREATE_WAV_HEADER <= 0) {
                            if (apeFileInfo.nWAVHeaderBytes > Integer.MAX_VALUE) {
                                //error
                            }
                            apeFileInfo.spWaveHeaderData = ByteArray(apeFileInfo.nWAVHeaderBytes)

                            input.readFully(apeFileInfo.spWaveHeaderData, 0, apeFileInfo.nWAVHeaderBytes)
                        }

                    } else {

                    }
                    apeFileInfo
                }

    }
}