package com.glee.exoplayersample.ape

import com.google.android.exoplayer2.extractor.ExtractorInput
import java.io.IOException
import java.nio.charset.Charset
import kotlin.experimental.and


@Throws(IOException::class, InterruptedException::class)
fun ExtractorInput.readString(size: Int, charset: Charset): String {
    val byteArray = ByteArray(size)
    read(byteArray, 0, size)
    return String(byteArray, charset)
}

@Throws(IOException::class, InterruptedException::class)
fun ExtractorInput.readInt(): Int {
    val byteArray = ByteArray(4)
    read(byteArray, 0, 4)
    return (((byteArray[0].toInt() and 0xff).toLong()) or
            ((byteArray[1].toInt() and 0xff).toLong() shl 8) or
            ((byteArray[2].toInt() and 0xff).toLong() shl 16) or
            ((byteArray[3].toInt() and 0xff).toLong() shl 24)).toInt()
}

@Throws(IOException::class, InterruptedException::class)
fun ExtractorInput.readShort(): Short {
    val byteArray = ByteArray(2)
    read(byteArray, 0, 2)
    return (byteArray[1].toInt() shl 8 or (byteArray[0].toInt() and 0xff)).toShort()
}

@Throws(IOException::class, InterruptedException::class)
fun ExtractorInput.readUnsignedShort(): Int {
    val byteArray = ByteArray(2)
    read(byteArray, 0, 2)
    return ((byteArray[0].toInt() and 0xff) or (byteArray[1].toInt() and 0xff shl 8))
}

@Throws(IOException::class, InterruptedException::class)
fun ExtractorInput.readUnsignedShort(byteArray: ByteArray): Int {
//    val byteArray = ByteArray(2)
    read(byteArray, 0, 2)
    return ((byteArray[0].toInt() and 0xff) or (byteArray[1].toInt() and 0xff shl 8))
}


//fun ExtractorInput.readUnsignedInt(): Long {
//    val byteArray = ByteArray(2)
//    read(byteArray, 0, 2)
//    return ((byteArray[0].toInt() and 0xff) or (byteArray[1].toInt() and 0xff shl 8))
//}
@Throws(IOException::class, InterruptedException::class)
fun ExtractorInput.readUnsignedInt(): Long {
    val array = ByteArray(4)
    read(array, 0, 4)
    return (array[0].toInt() and 0xff).toLong() or ((array[1].toInt() and 0xff).toLong() shl 8) or ((array[2].toInt() and 0xff).toLong() shl 16) or ((array[3].toInt() and 0xff).toLong() shl 24)
}
