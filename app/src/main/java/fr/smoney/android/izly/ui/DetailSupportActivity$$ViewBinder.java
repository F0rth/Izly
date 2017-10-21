package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.IconTextView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class DetailSupportActivity$$ViewBinder<T extends DetailSupportActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.type = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.support_type_text, "field 'type'"), R.id.support_type_text, "field 'type'");
        t.crous = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.crous_text, "field 'crous'"), R.id.crous_text, "field 'crous'");
        t.studentId = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.student_id_text, "field 'studentId'"), R.id.student_id_text, "field 'studentId'");
        t.expDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.expiration_date_text, "field 'expDate'"), R.id.expiration_date_text, "field 'expDate'");
        t.oppositeDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.opposite_date_text, "field 'oppositeDate'"), R.id.opposite_date_text, "field 'oppositeDate'");
        t.oppositeMotif = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.opposite_motif_text, "field 'oppositeMotif'"), R.id.opposite_motif_text, "field 'oppositeMotif'");
        t.actionButton = (IconTextView) finder.castView((View) finder.findRequiredView(obj, R.id.b_action, "field 'actionButton'"), R.id.b_action, "field 'actionButton'");
        t.riseInstruction = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.rise_instruction, "field 'riseInstruction'"), R.id.rise_instruction, "field 'riseInstruction'");
        t.motifView = (View) finder.findRequiredView(obj, R.id.motif_layout, "field 'motifView'");
        t.motifSpinner = (Spinner) finder.castView((View) finder.findRequiredView(obj, R.id.sp_motif, "field 'motifSpinner'"), R.id.sp_motif, "field 'motifSpinner'");
    }

    public void unbind(T t) {
        t.type = null;
        t.crous = null;
        t.studentId = null;
        t.expDate = null;
        t.oppositeDate = null;
        t.oppositeMotif = null;
        t.actionButton = null;
        t.riseInstruction = null;
        t.motifView = null;
        t.motifSpinner = null;
    }
}
