package com.google.tagmanager.protobuf.nano;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class WireFormatNano {
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final Boolean[] EMPTY_BOOLEAN_REF_ARRAY = new Boolean[0];
    public static final byte[] EMPTY_BYTES = new byte[0];
    public static final byte[][] EMPTY_BYTES_ARRAY = new byte[0][];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final Double[] EMPTY_DOUBLE_REF_ARRAY = new Double[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final Float[] EMPTY_FLOAT_REF_ARRAY = new Float[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Integer[] EMPTY_INT_REF_ARRAY = new Integer[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final Long[] EMPTY_LONG_REF_ARRAY = new Long[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    static final int MESSAGE_SET_ITEM = 1;
    static final int MESSAGE_SET_ITEM_END_TAG = makeTag(1, 4);
    static final int MESSAGE_SET_ITEM_TAG = makeTag(1, 3);
    static final int MESSAGE_SET_MESSAGE = 3;
    static final int MESSAGE_SET_MESSAGE_TAG = makeTag(3, 2);
    static final int MESSAGE_SET_TYPE_ID = 2;
    static final int MESSAGE_SET_TYPE_ID_TAG = makeTag(2, 0);
    static final int TAG_TYPE_BITS = 3;
    static final int TAG_TYPE_MASK = 7;
    static final int WIRETYPE_END_GROUP = 4;
    static final int WIRETYPE_FIXED32 = 5;
    static final int WIRETYPE_FIXED64 = 1;
    static final int WIRETYPE_LENGTH_DELIMITED = 2;
    static final int WIRETYPE_START_GROUP = 3;
    static final int WIRETYPE_VARINT = 0;

    private WireFormatNano() {
    }

    public static int computeWireSize(List<UnknownFieldData> list) {
        if (list == null) {
            return 0;
        }
        int i = 0;
        for (UnknownFieldData unknownFieldData : list) {
            i = unknownFieldData.bytes.length + (i + CodedOutputByteBufferNano.computeRawVarint32Size(unknownFieldData.tag));
        }
        return i;
    }

    public static <T> T getExtension(Extension<T> extension, List<UnknownFieldData> list) {
        if (list == null) {
            return null;
        }
        List<UnknownFieldData> arrayList = new ArrayList();
        for (UnknownFieldData unknownFieldData : list) {
            if (getTagFieldNumber(unknownFieldData.tag) == extension.fieldNumber) {
                arrayList.add(unknownFieldData);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        if (extension.isRepeatedField) {
            List arrayList2 = new ArrayList(arrayList.size());
            for (UnknownFieldData unknownFieldData2 : arrayList) {
                arrayList2.add(readData(extension.fieldType, unknownFieldData2.bytes));
            }
            return extension.listType.cast(arrayList2);
        }
        return readData(extension.fieldType, ((UnknownFieldData) arrayList.get(arrayList.size() - 1)).bytes);
    }

    public static final int getRepeatedFieldArrayLength(CodedInputByteBufferNano codedInputByteBufferNano, int i) throws IOException {
        int i2 = 1;
        int position = codedInputByteBufferNano.getPosition();
        codedInputByteBufferNano.skipField(i);
        while (codedInputByteBufferNano.getBytesUntilLimit() > 0 && codedInputByteBufferNano.readTag() == i) {
            codedInputByteBufferNano.skipField(i);
            i2++;
        }
        codedInputByteBufferNano.rewindToPosition(position);
        return i2;
    }

    public static int getTagFieldNumber(int i) {
        return i >>> 3;
    }

    static int getTagWireType(int i) {
        return i & 7;
    }

    static int makeTag(int i, int i2) {
        return (i << 3) | i2;
    }

    public static boolean parseUnknownField(CodedInputByteBufferNano codedInputByteBufferNano, int i) throws IOException {
        return codedInputByteBufferNano.skipField(i);
    }

    private static <T> T readData(java.lang.Class<T> r4, byte[] r5) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.tagmanager.protobuf.nano.WireFormatNano.readData(java.lang.Class, byte[]):T. bs: [B:5:0x000d, B:40:0x0080]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = r5.length;
        if (r0 != 0) goto L_0x0005;
    L_0x0003:
        r0 = 0;
    L_0x0004:
        return r0;
    L_0x0005:
        r1 = com.google.tagmanager.protobuf.nano.CodedInputByteBufferNano.newInstance(r5);
        r0 = java.lang.String.class;
        if (r4 != r0) goto L_0x0016;
    L_0x000d:
        r0 = r1.readString();	 Catch:{ IOException -> 0x00a5 }
        r0 = r4.cast(r0);	 Catch:{ IOException -> 0x00a5 }
        goto L_0x0004;
    L_0x0016:
        r0 = java.lang.Integer.class;
        if (r4 != r0) goto L_0x0027;
    L_0x001a:
        r0 = r1.readInt32();	 Catch:{ IOException -> 0x00a5 }
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ IOException -> 0x00a5 }
        r0 = r4.cast(r0);	 Catch:{ IOException -> 0x00a5 }
        goto L_0x0004;
    L_0x0027:
        r0 = java.lang.Long.class;
        if (r4 != r0) goto L_0x0038;
    L_0x002b:
        r0 = r1.readInt64();	 Catch:{ IOException -> 0x00a5 }
        r0 = java.lang.Long.valueOf(r0);	 Catch:{ IOException -> 0x00a5 }
        r0 = r4.cast(r0);	 Catch:{ IOException -> 0x00a5 }
        goto L_0x0004;
    L_0x0038:
        r0 = java.lang.Boolean.class;
        if (r4 != r0) goto L_0x0049;
    L_0x003c:
        r0 = r1.readBool();	 Catch:{ IOException -> 0x00a5 }
        r0 = java.lang.Boolean.valueOf(r0);	 Catch:{ IOException -> 0x00a5 }
        r0 = r4.cast(r0);	 Catch:{ IOException -> 0x00a5 }
        goto L_0x0004;
    L_0x0049:
        r0 = java.lang.Float.class;
        if (r4 != r0) goto L_0x005a;
    L_0x004d:
        r0 = r1.readFloat();	 Catch:{ IOException -> 0x00a5 }
        r0 = java.lang.Float.valueOf(r0);	 Catch:{ IOException -> 0x00a5 }
        r0 = r4.cast(r0);	 Catch:{ IOException -> 0x00a5 }
        goto L_0x0004;
    L_0x005a:
        r0 = java.lang.Double.class;
        if (r4 != r0) goto L_0x006b;
    L_0x005e:
        r0 = r1.readDouble();	 Catch:{ IOException -> 0x00a5 }
        r0 = java.lang.Double.valueOf(r0);	 Catch:{ IOException -> 0x00a5 }
        r0 = r4.cast(r0);	 Catch:{ IOException -> 0x00a5 }
        goto L_0x0004;
    L_0x006b:
        r0 = byte[].class;
        if (r4 != r0) goto L_0x0078;
    L_0x006f:
        r0 = r1.readBytes();	 Catch:{ IOException -> 0x00a5 }
        r0 = r4.cast(r0);	 Catch:{ IOException -> 0x00a5 }
        goto L_0x0004;	 Catch:{ IOException -> 0x00a5 }
    L_0x0078:
        r0 = com.google.tagmanager.protobuf.nano.MessageNano.class;	 Catch:{ IOException -> 0x00a5 }
        r0 = r0.isAssignableFrom(r4);	 Catch:{ IOException -> 0x00a5 }
        if (r0 == 0) goto L_0x00c4;
    L_0x0080:
        r0 = r4.newInstance();	 Catch:{ IllegalAccessException -> 0x008f, InstantiationException -> 0x00ae }
        r0 = (com.google.tagmanager.protobuf.nano.MessageNano) r0;	 Catch:{ IllegalAccessException -> 0x008f, InstantiationException -> 0x00ae }
        r1.readMessage(r0);	 Catch:{ IllegalAccessException -> 0x008f, InstantiationException -> 0x00ae }
        r0 = r4.cast(r0);	 Catch:{ IllegalAccessException -> 0x008f, InstantiationException -> 0x00ae }
        goto L_0x0004;
    L_0x008f:
        r0 = move-exception;
        r1 = new java.lang.IllegalArgumentException;	 Catch:{ IOException -> 0x00a5 }
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00a5 }
        r3 = "Error creating instance of class ";	 Catch:{ IOException -> 0x00a5 }
        r2.<init>(r3);	 Catch:{ IOException -> 0x00a5 }
        r2 = r2.append(r4);	 Catch:{ IOException -> 0x00a5 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x00a5 }
        r1.<init>(r2, r0);	 Catch:{ IOException -> 0x00a5 }
        throw r1;	 Catch:{ IOException -> 0x00a5 }
    L_0x00a5:
        r0 = move-exception;
        r1 = new java.lang.IllegalArgumentException;
        r2 = "Error reading extension field";
        r1.<init>(r2, r0);
        throw r1;
    L_0x00ae:
        r0 = move-exception;
        r1 = new java.lang.IllegalArgumentException;	 Catch:{ IOException -> 0x00a5 }
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00a5 }
        r3 = "Error creating instance of class ";	 Catch:{ IOException -> 0x00a5 }
        r2.<init>(r3);	 Catch:{ IOException -> 0x00a5 }
        r2 = r2.append(r4);	 Catch:{ IOException -> 0x00a5 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x00a5 }
        r1.<init>(r2, r0);	 Catch:{ IOException -> 0x00a5 }
        throw r1;	 Catch:{ IOException -> 0x00a5 }
    L_0x00c4:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ IOException -> 0x00a5 }
        r1 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00a5 }
        r2 = "Unhandled extension field type: ";	 Catch:{ IOException -> 0x00a5 }
        r1.<init>(r2);	 Catch:{ IOException -> 0x00a5 }
        r1 = r1.append(r4);	 Catch:{ IOException -> 0x00a5 }
        r1 = r1.toString();	 Catch:{ IOException -> 0x00a5 }
        r0.<init>(r1);	 Catch:{ IOException -> 0x00a5 }
        throw r0;	 Catch:{ IOException -> 0x00a5 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.tagmanager.protobuf.nano.WireFormatNano.readData(java.lang.Class, byte[]):T");
    }

    public static <T> void setExtension(Extension<T> extension, T t, List<UnknownFieldData> list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (extension.fieldNumber == getTagFieldNumber(((UnknownFieldData) it.next()).tag)) {
                it.remove();
            }
        }
        if (t != null) {
            if (t instanceof List) {
                for (Object write : (List) t) {
                    list.add(write(extension.fieldNumber, write));
                }
                return;
            }
            list.add(write(extension.fieldNumber, t));
        }
    }

    public static boolean storeUnknownField(List<UnknownFieldData> list, CodedInputByteBufferNano codedInputByteBufferNano, int i) throws IOException {
        int position = codedInputByteBufferNano.getPosition();
        boolean skipField = codedInputByteBufferNano.skipField(i);
        list.add(new UnknownFieldData(i, codedInputByteBufferNano.getData(position, codedInputByteBufferNano.getPosition() - position)));
        return skipField;
    }

    private static UnknownFieldData write(int i, Object obj) {
        byte[] bArr;
        int makeTag;
        Class cls = obj.getClass();
        if (cls == String.class) {
            try {
                String str = (String) obj;
                bArr = new byte[CodedOutputByteBufferNano.computeStringSizeNoTag(str)];
                CodedOutputByteBufferNano.newInstance(bArr).writeStringNoTag(str);
                makeTag = makeTag(i, 2);
            } catch (Throwable e) {
                throw new IllegalArgumentException(e);
            }
        } else if (cls == Integer.class) {
            Integer num = (Integer) obj;
            bArr = new byte[CodedOutputByteBufferNano.computeInt32SizeNoTag(num.intValue())];
            CodedOutputByteBufferNano.newInstance(bArr).writeInt32NoTag(num.intValue());
            makeTag = makeTag(i, 0);
        } else if (cls == Long.class) {
            Long l = (Long) obj;
            bArr = new byte[CodedOutputByteBufferNano.computeInt64SizeNoTag(l.longValue())];
            CodedOutputByteBufferNano.newInstance(bArr).writeInt64NoTag(l.longValue());
            makeTag = makeTag(i, 0);
        } else if (cls == Boolean.class) {
            Boolean bool = (Boolean) obj;
            bArr = new byte[CodedOutputByteBufferNano.computeBoolSizeNoTag(bool.booleanValue())];
            CodedOutputByteBufferNano.newInstance(bArr).writeBoolNoTag(bool.booleanValue());
            makeTag = makeTag(i, 0);
        } else if (cls == Float.class) {
            Float f = (Float) obj;
            bArr = new byte[CodedOutputByteBufferNano.computeFloatSizeNoTag(f.floatValue())];
            CodedOutputByteBufferNano.newInstance(bArr).writeFloatNoTag(f.floatValue());
            makeTag = makeTag(i, 5);
        } else if (cls == Double.class) {
            Double d = (Double) obj;
            bArr = new byte[CodedOutputByteBufferNano.computeDoubleSizeNoTag(d.doubleValue())];
            CodedOutputByteBufferNano.newInstance(bArr).writeDoubleNoTag(d.doubleValue());
            makeTag = makeTag(i, 1);
        } else if (cls == byte[].class) {
            byte[] bArr2 = (byte[]) obj;
            bArr = new byte[CodedOutputByteBufferNano.computeByteArraySizeNoTag(bArr2)];
            CodedOutputByteBufferNano.newInstance(bArr).writeByteArrayNoTag(bArr2);
            makeTag = makeTag(i, 2);
        } else if (MessageNano.class.isAssignableFrom(cls)) {
            MessageNano messageNano = (MessageNano) obj;
            makeTag = messageNano.getSerializedSize();
            bArr = new byte[(CodedOutputByteBufferNano.computeRawVarint32Size(makeTag) + makeTag)];
            CodedOutputByteBufferNano newInstance = CodedOutputByteBufferNano.newInstance(bArr);
            newInstance.writeRawVarint32(makeTag);
            newInstance.writeRawBytes(MessageNano.toByteArray(messageNano));
            makeTag = makeTag(i, 2);
        } else {
            throw new IllegalArgumentException("Unhandled extension field type: " + cls);
        }
        return new UnknownFieldData(makeTag, bArr);
    }

    public static void writeUnknownFields(List<UnknownFieldData> list, CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (list != null) {
            for (UnknownFieldData unknownFieldData : list) {
                codedOutputByteBufferNano.writeTag(getTagFieldNumber(unknownFieldData.tag), getTagWireType(unknownFieldData.tag));
                codedOutputByteBufferNano.writeRawBytes(unknownFieldData.bytes);
            }
        }
    }
}
