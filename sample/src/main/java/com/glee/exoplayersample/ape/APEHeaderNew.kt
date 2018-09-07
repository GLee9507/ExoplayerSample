/*
 *  21.04.2004 Original verion. davagin@udm.ru.
 *-----------------------------------------------------------------------
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package com.glee.exoplayersample.ape

import com.glee.exoplayersample.ApeHeader
import com.google.android.exoplayer2.extractor.ExtractorInput

import java.io.EOFException
import java.io.IOException


/**
 * Author: Dmitry Vaguine
 * Date: 04.03.2004
 * Time: 14:51:31
 */
class APEHeaderNew {
    var nCompressionLevel: Int = 0        // the compression level (unsigned short)
    var nFormatFlags: Int = 0            // any format flags (for future use) (unsigned short)

    var nBlocksPerFrame: Long = 0        // the number of audio blocks in one frame (unsigned int)
    var nFinalFrameBlocks: Long = 0        // the number of audio blocks in the final frame (unsigned int)
    var nTotalFrames: Long = 0            // the total number of frames (unsigned int)

    var nBitsPerSample: Int = 0            // the bits per sample (typically 16) (unsigned short)
    var nChannels: Int = 0                // the number of channels (1 or 2) (unsigned short)
    var nSampleRate: Long = 0            // the sample rate (typically 44100) (unsigned int)

    companion object {

        const val APE_HEADER_BYTES = 24

        @Throws(IOException::class)
        fun read(input: ExtractorInput) = APEHeaderNew().apply {
            nCompressionLevel = input.readUnsignedShort()
            nFormatFlags = input.readUnsignedShort()
            nBlocksPerFrame = input.readUnsignedInt()
            nFinalFrameBlocks = input.readUnsignedInt()
            nTotalFrames = input.readUnsignedInt()
            nBitsPerSample = input.readUnsignedShort()
            nChannels = input.readUnsignedShort()
            nSampleRate = input.readUnsignedInt()
        }
    }


}
