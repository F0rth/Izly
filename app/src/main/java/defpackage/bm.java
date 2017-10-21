package defpackage;

import com.ad4screen.sdk.external.shortcutbadger.impl.AdwHomeBadger;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetListData;
import fr.smoney.android.izly.data.model.P2PGetMult;
import fr.smoney.android.izly.data.model.ServerError;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class bm extends DefaultHandler {
    public ServerError a = null;
    public P2PGetListData b = null;
    public boolean c = false;
    public boolean d = false;
    private StringBuilder e = new StringBuilder();
    private P2PGetMult f = null;
    private P2PGet g = null;

    public final void characters(char[] cArr, int i, int i2) throws SAXException {
        super.characters(cArr, i, i2);
        this.e.append(cArr, i, i2);
    }

    public final void endElement(String str, String str2, String str3) throws SAXException {
        boolean z = true;
        boolean z2 = false;
        if (this.c) {
            if (this.d) {
                if (str2.equals("GETS")) {
                    this.d = false;
                } else if (str2.equals("GET")) {
                    this.f.b.add(this.g);
                } else if (str2.equals("ID")) {
                    this.g.a = Long.parseLong(this.e.toString());
                } else if (str2.equals("RECID")) {
                    this.g.c = this.e.toString();
                } else if (str2.equals("RECCLI")) {
                    r2 = this.g;
                    if (!this.e.toString().equals("1")) {
                        z = false;
                    }
                    r2.d = z;
                } else if (str2.equals("RECFNAME")) {
                    this.g.f = this.e.toString();
                } else if (str2.equals("RECLNAME")) {
                    this.g.g = this.e.toString();
                } else if (str2.equals("ISPRO")) {
                    r2 = this.g;
                    if (Integer.parseInt(this.e.toString()) != 1) {
                        z = false;
                    }
                    r2.e = z;
                } else if (str2.equals("AMT")) {
                    if (this.e.toString().length() > 0) {
                        this.g.h = Double.parseDouble(this.e.toString());
                        return;
                    }
                    this.g.h = -1.0d;
                } else if (str2.equals("COM")) {
                    if (this.e.toString().length() > 0) {
                        this.g.i = Double.parseDouble(this.e.toString());
                        return;
                    }
                    this.g.i = -1.0d;
                } else if (str2.equals("RESPMSG")) {
                    this.g.k = this.e.toString();
                } else if (str2.equals("RESPDATE")) {
                    if (this.e.length() > 0) {
                        try {
                            this.g.l = ag.a.parse(this.e.toString()).getTime();
                        } catch (Exception e) {
                            throw new SAXException(e);
                        }
                    }
                } else if (str2.equals("STATUS")) {
                    this.g.m = Integer.parseInt(this.e.toString());
                } else if (str2.equals("REVCOUNT")) {
                    this.g.n = Integer.parseInt(this.e.toString());
                } else if (str2.equals("REVTIME")) {
                    if (this.e.length() > 0) {
                        try {
                            this.g.o = ag.a.parse(this.e.toString()).getTime();
                        } catch (Exception e2) {
                            throw new SAXException(e2);
                        }
                    }
                } else if (str2.equals("REVREMAINING") && this.e.length() > 0 && !this.e.toString().equals("0")) {
                    this.g.p = this.e.toString();
                }
            } else if (str2.equals("GETMULT")) {
                this.b.a.add(this.f);
                this.c = false;
            } else if (str2.equals("ID")) {
                this.f.c = Long.parseLong(this.e.toString());
            } else if (str2.equals("ISREAD")) {
                P2PGetMult p2PGetMult = this.f;
                if (!this.e.toString().equals("1")) {
                    z = false;
                }
                p2PGetMult.d = z;
            } else if (str2.equals("DATE")) {
                try {
                    this.f.e = ag.a.parse(this.e.toString()).getTime();
                } catch (Exception e22) {
                    throw new SAXException(e22);
                }
            } else if (str2.equals("TOTAL")) {
                if (this.e.toString().length() > 0) {
                    this.f.f = Double.parseDouble(this.e.toString());
                    return;
                }
                this.f.f = -1.0d;
            } else if (str2.equals("TOTALHT")) {
                if (this.e.toString().length() > 0) {
                    this.f.g = Double.parseDouble(this.e.toString());
                    return;
                }
                this.f.g = 0.0d;
            } else if (str2.equals("TAX")) {
                if (this.e.toString().length() > 0) {
                    this.f.h = Double.parseDouble(this.e.toString());
                    return;
                }
                this.f.h = 0.0d;
            } else if (str2.equals(AdwHomeBadger.COUNT)) {
                this.f.i = Integer.parseInt(this.e.toString());
            } else if (str2.equals("MSG")) {
                this.f.j = this.e.toString();
            } else if (str2.equals("ATTACHMENTID")) {
                this.f.n = this.e.toString();
            } else if (str2.equals("ATTACHMENTNAME")) {
                this.f.o = this.e.toString();
            } else if (str2.equals("MULTREVCOUNT")) {
                this.f.k = Integer.parseInt(this.e.toString());
            } else if (str2.equals("MULTREVTIME")) {
                if (this.e.length() > 0) {
                    try {
                        this.f.l = ag.a.parse(this.e.toString()).getTime();
                    } catch (Exception e222) {
                        throw new SAXException(e222);
                    }
                }
            } else if (str2.equals("MULTREVREMAINING")) {
                this.f.m = Long.parseLong(this.e.toString());
            }
        } else if (str2.equals("GETLIST")) {
            P2PGetListData p2PGetListData = this.b;
            if (this.b.c > this.b.a.size()) {
                z2 = true;
            }
            p2PGetListData.b = z2;
        } else if (str2.equals("Error")) {
            this.a.a = this.e.toString();
        } else if (str2.equals("Code")) {
            this.a.b = Integer.parseInt(this.e.toString());
        } else if (str2.equals("Msg")) {
            this.a.c = this.e.toString();
        } else if (str2.equals("Title")) {
            this.a.d = this.e.toString();
        } else if (str2.equals("Prio")) {
            this.a.e = Integer.parseInt(this.e.toString());
        } else if (str2.equals("BAL")) {
            this.b.f.a = Double.parseDouble(iu.c(this.e.toString()));
        } else if (str2.equals("LUD")) {
            try {
                this.b.f.c = ag.a.parse(this.e.toString()).getTime();
            } catch (Exception e2222) {
                throw new SAXException(e2222);
            }
        }
    }

    public final void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        boolean z = false;
        this.e.setLength(0);
        if (this.c) {
            if (this.d) {
                if (str2.equals("GET")) {
                    this.g = new P2PGet();
                }
            } else if (str2.equals("GETS")) {
                this.d = true;
                this.f.b = new ArrayList();
            } else if (str2.equals("GETMULT")) {
                this.f = new P2PGetMult();
            }
        } else if (str2.equals("E")) {
            this.a = new ServerError();
        } else if (str2.equals("RGETLIST")) {
            this.b = new P2PGetListData();
        } else if (str2.equals("GETLIST")) {
            this.b.c = Integer.parseInt(attributes.getValue("NB"));
            this.b.d = Integer.parseInt(attributes.getValue("TOTAL"));
            P2PGetListData p2PGetListData = this.b;
            if (Integer.parseInt(attributes.getValue("NEW")) == 1) {
                z = true;
            }
            p2PGetListData.e = z;
            this.b.a = new ArrayList();
        } else if (str2.equals("GETMULT")) {
            this.c = true;
            this.f = new P2PGetMult();
        }
    }
}
