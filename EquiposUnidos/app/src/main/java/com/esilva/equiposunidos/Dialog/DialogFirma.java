package com.esilva.equiposunidos.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.util.SignLayout;

import java.security.PublicKey;

public class DialogFirma extends Dialog {

    private Button btDialogSave,btDialogClean,btDialogCAncel;
    private LinearLayout firma;
    private SignLayout singLayout;

    private ListenerDialog listenerDialog;

    public interface ListenerDialog{
        void saveImageOK();
    }

    public DialogFirma(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparant);
        setCancelable(false);
        setContentView(R.layout.dialog_firma);

        firma = findViewById(R.id.canvaFirma);
        singLayout = new SignLayout(firma,context);

        btDialogSave = findViewById(R.id.btnFirmaOk);
        btDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(singLayout.isSignWritten()) {
                    singLayout.encodeSign();
                    listenerDialog.saveImageOK();
                    dismiss();
                }else {
                    Toast.makeText(context,"Por favor coloque una firma valida",Toast.LENGTH_LONG).show();
                }
            }
        });

        btDialogClean = findViewById(R.id.btnFirmaClean);
        btDialogClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singLayout.eraseCanvas();

            }
        });

        btDialogCAncel = findViewById(R.id.btnFirmaFail);
        btDialogCAncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    @Override
    public void show() {
        super.show();
    }
    public void setListenerDialog(ListenerDialog listener){
        this.listenerDialog = listener;
    }

}
