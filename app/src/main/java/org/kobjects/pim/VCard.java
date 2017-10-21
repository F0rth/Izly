package org.kobjects.pim;

public class VCard extends PimItem {
    public VCard(VCard vCard) {
        super(vCard);
    }

    public int getArraySize(String str) {
        return str.equals("n") ? 5 : str.equals("adr") ? 6 : -1;
    }

    public String getType() {
        return "vcard";
    }
}
