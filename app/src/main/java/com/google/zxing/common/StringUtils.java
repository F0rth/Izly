package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING = System.getProperty("file.encoding");
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    static {
        boolean z = SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING);
        ASSUME_SHIFT_JIS = z;
    }

    private StringUtils() {
    }

    public static String guessEncoding(byte[] bArr, Map<DecodeHintType, ?> map) {
        if (map != null) {
            String str = (String) map.get(DecodeHintType.CHARACTER_SET);
            if (str != null) {
                return str;
            }
        }
        int length = bArr.length;
        Object obj = 1;
        Object obj2 = 1;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        Object obj3 = (bArr.length > 3 && bArr[0] == (byte) -17 && bArr[1] == (byte) -69 && bArr[2] == (byte) -65) ? 1 : null;
        int i10 = 0;
        int i11 = 0;
        Object obj4 = 1;
        int i12 = 0;
        while (i11 < length && (obj != null || obj4 != null || obj2 != null)) {
            Object obj5;
            int i13;
            Object obj6;
            int i14;
            int i15 = bArr[i11] & 255;
            if (obj2 != null) {
                if (i10 > 0) {
                    if ((i15 & 128) != 0) {
                        obj5 = obj2;
                        i13 = i10 - 1;
                        if (obj != null) {
                            if (i15 <= 127 && i15 < 160) {
                                obj6 = null;
                                i14 = i9;
                                if (obj4 != null) {
                                    if (i4 > 0) {
                                        if (i15 != 128) {
                                        }
                                        obj4 = null;
                                    } else {
                                        if (i15 >= 64) {
                                        }
                                        obj4 = null;
                                    }
                                }
                                i11++;
                                i9 = i14;
                                obj = obj6;
                                obj2 = obj5;
                                i10 = i13;
                            } else if (i15 > 159 && (i15 < 192 || i15 == 215 || i15 == 247)) {
                                i14 = i9 + 1;
                                obj6 = obj;
                                if (obj4 != null) {
                                    if (i4 > 0) {
                                        if (i15 >= 64 || i15 == 127 || i15 > 252) {
                                            obj4 = null;
                                        } else {
                                            i4--;
                                        }
                                    } else if (i15 != 128 || i15 == 160 || i15 > 239) {
                                        obj4 = null;
                                    } else if (i15 > 160 && i15 < 224) {
                                        i5++;
                                        i6++;
                                        if (i6 > i8) {
                                            i7 = 0;
                                            i8 = i6;
                                        } else {
                                            i7 = 0;
                                        }
                                    } else if (i15 > 127) {
                                        i4++;
                                        i7++;
                                        if (i7 > i12) {
                                            i6 = 0;
                                            i12 = i7;
                                        } else {
                                            i6 = 0;
                                        }
                                    } else {
                                        i6 = 0;
                                        i7 = 0;
                                    }
                                }
                                i11++;
                                i9 = i14;
                                obj = obj6;
                                obj2 = obj5;
                                i10 = i13;
                            }
                        }
                        obj6 = obj;
                        i14 = i9;
                        if (obj4 != null) {
                            if (i4 > 0) {
                                if (i15 >= 64) {
                                }
                                obj4 = null;
                            } else {
                                if (i15 != 128) {
                                }
                                obj4 = null;
                            }
                        }
                        i11++;
                        i9 = i14;
                        obj = obj6;
                        obj2 = obj5;
                        i10 = i13;
                    }
                } else if ((i15 & 128) != 0) {
                    if ((i15 & 64) != 0) {
                        int i16 = i10 + 1;
                        if ((i15 & 32) == 0) {
                            i++;
                            i13 = i16;
                            obj5 = obj2;
                        } else {
                            i16++;
                            if ((i15 & 16) == 0) {
                                i2++;
                                i13 = i16;
                                obj5 = obj2;
                            } else {
                                i10 = i16 + 1;
                                if ((i15 & 8) == 0) {
                                    i3++;
                                    obj5 = obj2;
                                    i13 = i10;
                                }
                            }
                        }
                        if (obj != null) {
                            if (i15 <= 127) {
                            }
                            i14 = i9 + 1;
                            obj6 = obj;
                            if (obj4 != null) {
                                if (i4 > 0) {
                                    if (i15 != 128) {
                                    }
                                    obj4 = null;
                                } else {
                                    if (i15 >= 64) {
                                    }
                                    obj4 = null;
                                }
                            }
                            i11++;
                            i9 = i14;
                            obj = obj6;
                            obj2 = obj5;
                            i10 = i13;
                        }
                        obj6 = obj;
                        i14 = i9;
                        if (obj4 != null) {
                            if (i4 > 0) {
                                if (i15 >= 64) {
                                }
                                obj4 = null;
                            } else {
                                if (i15 != 128) {
                                }
                                obj4 = null;
                            }
                        }
                        i11++;
                        i9 = i14;
                        obj = obj6;
                        obj2 = obj5;
                        i10 = i13;
                    }
                }
                obj5 = null;
                i13 = i10;
                if (obj != null) {
                    if (i15 <= 127) {
                    }
                    i14 = i9 + 1;
                    obj6 = obj;
                    if (obj4 != null) {
                        if (i4 > 0) {
                            if (i15 != 128) {
                            }
                            obj4 = null;
                        } else {
                            if (i15 >= 64) {
                            }
                            obj4 = null;
                        }
                    }
                    i11++;
                    i9 = i14;
                    obj = obj6;
                    obj2 = obj5;
                    i10 = i13;
                }
                obj6 = obj;
                i14 = i9;
                if (obj4 != null) {
                    if (i4 > 0) {
                        if (i15 >= 64) {
                        }
                        obj4 = null;
                    } else {
                        if (i15 != 128) {
                        }
                        obj4 = null;
                    }
                }
                i11++;
                i9 = i14;
                obj = obj6;
                obj2 = obj5;
                i10 = i13;
            }
            obj5 = obj2;
            i13 = i10;
            if (obj != null) {
                if (i15 <= 127) {
                }
                i14 = i9 + 1;
                obj6 = obj;
                if (obj4 != null) {
                    if (i4 > 0) {
                        if (i15 != 128) {
                        }
                        obj4 = null;
                    } else {
                        if (i15 >= 64) {
                        }
                        obj4 = null;
                    }
                }
                i11++;
                i9 = i14;
                obj = obj6;
                obj2 = obj5;
                i10 = i13;
            }
            obj6 = obj;
            i14 = i9;
            if (obj4 != null) {
                if (i4 > 0) {
                    if (i15 >= 64) {
                    }
                    obj4 = null;
                } else {
                    if (i15 != 128) {
                    }
                    obj4 = null;
                }
            }
            i11++;
            i9 = i14;
            obj = obj6;
            obj2 = obj5;
            i10 = i13;
        }
        Object obj7 = (obj2 == null || i10 <= 0) ? obj2 : null;
        if (obj4 != null && i4 > 0) {
            obj4 = null;
        }
        return (obj7 == null || (obj3 == null && (i + i2) + i3 <= 0)) ? (obj4 == null || (!ASSUME_SHIFT_JIS && i8 < 3 && i12 < 3)) ? (obj == null || obj4 == null) ? obj != null ? ISO88591 : obj4 != null ? SHIFT_JIS : obj7 != null ? UTF8 : PLATFORM_DEFAULT_ENCODING : (!(i8 == 2 && i5 == 2) && i9 * 10 < length) ? ISO88591 : SHIFT_JIS : SHIFT_JIS : UTF8;
    }
}
