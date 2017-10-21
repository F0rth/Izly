package com.google.zxing.oned;

import java.util.Arrays;

public final class CodaBarWriter extends OneDimensionalCodeWriter {
    private static final char[] END_CHARS = new char[]{'T', 'N', '*', 'E'};
    private static final char[] START_CHARS = new char[]{'A', 'B', 'C', 'D'};

    public final boolean[] encode(String str) {
        if (!CodaBarReader.arrayContains(START_CHARS, Character.toUpperCase(str.charAt(0)))) {
            throw new IllegalArgumentException("Codabar should start with one of the following: " + Arrays.toString(START_CHARS));
        } else if (CodaBarReader.arrayContains(END_CHARS, Character.toUpperCase(str.charAt(str.length() - 1)))) {
            int i = 20;
            int i2 = 1;
            while (i2 < str.length() - 1) {
                if (Character.isDigit(str.charAt(i2)) || str.charAt(i2) == '-' || str.charAt(i2) == '$') {
                    i += 9;
                } else {
                    if (CodaBarReader.arrayContains(new char[]{'/', ':', '+', '.'}, str.charAt(i2))) {
                        i += 10;
                    } else {
                        throw new IllegalArgumentException("Cannot encode : '" + str.charAt(i2) + '\'');
                    }
                }
                i2++;
            }
            boolean[] zArr = new boolean[((str.length() - 1) + i)];
            i = 0;
            for (i2 = 0; i2 < str.length(); i2++) {
                int i3;
                boolean z;
                int i4;
                int i5;
                int i6;
                char toUpperCase = Character.toUpperCase(str.charAt(i2));
                if (i2 == str.length() - 1) {
                    switch (toUpperCase) {
                        case '*':
                            toUpperCase = 'C';
                            break;
                        case 'E':
                            toUpperCase = 'D';
                            break;
                        case 'N':
                            toUpperCase = 'B';
                            break;
                        case 'T':
                            toUpperCase = 'A';
                            break;
                    }
                }
                int i7 = 0;
                while (i7 < CodaBarReader.ALPHABET.length) {
                    if (toUpperCase == CodaBarReader.ALPHABET[i7]) {
                        i3 = CodaBarReader.CHARACTER_ENCODINGS[i7];
                        z = true;
                        i4 = 0;
                        i5 = 0;
                        while (i4 < 7) {
                            zArr[i] = z;
                            i6 = i + 1;
                            if (((i3 >> (6 - i4)) & 1) != 0 || i5 == 1) {
                                i4++;
                                i5 = 0;
                                z = z;
                                i = i6;
                            } else {
                                i5++;
                                i = i6;
                            }
                        }
                        if (i2 < str.length() - 1) {
                            zArr[i] = false;
                            i++;
                        }
                    } else {
                        i7++;
                    }
                }
                i3 = 0;
                z = true;
                i4 = 0;
                i5 = 0;
                while (i4 < 7) {
                    zArr[i] = z;
                    i6 = i + 1;
                    if (((i3 >> (6 - i4)) & 1) != 0) {
                    }
                    if (z) {
                    }
                    i4++;
                    i5 = 0;
                    z = z;
                    i = i6;
                }
                if (i2 < str.length() - 1) {
                    zArr[i] = false;
                    i++;
                }
            }
            return zArr;
        } else {
            throw new IllegalArgumentException("Codabar should end with one of the following: " + Arrays.toString(END_CHARS));
        }
    }
}
