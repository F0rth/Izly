package defpackage;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import com.thewingitapp.thirdparties.wingitlib.model.WGEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class ab extends Adapter<ViewHolder> {
    protected List<WGEvent> a = new ArrayList();
    public ab$a b;

    public void a() {
        this.a.clear();
        notifyDataSetChanged();
    }

    public final void a(ab$a ab_a) {
        this.b = ab_a;
    }

    public final void a(List<WGEvent> list) {
        this.a.addAll(list);
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return this.a.size();
    }
}
