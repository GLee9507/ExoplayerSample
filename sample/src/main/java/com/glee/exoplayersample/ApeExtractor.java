package com.glee.exoplayersample;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.Id3Peeker;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;

import java.io.IOException;
import java.util.Arrays;

class ApeExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() {

        @Override
        public Extractor[] createExtractors() {
            return new Extractor[] {new ApeExtractor()};
        }

    };
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

    private static final byte[] FLAC_SIGNATURE = {'f', 'L', 'a', 'C', 0, 0, 0, 0x22};
    private final boolean isId3MetadataDisabled = true;
    private final Id3Peeker id3Peeker;
    private Metadata id3Metadata;

    /**
     * Peeks ID3 tag data (if present) at the beginning of the input.
     *
     * @return The first ID3 tag decoded into a {@link Metadata} object. May be null if ID3 tag is not
     *     present in the input.
     */
    @Nullable
    private Metadata peekId3Data(ExtractorInput input) throws IOException, InterruptedException {
        input.resetPeekPosition();
        Id3Decoder.FramePredicate id3FramePredicate =
                isId3MetadataDisabled ? Id3Decoder.NO_FRAMES_PREDICATE : null;
        return id3Peeker.peekId3Data(input, id3FramePredicate);
    }
    private boolean peekFlacSignature(ExtractorInput input) throws IOException, InterruptedException {
        byte[] header = new byte[FLAC_SIGNATURE.length];
        input.peekFully(header, 0, FLAC_SIGNATURE.length);
        return Arrays.equals(header, FLAC_SIGNATURE);
    }
    @Override
    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        if (input.getPosition() == 0) {
            id3Metadata = peekId3Data(input);
        }
        boolean b = peekFlacSignature(input);
        return b;
    }

    public ApeExtractor() {
        id3Peeker = new Id3Peeker();
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