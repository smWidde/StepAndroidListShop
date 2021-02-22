package com.example.classlistview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class DeleteDialogFragment extends DialogFragment {
    private Calendar cal = Calendar.getInstance();
    private DialogAction act;
    public DeleteDialogFragment()
    {
        super();
    }
    public DeleteDialogFragment(DialogAction act)
    {
        super();
        this.act = act;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInst)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setNegativeButton("Отмена", null).setPositiveButton("Купить", (dialog, which)->act.Do()).setMessage("Купить?").create();
    }
}
