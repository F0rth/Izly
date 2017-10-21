package org.kobjects.pim;

import java.util.Enumeration;
import java.util.Hashtable;

public class PimField {
    String name;
    Hashtable properties;
    Object value;

    public PimField(String str) {
        this.name = str;
    }

    public PimField(PimField pimField) {
        this(pimField.name);
        if (pimField.value instanceof String[]) {
            Object obj = new String[((String[]) pimField.value).length];
            System.arraycopy((String[]) pimField.value, 0, obj, 0, obj.length);
            this.value = obj;
        } else {
            this.value = pimField.value;
        }
        if (pimField.properties != null) {
            this.properties = new Hashtable();
            Enumeration keys = pimField.properties.keys();
            while (keys.hasMoreElements()) {
                String str = (String) keys.nextElement();
                this.properties.put(str, pimField.properties.get(str));
            }
        }
    }

    public boolean getAttribute(String str) {
        String property = getProperty("type");
        return (property == null || property.indexOf(str) == -1) ? false : true;
    }

    public String getProperty(String str) {
        return this.properties == null ? null : (String) this.properties.get(str);
    }

    public Object getValue() {
        return this.value;
    }

    public Enumeration propertyNames() {
        return this.properties.keys();
    }

    public void setAttribute(String str, boolean z) {
        if (getAttribute(str) != z) {
            String property = getProperty("type");
            if (!z) {
                int indexOf = property.indexOf(str);
                if (indexOf > 0) {
                    indexOf--;
                }
                str = indexOf != -1 ? property.substring(0, indexOf) + property.substring((indexOf + str.length()) + 1) : property;
            } else if (!(property == null || property.length() == 0)) {
                str = property + str;
            }
            setProperty("type", str);
        }
    }

    public void setProperty(String str, String str2) {
        if (this.properties == null) {
            if (str2 != null) {
                this.properties = new Hashtable();
            } else {
                return;
            }
        }
        if (str2 == null) {
            this.properties.remove(str);
        } else {
            this.properties.put(str, str2);
        }
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public String toString() {
        return this.name + (this.properties != null ? ";" + this.properties : "") + ":" + this.value;
    }
}
