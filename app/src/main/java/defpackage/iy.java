package defpackage;

import android.content.Context;
import android.database.Cursor;
import fr.smoney.android.izly.data.model.Bookmark;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.Contact.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public final class iy {
    public static List<Contact> a(Context context) {
        Pattern compile = Pattern.compile("^(0|(\\+|00)33)[67][0-9]{8}$");
        List<Contact> arrayList = new ArrayList();
        Cursor a = gh.a(context);
        if (a != null && a.moveToFirst()) {
            int columnIndex = a.getColumnIndex("contact_id");
            int columnIndex2 = a.getColumnIndex("display_name");
            int columnIndex3 = a.getColumnIndex("data1");
            int columnIndex4 = a.getColumnIndex("data2");
            do {
                String string = a.getString(columnIndex);
                String string2 = a.getString(columnIndex2);
                String replace = a.getString(columnIndex3).replace(" ", "");
                int i = a.getInt(columnIndex4);
                if (compile.matcher(replace).matches()) {
                    Contact contact = new Contact();
                    contact.a = string;
                    if (string2 == null) {
                        string2 = replace;
                    }
                    contact.b = string2;
                    contact.c = replace;
                    contact.g = a.c;
                    contact.h = i;
                    arrayList.add(contact);
                }
            } while (a.moveToNext());
            a.close();
        }
        return arrayList;
    }

    public static List<String> a(Context context, String str) {
        Pattern compile = Pattern.compile("^(0|(\\+|00)33)[67][0-9]{8}$");
        List<String> arrayList = new ArrayList();
        Cursor a = gh.a(context, str);
        if (a != null) {
            if (a.moveToFirst()) {
                int columnIndex = a.getColumnIndex("data1");
                do {
                    CharSequence replace = a.getString(columnIndex).replace(" ", "");
                    if (compile.matcher(replace).matches()) {
                        arrayList.add(replace);
                    }
                } while (a.moveToNext());
            }
            a.close();
        }
        return arrayList;
    }

    public static void a(List<Contact> list) {
        String str = null;
        for (Contact contact : list) {
            String b = jh.b(contact.b);
            contact.i = !b.equalsIgnoreCase(str);
            if (contact.i) {
                contact.j = b;
            }
            str = b;
        }
    }

    private static boolean a(List<Contact> list, String str) {
        for (Contact contact : list) {
            if (contact.a.compareTo(str) == 0) {
                return true;
            }
        }
        return false;
    }

    public static List<Contact> b(Context context) {
        Pattern compile = Pattern.compile("^(0|(\\+|00)33)[67][0-9]{8}$");
        Pattern compile2 = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Cursor c = gh.c(context);
        List<Contact> arrayList = new ArrayList();
        if (c != null) {
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex("contact_id");
                int columnIndex2 = c.getColumnIndex("display_name");
                int columnIndex3 = c.getColumnIndex("data1");
                int columnIndex4 = c.getColumnIndex("data1");
                String str = null;
                while (true) {
                    String string = c.getString(columnIndex);
                    String string2 = c.getString(columnIndex2);
                    String string3 = c.getString(columnIndex3);
                    Object replace = string3 != null ? string3.replace(" ", "") : null;
                    string3 = c.getString(columnIndex4);
                    if (string.equals(str) || !(compile.matcher(replace).matches() || compile2.matcher(string3).matches())) {
                        string2 = str;
                    } else {
                        Contact contact = new Contact();
                        contact.a = string;
                        if (string2 == null) {
                            string2 = string3;
                        }
                        contact.b = string2;
                        contact.c = replace;
                        contact.e = string3;
                        arrayList.add(contact);
                        string2 = string;
                    }
                    if (!c.moveToNext()) {
                        break;
                    }
                    str = string2;
                }
            }
            c.close();
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public static List<String> b(Context context, String str) {
        Pattern compile = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        List arrayList = new ArrayList();
        Cursor c = gh.c(context, str);
        if (c != null && c.moveToFirst()) {
            int columnIndex = c.getColumnIndex("data1");
            do {
                CharSequence string = c.getString(columnIndex);
                if (compile.matcher(string).matches()) {
                    arrayList.add(string);
                }
            } while (c.moveToNext());
            c.close();
        }
        return arrayList;
    }

    public static List<Contact> b(List<Bookmark> list) {
        List arrayList = new ArrayList();
        for (Bookmark bookmark : list) {
            if (!bookmark.d) {
                Contact contact = new Contact();
                contact.c = bookmark.a;
                contact.b = jf.a(bookmark.c, bookmark.b, bookmark.a);
                contact.g = a.a;
                arrayList.add(contact);
            }
        }
        return arrayList;
    }

    public static List<Contact> c(Context context) {
        Pattern compile = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Cursor c = gh.c(context);
        List arrayList = new ArrayList();
        if (c != null) {
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex("contact_id");
                int columnIndex2 = c.getColumnIndex("display_name");
                int columnIndex3 = c.getColumnIndex("data1");
                do {
                    String string = c.getString(columnIndex);
                    String string2 = c.getString(columnIndex2);
                    String string3 = c.getString(columnIndex3);
                    if (compile.matcher(string3).matches() && !string3.contains("whatsapp")) {
                        Contact contact = new Contact();
                        contact.a = string;
                        if (string2 == null) {
                            string2 = string3;
                        }
                        contact.b = string2;
                        contact.e = string3;
                        if (!iy.a(arrayList, contact.a)) {
                            arrayList.add(contact);
                        }
                    }
                } while (c.moveToNext());
            }
            c.close();
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public static List<String> c(Context context, String str) {
        List arrayList = new ArrayList();
        Cursor d = gh.d(context, str);
        if (d != null) {
            while (d.moveToNext()) {
                Object string = d.getString(d.getColumnIndex("data4"));
                Object string2 = d.getString(d.getColumnIndex("data7"));
                Object string3 = d.getString(d.getColumnIndex("data9"));
                if (string == null) {
                    string = "";
                }
                if (string2 == null) {
                    string2 = "";
                }
                if (string3 == null) {
                    string3 = "";
                }
                arrayList.add(string);
                arrayList.add(string2);
                arrayList.add(string3);
            }
            d.close();
        }
        return arrayList;
    }
}
