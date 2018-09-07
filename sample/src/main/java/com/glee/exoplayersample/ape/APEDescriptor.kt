package com.glee.exoplayersample.ape

class APEDescriptor {
    var cID: String? = null                    // should equal 'MAC ' (char[4])
    var nVersion: Int = 0                // version number * 1000 (3.81 = 3810) (unsigned short)

    var nDescriptorBytes: Long = 0        // the number of descriptor bytes (allows later expansion of this header) (unsigned int32)
    var nHeaderBytes: Long = 0            // the number of header APE_HEADER bytes (unsigned int32)
    var nSeekTableBytes: Long = 0        // the number of bytes of the seek table (unsigned int32)
    var nHeaderDataBytes: Long = 0        // the number of header data bytes (from original file) (unsigned int32)
    var nAPEFrameDataBytes: Long = 0        // the number of bytes of APE frame data (unsigned int32)
    var nAPEFrameDataBytesHigh: Long = 0    // the high order number of APE frame data bytes (unsigned int32)
    var nTerminatingDataBytes: Long = 0    // the terminating data of the file (not including tag data) (unsigned int32)

    var cFileMD5: ByteArray? = null // the MD5 hash of the file (see notes for usage... it's a littly tricky) (unsigned char[16])

    companion object {

        const val APE_DESCRIPTOR_BYTES = 52
    }
}