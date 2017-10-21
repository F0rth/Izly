package defpackage;

import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import fr.smoney.android.izly.R;

final class hl$b implements Callback {
    final /* synthetic */ hl a;
    private int b;

    public hl$b(hl hlVar, int i) {
        this.a = hlVar;
        this.b = i;
    }

    public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 1:
                if (!hl.a(this.a, this.a.f.b)) {
                    this.a.a(hu.a(this.a.getString(R.string.dialog_p2p_get_mult_delete_error_title), this.a.getString(R.string.dialog_p2p_get_mult_delete_error_message), this.a.getString(17039370)));
                    break;
                }
                this.a.a(ht.a(this.a.getString(R.string.dialog_p2p_get_mult_delete_confirm_title), this.a.getString(R.string.dialog_p2p_get_mult_delete_confirm_message), this.a.getString(17039379), this.a.getString(17039369), this.a, ie.P2PGetMultHideType));
                break;
        }
        actionMode.finish();
        return true;
    }

    public final boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        menu.add(0, 1, 1, R.string.p2p_get_list_action_delete).setIcon(R.drawable.pict_delete).setShowAsAction(1);
        return true;
    }

    public final void onDestroyActionMode(ActionMode actionMode) {
        this.a.i.a = -1;
        this.a.i.notifyDataSetChanged();
    }

    public final boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        this.a.i.a = this.b;
        this.a.i.notifyDataSetChanged();
        return true;
    }
}
