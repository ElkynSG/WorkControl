package com.esilva.equiposunidos.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.esilva.equiposunidos.R;

public class ProgressDialog extends Dialog {
    private Context context;
    private TextView textDialog;

    public ProgressDialog(@NonNull Context context,String message) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparant);
        setCancelable(false);
        setContentView(R.layout.dialog_progress);
        textDialog = findViewById(R.id.textDialog);
        textDialog.setText(message);

    }

    public ProgressDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparant);
        setCancelable(false);
        setContentView(R.layout.dialog_progress);
        textDialog = findViewById(R.id.textDialog);
    }

    public void setMessage(String msm){
        textDialog.setText(msm);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

}
