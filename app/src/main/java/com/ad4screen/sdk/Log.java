package com.ad4screen.sdk;

import com.ad4screen.sdk.common.annotations.API;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

@API
public final class Log {
    public static final String TAG = "com.ad4screen.sdk";
    private static boolean a;
    private static boolean b = false;

    private Log() {
    }

    private static void a(int i, String str) {
        int i2 = 0;
        while (i2 < str.length()) {
            int i3 = i2 + 1000;
            if (i3 > str.length()) {
                i3 = str.length();
            }
            android.util.Log.println(i, "com.ad4screen.sdk", str.substring(i2, i3));
            i2 = i3;
        }
    }

    private static void a(int i, String str, JSONArray jSONArray) {
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                Object obj = jSONArray.get(i2);
                if (obj instanceof JSONArray) {
                    a(i, str, (JSONArray) obj);
                } else if (obj instanceof JSONObject) {
                    a(i, str, (JSONObject) obj);
                } else {
                    b(i, str + "|" + obj);
                }
            } catch (Throwable e) {
                error(str + "|Can't properly log array value for index : " + i2, e);
            }
        }
    }

    private static void a(int i, String str, JSONObject jSONObject) {
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            try {
                if (jSONObject.get(str2) instanceof JSONObject) {
                    a(i, str, (JSONObject) jSONObject.get(str2));
                } else if (jSONObject.get(str2) instanceof JSONArray) {
                    b(i, str + "|Sending : " + str2 + " with value : ");
                    a(i, str, (JSONArray) jSONObject.get(str2));
                } else {
                    b(i, str + "|Sending : " + str2 + " with value : " + jSONObject.get(str2));
                }
            } catch (Throwable e) {
                error(str + "|Can't properly log value for key : " + str2, e);
            }
        }
    }

    private static void b(int i, String str) {
        if (i == 6) {
            error(str);
        } else if (i == 5) {
            warn(str);
        } else if (i == 4) {
            info(str);
        } else if (i == 3) {
            debug(str);
        } else if (i == 2) {
            verbose(str);
        } else {
            internal(str);
        }
    }

    public static void debug(String str) {
        if (a) {
            a(3, "A4S|DEBUG|" + str);
        }
    }

    public static void debug(String str, Throwable th) {
        if (a) {
            a(3, "A4S|DEBUG|" + str + "\n" + android.util.Log.getStackTraceString(th));
        }
    }

    public static void debug(String str, JSONArray jSONArray) {
        if (a) {
            a(3, str, jSONArray);
        }
    }

    public static void debug(String str, JSONObject jSONObject) {
        if (a) {
            a(3, str, jSONObject);
        }
    }

    public static void error(String str) {
        if (a) {
            a(6, "A4S|ERROR|" + str);
        }
    }

    public static void error(String str, Throwable th) {
        if (a) {
            a(6, "A4S|ERROR|" + str + "\n" + android.util.Log.getStackTraceString(th));
        }
    }

    public static void error(String str, JSONArray jSONArray) {
        if (a) {
            a(6, str, jSONArray);
        }
    }

    public static void error(String str, JSONObject jSONObject) {
        if (a) {
            a(6, str, jSONObject);
        }
    }

    public static void info(String str) {
        if (a) {
            a(4, "A4S|INFO|" + str);
        }
    }

    public static void info(String str, Throwable th) {
        if (a) {
            a(4, "A4S|INFO|" + str + "\n" + android.util.Log.getStackTraceString(th));
        }
    }

    public static void info(String str, JSONArray jSONArray) {
        if (a) {
            a(4, str, jSONArray);
        }
    }

    public static void info(String str, JSONObject jSONObject) {
        if (a) {
            a(4, str, jSONObject);
        }
    }

    public static void internal(String str) {
        if (b && a) {
            a(2, "A4S|INTERNAL|" + str);
        }
    }

    public static void internal(String str, Throwable th) {
        if (b && a) {
            a(2, "A4S|INTERNAL|" + str + "\n" + android.util.Log.getStackTraceString(th));
        }
    }

    public static void internal(String str, JSONArray jSONArray) {
        if (b && a) {
            a(2, str, jSONArray);
        }
    }

    public static void internal(String str, JSONObject jSONObject) {
        if (b && a) {
            a(2, str, jSONObject);
        }
    }

    public static void internal(Throwable th) {
        if (b && a) {
            a(2, "A4S|INTERNAL|" + android.util.Log.getStackTraceString(th));
        }
    }

    public static void setEnabled(boolean z) {
        a = z;
    }

    public static void setInternalLoggingEnabled(boolean z) {
        b = z;
    }

    public static void verbose(String str) {
        if (a) {
            a(2, "A4S|VERBOSE|" + str);
        }
    }

    public static void verbose(String str, JSONArray jSONArray) {
        if (a) {
            a(2, str, jSONArray);
        }
    }

    public static void verbose(String str, JSONObject jSONObject) {
        if (a) {
            a(2, str, jSONObject);
        }
    }

    public static void warn(String str) {
        if (a) {
            a(5, "A4S|WARNING|" + str);
        }
    }

    public static void warn(String str, Throwable th) {
        if (a) {
            a(5, "A4S|WARNING|" + str + "\n" + android.util.Log.getStackTraceString(th));
        }
    }

    public static void warn(String str, JSONArray jSONArray) {
        if (a) {
            a(5, str, jSONArray);
        }
    }

    public static void warn(String str, JSONObject jSONObject) {
        if (a) {
            a(5, str, jSONObject);
        }
    }
}
