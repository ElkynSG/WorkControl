package com.esilva.equiposunidos.Dialog;

import android.app.Dialog;
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

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class CustumerDialogButton extends Dialog {
    private TextView tvTitle;
    private TextView tvMessage;
    private ImageView imaDialog;
    private Button btDialogSi,btDialogNo;
    Animation animation;

    public interface LisenerDailog{
        public void OnClickSI();
        public void OnClickNO();
    }

    private LisenerDailog lisenerDailog;
    public CustumerDialogButton(@NonNull Context context, String Title,String message) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparant);
        setCancelable(false);
        setContentView(R.layout.custumer_dialog_button);
        tvTitle = findViewById(R.id.tvTitleDialogBt);
        tvTitle.setText(Title);
        tvMessage = findViewById(R.id.tvMessageDialogBt);
        tvMessage.setText(message);
        btDialogSi = findViewById(R.id.btn_dialog_si);
        btDialogSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisenerDailog.OnClickSI();
                dismiss();
            }
        });

        btDialogNo = findViewById(R.id.btn_dialog_no);
        btDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisenerDailog.OnClickNO();
                dismiss();
            }
        });

        animation = AnimationUtils.loadAnimation(context,R.anim.app_item_zoomin);
        tvTitle.setAnimation(animation);

    }

    @Override
    public void show() {
        super.show();
        tvTitle.startAnimation(animation);
    }

    public void setOnClickListener(LisenerDailog lisenerDailogIn ){
        lisenerDailog = lisenerDailogIn;
    }
}
