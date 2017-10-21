package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERInteger;

public class TimeStampedDataParser {
    private ASN1OctetStringParser content;
    private DERIA5String dataUri;
    private MetaData metaData;
    private ASN1SequenceParser parser;
    private Evidence temporalEvidence;
    private ASN1Integer version;

    private TimeStampedDataParser(ASN1SequenceParser aSN1SequenceParser) throws IOException {
        this.parser = aSN1SequenceParser;
        this.version = DERInteger.getInstance(aSN1SequenceParser.readObject());
        ASN1Encodable readObject = aSN1SequenceParser.readObject();
        if (readObject instanceof DERIA5String) {
            this.dataUri = DERIA5String.getInstance(readObject);
            readObject = aSN1SequenceParser.readObject();
        }
        if ((readObject instanceof MetaData) || (readObject instanceof ASN1SequenceParser)) {
            this.metaData = MetaData.getInstance(readObject.toASN1Primitive());
            readObject = aSN1SequenceParser.readObject();
        }
        if (readObject instanceof ASN1OctetStringParser) {
            this.content = (ASN1OctetStringParser) readObject;
        }
    }

    public static TimeStampedDataParser getInstance(Object obj) throws IOException {
        return obj instanceof ASN1Sequence ? new TimeStampedDataParser(((ASN1Sequence) obj).parser()) : obj instanceof ASN1SequenceParser ? new TimeStampedDataParser((ASN1SequenceParser) obj) : null;
    }

    public ASN1OctetStringParser getContent() {
        return this.content;
    }

    public DERIA5String getDataUri() {
        return this.dataUri;
    }

    public MetaData getMetaData() {
        return this.metaData;
    }

    public Evidence getTemporalEvidence() throws IOException {
        if (this.temporalEvidence == null) {
            this.temporalEvidence = Evidence.getInstance(this.parser.readObject().toASN1Primitive());
        }
        return this.temporalEvidence;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.version);
        if (this.dataUri != null) {
            aSN1EncodableVector.add(this.dataUri);
        }
        if (this.metaData != null) {
            aSN1EncodableVector.add(this.metaData);
        }
        if (this.content != null) {
            aSN1EncodableVector.add(this.content);
        }
        aSN1EncodableVector.add(this.temporalEvidence);
        return new BERSequence(aSN1EncodableVector);
    }
}
