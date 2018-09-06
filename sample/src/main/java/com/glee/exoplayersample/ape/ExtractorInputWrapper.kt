package com.glee.exoplayersample.ape

import com.google.android.exoplayer2.extractor.ExtractorInput
import java.io.IOException
import java.nio.charset.Charset
import kotlin.experimental.and
import kotlin.experimental.or

class ExtractorInputWrapper private constructor(private val input: ExtractorInput) {
    companion object {
        fun wrap(input: ExtractorInput) = ExtractorInputWrapper(input)
    }


    @Throws(IOException::class, InterruptedException::class)
    fun readString(size: Int, charset: Charset): String {
        val byteArray = ByteArray(size)
        input.read(byteArray, 0, size)
        return String(byteArray, charset)
    }

    @Throws(IOException::class, InterruptedException::class)
    fun readInt(): Int {
        val byteArray = ByteArray(4)
        input.read(byteArray, 0, 4)
        return (((byteArray[0].toInt() and 0xff).toLong()) or
                ((byteArray[1].toInt() and 0xff).toLong() shl 8) or
                ((byteArray[2].toInt() and 0xff).toLong() shl 16) or
                ((byteArray[3].toInt() and 0xff).toLong() shl 24)).toInt()

    }

    @Throws(IOException::class, InterruptedException::class)
    fun readShort(): Short {
        val byteArray = ByteArray(2)
        input.read(byteArray, 0, 2)
        return (byteArray[1].toInt() shl 8 or (byteArray[0].toInt() and 0xff)).toShort()
    }

    @Throws(IOException::class, InterruptedException::class)
    fun readUnsignedShort(): Int {
        val byteArray = ByteArray(2)
        input.read(byteArray, 0, 2)
        return ((byteArray[0].toInt() and 0xff) or (byteArray[1].toInt() and 0xff shl 8))
    }

}

