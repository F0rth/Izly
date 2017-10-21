package defpackage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.util.Patterns;
import fr.smoney.android.izly.ui.view.ExpandableTextView.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ji {
    public static final MatchFilter a = new ji$1();

    public static SpannableString a(Context context, SpannableString spannableString) {
        ArrayList arrayList = new ArrayList();
        String[] strArr = new String[]{"http://", "https://", "rtsp://"};
        ji.a(arrayList, spannableString, Patterns.WEB_URL, strArr, a, null);
        ji.a(arrayList);
        if (arrayList.size() != 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ji$a ji_a = (ji$a) it.next();
                String str = ji_a.a;
                spannableString.setSpan(new a(context, str, 1), ji_a.b, ji_a.c, 33);
            }
        }
        return spannableString;
    }

    private static void a(ArrayList<ji$a> arrayList) {
        Collections.sort(arrayList, new ji$2());
        int i = 0;
        int size = arrayList.size();
        while (i < size - 1) {
            ji$a ji_a = (ji$a) arrayList.get(i);
            ji$a ji_a2 = (ji$a) arrayList.get(i + 1);
            if (ji_a.b <= ji_a2.b && ji_a.c > ji_a2.b) {
                int i2 = ji_a2.c <= ji_a.c ? i + 1 : ji_a.c - ji_a.b > ji_a2.c - ji_a2.b ? i + 1 : ji_a.c - ji_a.b < ji_a2.c - ji_a2.b ? i : -1;
                if (i2 != -1) {
                    arrayList.remove(i2);
                    size--;
                }
            }
            i++;
        }
    }

    private static void a(ArrayList<ji$a> arrayList, Spannable spannable, Pattern pattern, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                Object obj;
                ji$a ji_a = new ji$a();
                String group = matcher.group(0);
                Object obj2 = null;
                int length = strArr.length;
                int i = 0;
                while (i < length) {
                    String str = strArr[i];
                    if (group.regionMatches(true, 0, str, 0, str.length())) {
                        obj2 = 1;
                        if (!group.regionMatches(false, 0, str, 0, str.length())) {
                            group = str + group.substring(str.length());
                            obj = 1;
                            if (obj == null) {
                                group = strArr[0] + group;
                            }
                            ji_a.a = group;
                            ji_a.b = start;
                            ji_a.c = end;
                            arrayList.add(ji_a);
                        }
                        obj = obj2;
                        if (obj == null) {
                            group = strArr[0] + group;
                        }
                        ji_a.a = group;
                        ji_a.b = start;
                        ji_a.c = end;
                        arrayList.add(ji_a);
                    } else {
                        i++;
                    }
                }
                obj = obj2;
                if (obj == null) {
                    group = strArr[0] + group;
                }
                ji_a.a = group;
                ji_a.b = start;
                ji_a.c = end;
                arrayList.add(ji_a);
            }
        }
    }
}
