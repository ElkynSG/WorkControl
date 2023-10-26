package com.esilva.equiposunidos.Dialog;

import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.esilva.equiposunidos.R;

public class CustumerDialog extends Dialog {
    private TextView tvTitle;
    private TextView tvMessage;
    private ImageView imaDialog;
    private Button btDialog;
    Animation animation;
    public CustumerDialog(@NonNull Context context,String title,String message, Boolean color) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparant);
        setCancelable(false);
        setContentView(R.layout.custumer_dialog);
        tvTitle = findViewById(R.id.tvTitleDialog);
        tvTitle.setText(title);
        tvMessage = findViewById(R.id.tvMessageDialog);
        tvMessage.setText(message);
        imaDialog = findViewById(R.id.imageDialog);
        btDialog = findViewById(R.id.btn_dialog);
        if(color){
            tvTitle.setTextColor(context.getColor(R.color.rojo_dialog));
            tvMessage.setTextColor(context.getColor(R.color.rojo_dialog));
            imaDialog.setImageResource(R.drawable.fail);
            btDialog.setBackground(context.getDrawable(R.drawable.shape_button_dialog_rojo));
        }
        animation = AnimationUtils.loadAnimation(context,R.anim.app_item_zoomin);
        imaDialog.setAnimation(animation);

        btDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Override
    public void show() {
        super.show();
        imaDialog.startAnimation(animation);
    }
}
