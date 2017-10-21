package com.google.zxing.oned.rss.expanded.decoders;

final class BlockParsedResult {
    private final DecodedInformation decodedInformation;
    private final boolean finished;

    BlockParsedResult(DecodedInformation decodedInformation, boolean z) {
        this.finished = z;
        this.decodedInformation = decodedInformation;
    }

    BlockParsedResult(boolean z) {
        this(null, z);
    }

    final DecodedInformation getDecodedInformation() {
        return this.decodedInformation;
    }

    final boolean isFinished() {
        return this.finished;
    }
}
