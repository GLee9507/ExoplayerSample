package com.glee.exoplayersample

class ApeHeader {
    var cID: String? = null                    // should equal 'MAC ' (char[4])

    var nDescriptorBytes: Long = 0        // the number of descriptor bytes (allows later expansion of this header) (unsigned int32)
    var nHeaderBytes: Long = 0            // the number of header APE_HEADER bytes (unsigned int32)
    var nSeekTableBytes: Long = 0        // the number of bytes of the seek table (unsigned int32)
    var nHeaderDataBytes: Long = 0        // the number of header data bytes (from original file) (unsigned int32)
    var nAPEFrameDataBytes: Long = 0        // the number of bytes of APE frame data (unsigned int32)
    var nAPEFrameDataBytesHigh: Long = 0    // the high order number of APE frame data bytes (unsigned int32)
    var nTerminatingDataBytes: Long = 0    // the terminating data of the file (not including tag data) (unsigned int32)

    var cFileMD5 = ByteArray(16) // the MD5 hash of the file (see notes for usage... it's a littly tricky) (unsigned char[16])

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
    //        var spAPEDescriptor: APEDescriptor? = null        // the descriptor (only with newer files)


}