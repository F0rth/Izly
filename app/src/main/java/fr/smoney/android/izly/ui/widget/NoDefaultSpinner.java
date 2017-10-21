package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class NoDefaultSpinner extends Spinner {

    public final class a implements InvocationHandler {
        protected SpinnerAdapter a;
        protected Method b;
        final /* synthetic */ NoDefaultSpinner c;

        protected a(NoDefaultSpinner noDefaultSpinner, SpinnerAdapter spinnerAdapter) {
            this.c = noDefaultSpinner;
            this.a = spinnerAdapter;
            try {
                this.b = SpinnerAdapter.class.getMethod("getView", new Class[]{Integer.TYPE, View.class, ViewGroup.class});
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            try {
                if (!method.equals(this.b) || ((Integer) objArr[0]).intValue() >= 0) {
                    return method.invoke(this.a, objArr);
                }
                int intValue = ((Integer) objArr[0]).intValue();
                View view = (View) objArr[1];
                ViewGroup viewGroup = (ViewGroup) objArr[2];
                if (intValue >= 0) {
                    return this.a.getView(intValue, view, viewGroup);
                }
                TextView textView = (TextView) ((LayoutInflater) this.c.getContext().getSystemService("layout_inflater")).inflate(17367048, viewGroup, false);
                textView.setText(this.c.getPrompt());
                return textView;
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public NoDefaultSpinner(Context context) {
        super(context);
    }

    public NoDefaultSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NoDefaultSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        Class[] clsArr = new Class[]{SpinnerAdapter.class};
        super.setAdapter((SpinnerAdapter) Proxy.newProxyInstance(spinnerAdapter.getClass().getClassLoader(), clsArr, new a(this, spinnerAdapter)));
        try {
            Method declaredMethod = AdapterView.class.getDeclaredMethod("setNextSelectedPositionInt", new Class[]{Integer.TYPE});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, new Object[]{Integer.valueOf(-1)});
            declaredMethod = AdapterView.class.getDeclaredMethod("setSelectedPositionInt", new Class[]{Integer.TYPE});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, new Object[]{Integer.valueOf(-1)});
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
