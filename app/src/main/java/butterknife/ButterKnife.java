package butterknife;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.Property;
import android.view.View;
import butterknife.internal.ButterKnifeProcessor;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ButterKnife {
    static final Map<Class<?>, ViewBinder<Object>> BINDERS = new LinkedHashMap();
    static final ViewBinder<Object> NOP_VIEW_BINDER = new ViewBinder<Object>() {
        public final void bind(Finder finder, Object obj, Object obj2) {
        }

        public final void unbind(Object obj) {
        }
    };
    private static final String TAG = "ButterKnife";
    private static boolean debug = false;

    public interface ViewBinder<T> {
        void bind(Finder finder, T t, Object obj);

        void unbind(T t);
    }

    public interface Action<T extends View> {
        void apply(T t, int i);
    }

    public enum Finder {
        VIEW {
            protected final View findView(Object obj, int i) {
                return ((View) obj).findViewById(i);
            }

            public final Context getContext(Object obj) {
                return ((View) obj).getContext();
            }
        },
        ACTIVITY {
            protected final View findView(Object obj, int i) {
                return ((Activity) obj).findViewById(i);
            }

            public final Context getContext(Object obj) {
                return (Activity) obj;
            }
        },
        DIALOG {
            protected final View findView(Object obj, int i) {
                return ((Dialog) obj).findViewById(i);
            }

            public final Context getContext(Object obj) {
                return ((Dialog) obj).getContext();
            }
        };

        public static <T> T[] arrayOf(T... tArr) {
            return filterNull(tArr);
        }

        private static <T> T[] filterNull(T[] tArr) {
            int i = 0;
            for (T t : tArr) {
                if (t != null) {
                    tArr[i] = t;
                    i++;
                }
            }
            return Arrays.copyOfRange(tArr, 0, i);
        }

        public static <T> List<T> listOf(T... tArr) {
            return new ImmutableList(filterNull(tArr));
        }

        public <T> T castParam(Object obj, String str, int i, String str2, int i2) {
            return obj;
        }

        public <T> T castView(View view, int i, String str) {
            return view;
        }

        public <T> T findOptionalView(Object obj, int i, String str) {
            return castView(findView(obj, i), i, str);
        }

        public <T> T findRequiredView(Object obj, int i, String str) {
            T findOptionalView = findOptionalView(obj, i, str);
            if (findOptionalView != null) {
                return findOptionalView;
            }
            throw new IllegalStateException("Required view '" + getContext(obj).getResources().getResourceEntryName(i) + "' with ID " + i + " for " + str + " was not found. If this view is optional add '@Nullable' annotation.");
        }

        protected abstract View findView(Object obj, int i);

        public abstract Context getContext(Object obj);
    }

    public interface Setter<T extends View, V> {
        void set(T t, V v, int i);
    }

    private ButterKnife() {
        throw new AssertionError("No instances.");
    }

    @TargetApi(14)
    public static <T extends View, V> void apply(List<T> list, Property<? super T, V> property, V v) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            property.set(list.get(i), v);
        }
    }

    public static <T extends View> void apply(List<T> list, Action<? super T> action) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            action.apply((View) list.get(i), i);
        }
    }

    public static <T extends View, V> void apply(List<T> list, Setter<? super T, V> setter, V v) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            setter.set((View) list.get(i), v, i);
        }
    }

    public static void bind(Activity activity) {
        bind(activity, activity, Finder.ACTIVITY);
    }

    public static void bind(Dialog dialog) {
        bind(dialog, dialog, Finder.DIALOG);
    }

    public static void bind(View view) {
        bind(view, view, Finder.VIEW);
    }

    public static void bind(Object obj, Activity activity) {
        bind(obj, activity, Finder.ACTIVITY);
    }

    public static void bind(Object obj, Dialog dialog) {
        bind(obj, dialog, Finder.DIALOG);
    }

    public static void bind(Object obj, View view) {
        bind(obj, view, Finder.VIEW);
    }

    static void bind(Object obj, Object obj2, Finder finder) {
        Class cls = obj.getClass();
        try {
            if (debug) {
                Log.d(TAG, "Looking up view binder for " + cls.getName());
            }
            ViewBinder findViewBinderForClass = findViewBinderForClass(cls);
            if (findViewBinderForClass != null) {
                findViewBinderForClass.bind(finder, obj, obj2);
            }
        } catch (Throwable e) {
            throw new RuntimeException("Unable to bind views for " + cls.getName(), e);
        }
    }

    public static <T extends View> T findById(Activity activity, int i) {
        return activity.findViewById(i);
    }

    public static <T extends View> T findById(Dialog dialog, int i) {
        return dialog.findViewById(i);
    }

    public static <T extends View> T findById(View view, int i) {
        return view.findViewById(i);
    }

    private static ViewBinder<Object> findViewBinderForClass(Class<?> cls) throws IllegalAccessException, InstantiationException {
        ViewBinder<Object> viewBinder = (ViewBinder) BINDERS.get(cls);
        if (viewBinder == null) {
            String name = cls.getName();
            if (name.startsWith(ButterKnifeProcessor.ANDROID_PREFIX) || name.startsWith(ButterKnifeProcessor.JAVA_PREFIX)) {
                if (debug) {
                    Log.d(TAG, "MISS: Reached framework class. Abandoning search.");
                }
                return NOP_VIEW_BINDER;
            }
            try {
                viewBinder = (ViewBinder) Class.forName(name + ButterKnifeProcessor.SUFFIX).newInstance();
                if (debug) {
                    Log.d(TAG, "HIT: Loaded view binder class.");
                }
            } catch (ClassNotFoundException e) {
                if (debug) {
                    Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
                }
                viewBinder = findViewBinderForClass(cls.getSuperclass());
            }
            BINDERS.put(cls, viewBinder);
            return viewBinder;
        } else if (!debug) {
            return viewBinder;
        } else {
            Log.d(TAG, "HIT: Cached in view binder map.");
            return viewBinder;
        }
    }

    public static void setDebug(boolean z) {
        debug = z;
    }

    public static void unbind(Object obj) {
        Class cls = obj.getClass();
        try {
            if (debug) {
                Log.d(TAG, "Looking up view binder for " + cls.getName());
            }
            ViewBinder findViewBinderForClass = findViewBinderForClass(cls);
            if (findViewBinderForClass != null) {
                findViewBinderForClass.unbind(obj);
            }
        } catch (Throwable e) {
            throw new RuntimeException("Unable to unbind views for " + cls.getName(), e);
        }
    }
}
