package com.ad4screen.sdk.service.modules.inapp.a.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class c {
    public static Date a(List<a> list, List<a> list2, Date date) {
        List arrayList = new ArrayList();
        arrayList.add(date);
        arrayList.addAll(a(date, (List) list2));
        arrayList.addAll(b(date, (List) list));
        return b(a(arrayList, (List) list2), (List) list);
    }

    public static List<Date> a(Date date, List<a> list) {
        List<Date> arrayList = new ArrayList();
        for (a b : list) {
            Date b2 = b.b();
            if (b2 != null && b2.after(date)) {
                arrayList.add(new Date(b2.getTime() + 1000));
            }
        }
        return arrayList;
    }

    public static List<Date> a(List<Date> list, List<a> list2) {
        List<Date> arrayList = new ArrayList();
        for (Date date : list) {
            if (list2 == null || list2.isEmpty() || !a((List) list2, date)) {
                arrayList.add(date);
            }
        }
        return arrayList;
    }

    public static boolean a(Date date, Date date2) {
        return (date == null || date2 == null || !date2.before(date)) ? false : true;
    }

    public static boolean a(Date date, Date date2, Date date3) {
        return (date3 == null || date == null || date2 == null || !date3.after(date) || !date3.before(date2)) ? false : true;
    }

    public static boolean a(List<a> list, Date date) {
        return a((List) list, date, true);
    }

    public static boolean a(List<a> list, Date date, boolean z) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        for (a aVar : list) {
            Date a = aVar.a();
            Date b = aVar.b();
            if (a != null && b != null && (a(a, b, date) || (z && (a.equals(date) || b.equals(date))))) {
                return true;
            }
            if (b == null && (b(a, date) || (z && date.equals(a)))) {
                return true;
            }
            if (a == null && (a(b, date) || (z && date.equals(b)))) {
                return true;
            }
        }
        return false;
    }

    public static Date b(List<Date> list, List<a> list2) {
        Collections.sort(list);
        for (Date date : list) {
            if (list2 == null || list2.isEmpty()) {
                return date;
            }
            if (a((List) list2, date)) {
                return date;
            }
        }
        return null;
    }

    private static List<Date> b(Date date, List<a> list) {
        List<Date> arrayList = new ArrayList();
        for (a a : list) {
            Date a2 = a.a();
            if (a2 != null && a2.after(date)) {
                arrayList.add(a2);
            }
        }
        return arrayList;
    }

    public static boolean b(Date date, Date date2) {
        return (date == null || date2 == null || !date2.after(date)) ? false : true;
    }
}
