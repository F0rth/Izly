package com.ad4screen.sdk.service.modules.inapp;

import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.service.modules.inapp.a.a.d;
import com.ad4screen.sdk.service.modules.inapp.a.a.e;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class j {
    private a a;

    public j(a aVar) {
        this.a = aVar;
    }

    private Calendar a(Calendar calendar) {
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar2.set(11, 0);
        calendar2.set(12, 0);
        calendar2.set(13, 0);
        calendar2.set(14, 0);
        return calendar2;
    }

    private List<Date> a(d dVar, Calendar calendar, Calendar calendar2) {
        List<Date> arrayList = new ArrayList();
        List f = dVar.f();
        Calendar a = a(calendar);
        a.set(11, calendar.get(11));
        int b = dVar.b();
        while (a(calendar2, a)) {
            arrayList.addAll(a(f, a));
            a.add(11, b);
        }
        return arrayList;
    }

    private List<Date> a(HashMap<e, Integer> hashMap, List<Integer> list, List<Integer> list2, Calendar calendar) {
        List<Date> arrayList = new ArrayList();
        if (a((HashMap) hashMap)) {
            e a = e.a(calendar);
            if (hashMap.containsKey(a)) {
                Integer num = (Integer) hashMap.get(a);
                if (num == null || a(num.intValue(), calendar)) {
                    arrayList.addAll(a((List) list, (List) list2, calendar));
                }
            }
            return arrayList;
        }
        arrayList.addAll(a((List) list, (List) list2, calendar));
        return arrayList;
    }

    private List<Date> a(List<Integer> list, Calendar calendar) {
        List<Date> arrayList = new ArrayList();
        if (list == null || list.isEmpty()) {
            arrayList.add(calendar.getTime());
        } else {
            for (Integer num : list) {
                if (num != null) {
                    calendar.set(12, num.intValue());
                    arrayList.add(calendar.getTime());
                }
            }
        }
        return arrayList;
    }

    private List<Date> a(List<Integer> list, HashMap<e, Integer> hashMap, List<Integer> list2, List<Integer> list3, Calendar calendar) {
        List<Date> arrayList = new ArrayList();
        if (list == null || list.isEmpty()) {
            arrayList.addAll(a((HashMap) hashMap, (List) list2, (List) list3, calendar));
        } else if (list.contains(Integer.valueOf(calendar.get(5)))) {
            arrayList.addAll(a((HashMap) hashMap, (List) list2, (List) list3, calendar));
        }
        return arrayList;
    }

    private List<Date> a(List<Integer> list, List<Integer> list2, Calendar calendar) {
        List<Date> arrayList = new ArrayList();
        if (list == null || list.isEmpty()) {
            arrayList.addAll(a((List) list2, calendar));
        } else {
            for (Integer num : list) {
                if (num != null) {
                    calendar.set(11, num.intValue());
                    arrayList.addAll(a((List) list2, calendar));
                }
            }
        }
        return arrayList;
    }

    protected static boolean a(int i, Calendar calendar) {
        int i2 = calendar.get(5);
        int actualMaximum = calendar.getActualMaximum(5);
        if (i > 0) {
            if (i2 <= (i - 1) * 7 || i2 > i * 7) {
                return false;
            }
        } else if (i >= 0) {
            return false;
        } else {
            i2 = (actualMaximum - i2) + 1;
            actualMaximum = Math.abs(i);
            if (i2 <= (actualMaximum - 1) * 7) {
                return false;
            }
            if (i2 > actualMaximum * 7) {
                return false;
            }
        }
        return true;
    }

    private boolean a(Calendar calendar, Calendar calendar2) {
        Calendar calendar3 = (Calendar) this.a.d().clone();
        calendar3.add(6, 1);
        return calendar2.before(calendar3) || calendar2.before(calendar);
    }

    private boolean a(HashMap<e, Integer> hashMap) {
        return (hashMap == null || hashMap.isEmpty()) ? false : true;
    }

    private List<Date> b(d dVar, Calendar calendar, Calendar calendar2) {
        List<Date> arrayList = new ArrayList();
        List e = dVar.e();
        List f = dVar.f();
        Calendar a = a(calendar);
        int b = dVar.b();
        while (a(calendar2, a)) {
            arrayList.addAll(a(e, f, a));
            a.add(6, b);
        }
        return arrayList;
    }

    private List<Date> c(d dVar, Calendar calendar, Calendar calendar2) {
        List<Date> arrayList = new ArrayList();
        HashMap d = dVar.d();
        List e = dVar.e();
        List f = dVar.f();
        Calendar a = a(calendar);
        int b = dVar.b();
        while (a(calendar2, a)) {
            arrayList.addAll(a(d, e, f, a));
            if (a.get(7) == 1) {
                a.add(6, ((b - 1) * 7) + 1);
            } else {
                a.add(6, 1);
            }
        }
        return arrayList;
    }

    private List<Date> d(d dVar, Calendar calendar, Calendar calendar2) {
        List<Date> arrayList = new ArrayList();
        List c = dVar.c();
        HashMap d = dVar.d();
        List e = dVar.e();
        List f = dVar.f();
        Calendar a = a(calendar);
        int b = dVar.b();
        while (a(calendar2, a)) {
            arrayList.addAll(a(c, d, e, f, a));
            if (a.get(5) != a.getActualMaximum(5) || b <= 1) {
                a.add(6, 1);
            } else {
                a.add(6, 1);
                a.add(2, b - 1);
            }
        }
        return arrayList;
    }

    public List<Date> a(d dVar, Date date, Date date2) {
        List<Date> arrayList = new ArrayList();
        Calendar d = this.a.d();
        if (date != null) {
            d.setTime(date);
        }
        Calendar calendar = null;
        if (date2 != null) {
            calendar = this.a.d();
            calendar.setTime(date2);
        }
        switch (dVar.a()) {
            case HOURLY:
                arrayList.addAll(a(dVar, d, calendar));
                return arrayList;
            case DAILY:
                d.add(6, dVar.b() * -1);
                arrayList.addAll(b(dVar, d, calendar));
                return arrayList;
            case WEEKLY:
                d.add(6, dVar.b() * -7);
                arrayList.addAll(c(dVar, d, calendar));
                return arrayList;
            case MONTHLY:
                d.add(2, dVar.b() * -1);
                arrayList.addAll(d(dVar, d, calendar));
                return arrayList;
            default:
                return arrayList;
        }
    }

    public List<com.ad4screen.sdk.service.modules.inapp.a.a.a> a(d dVar, Date date, Date date2, boolean z) {
        List<com.ad4screen.sdk.service.modules.inapp.a.a.a> arrayList = new ArrayList();
        for (Date date3 : a(dVar, date, date2)) {
            Date date32;
            if (date32 != null) {
                if (z) {
                    Calendar d = this.a.d();
                    date32 = new Date(date32.getTime() - ((long) d.getTimeZone().getOffset(d.getTimeInMillis())));
                }
                com.ad4screen.sdk.service.modules.inapp.a.a.a aVar = new com.ad4screen.sdk.service.modules.inapp.a.a.a(date32, new Date(date32.getTime() + dVar.g()));
                if (date != null) {
                    if (aVar.a().before(date)) {
                        if (aVar.b().after(date)) {
                            aVar.a(date);
                        }
                    }
                    if (date2 != null) {
                        if (!aVar.a().after(date2)) {
                            if (aVar.b().after(date2)) {
                                aVar.b(date2);
                            }
                        }
                    }
                }
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }
}
