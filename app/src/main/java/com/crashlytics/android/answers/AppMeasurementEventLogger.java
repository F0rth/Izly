package com.crashlytics.android.answers;

import android.content.Context;
import android.os.Bundle;
import java.lang.reflect.Method;

public class AppMeasurementEventLogger implements EventLogger {
    private static final String ANALYTIC_CLASS = "com.google.android.gms.measurement.AppMeasurement";
    private static final String GET_INSTANCE_METHOD = "getInstance";
    private static final String LOG_METHOD = "logEventInternal";
    private final Object logEventInstance;
    private final Method logEventMethod;

    public AppMeasurementEventLogger(Object obj, Method method) {
        this.logEventInstance = obj;
        this.logEventMethod = method;
    }

    private static Class getClass(Context context) {
        try {
            return context.getClassLoader().loadClass(ANALYTIC_CLASS);
        } catch (Exception e) {
            return null;
        }
    }

    public static EventLogger getEventLogger(Context context) {
        Class cls = getClass(context);
        if (cls != null) {
            Object instance = getInstance(context, cls);
            if (instance != null) {
                Method logEventMethod = getLogEventMethod(context, cls);
                if (logEventMethod != null) {
                    return new AppMeasurementEventLogger(instance, logEventMethod);
                }
            }
        }
        return null;
    }

    private static Object getInstance(Context context, Class cls) {
        try {
            return cls.getDeclaredMethod(GET_INSTANCE_METHOD, new Class[]{Context.class}).invoke(cls, new Object[]{context});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method getLogEventMethod(Context context, Class cls) {
        try {
            return cls.getDeclaredMethod(LOG_METHOD, new Class[]{String.class, String.class, Bundle.class});
        } catch (Exception e) {
            return null;
        }
    }

    public void logEvent(String str, Bundle bundle) {
        logEvent("fab", str, bundle);
    }

    public void logEvent(String str, String str2, Bundle bundle) {
        try {
            this.logEventMethod.invoke(this.logEventInstance, new Object[]{str, str2, bundle});
        } catch (Exception e) {
        }
    }
}
