package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class EAN13Writer extends UPCEANWriter {
    private static final int CODE_WIDTH = 95;

    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_13) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_13, but got " + barcodeFormat);
    }

    public final boolean[] encode(String str) {
        if (str.length() != 13) {
            throw new IllegalArgumentException("Requested contents should be 13 digits long, but got " + str.length());
        }
        try {
            if (UPCEANReader.checkStandardUPCEANChecksum(str)) {
                int parseInt;
                int i = EAN13Reader.FIRST_DIGIT_ENCODINGS[Integer.parseInt(str.substring(0, 1))];
                boolean[] zArr = new boolean[95];
                int appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, UPCEANReader.START_END_PATTERN, true) + 0;
                int i2 = 1;
                while (i2 <= 6) {
                    parseInt = Integer.parseInt(str.substring(i2, i2 + 1));
                    if (((i >> (6 - i2)) & 1) == 1) {
                        parseInt += 10;
                    }
                    i2++;
                    appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.L_AND_G_PATTERNS[parseInt], false) + appendPattern;
                }
                parseInt = OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.MIDDLE_PATTERN, false) + appendPattern;
                for (appendPattern = 7; appendPattern <= 12; appendPattern++) {
                    parseInt += OneDimensionalCodeWriter.appendPattern(zArr, parseInt, UPCEANReader.L_PATTERNS[Integer.parseInt(str.substring(appendPattern, appendPattern + 1))], true);
                }
                OneDimensionalCodeWriter.appendPattern(zArr, parseInt, UPCEANReader.START_END_PATTERN, true);
                return zArr;
            }
            throw new IllegalArgumentException("Contents do not pass checksum");
        } catch (FormatException e) {
            throw new IllegalArgumentException("Illegal contents");
        }
    }
}
