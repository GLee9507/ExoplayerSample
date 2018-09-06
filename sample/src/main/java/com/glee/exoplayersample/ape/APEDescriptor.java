package com.glee.exoplayersample.ape;

public class APEDescriptor {
    public String cID;					// should equal 'MAC ' (char[4])
    public int nVersion;				// version number * 1000 (3.81 = 3810) (unsigned short)

    public long nDescriptorBytes;		// the number of descriptor bytes (allows later expansion of this header) (unsigned int32)
    public long nHeaderBytes;			// the number of header APE_HEADER bytes (unsigned int32)
    public long nSeekTableBytes;		// the number of bytes of the seek table (unsigned int32)
    public long nHeaderDataBytes;		// the number of header data bytes (from original file) (unsigned int32)
    public long nAPEFrameDataBytes;		// the number of bytes of APE frame data (unsigned int32)
    public long nAPEFrameDataBytesHigh;	// the high order number of APE frame data bytes (unsigned int32)
    public long nTerminatingDataBytes;	// the terminating data of the file (not including tag data) (unsigned int32)

    public byte[] cFileMD5 = new byte[16]; // the MD5 hash of the file (see notes for usage... it's a littly tricky) (unsigned char[16])

    public final static int APE_DESCRIPTOR_BYTES = 52;
}