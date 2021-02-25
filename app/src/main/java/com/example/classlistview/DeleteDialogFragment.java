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
    private Calendar cal;
    private DialogAction act;
    public DeleteDialogFragment()
    {
        super();
        cal = Calendar.getInstance();
    }
    public DeleteDialogFragment(DialogAction act)
    {
        this();
        this.act = act;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInst)
    {
        if(cal==null)
        {
            cal = Calendar.getInstance();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setNegativeButton("Отмена", null).setPositiveButton("Удалить", (dialog, which)->act.Do()).setMessage("Удалить?").create();
    }
}