package com.glee.exoplayersample.ape

import com.google.android.exoplayer2.extractor.ExtractorInput

object APEInfoReader {

    fun read(input: ExtractorInput, version: Int) {
        val apeFileInfo = APEFileInfo()
        if (version >= 3980) {
            // current header format
            readNew(input,apeFileInfo)
        } else {
            // legacy support
            readOld(input,apeFileInfo)
        }
    }

    private fun readNew(input: ExtractorInput, apeFileInfo: APEFileInfo) {
//        input.read()
    }

    private fun readOld(input: ExtractorInput, apeFileInfo: APEFileInfo) {
    }

}