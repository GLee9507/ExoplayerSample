package com.glee.exoplayersample;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;

import java.io.IOException;

class FlacExtractor implements Extractor {

    /**
     * Returns whether this extractor can extract samples from the {@link ExtractorInput}, which must
     * provide data from the start of the stream.
     * <p>
     * If {@code true} is returned, the {@code input}'s reading position may have been modified.
     * Otherwise, only its peek position may have been modified.
     *
     * @param input The {@link ExtractorInput} from which data should be peeked/read.
     * @return Whether this extractor can read the provided input.
     * @throws IOException          If an error occurred reading from the input.
     * @throws InterruptedException If the thread was interrupted.
     */
    @Override
    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        return false;
    }

    @Override
    public void init(ExtractorOutput output) {

    }

    @Override
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void seek(long position, long timeUs) {

    }

    @Override
    public void release() {

    }
}