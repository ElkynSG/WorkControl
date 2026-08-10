package com.esilva.equiposunidos.Dialog;

import static com.esilva.equiposunidos.util.Constantes.PERFIL_AXILIAR;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_MANTENIMIENTO;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_NULL;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_OPERARIO_1;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_OPERARIO_2;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_SUPERADMIN;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_SUPER_NIVEL_1;
import static com.esilva.equiposunidos.util.Constantes.PERFIL_SUPER_NIVEL_2;
import static com.esilva.equiposunidos.util.Constantes.PER_AXILIAR;
import static com.esilva.equiposunidos.util.Constantes.PER_MANTE;
import static com.esilva.equiposunidos.util.Constantes.PER_OPER1;
import static com.esilva.equiposunidos.util.Constantes.PER_OPER2;
import static com.esilva.equiposunidos.util.Constantes.PER_SUPER;
import static com.esilva.equiposunidos.util.Constantes.PER_SUPER_NIVEL1;
import static com.esilva.equiposunidos.util.Constantes.PER_SUPER_NIVEL2;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.models.User;

public class DialogCreateUser extends Dialog {
    private TextView tvTitle;
    private TextView tvMessage;
    private ImageView imaDialog;
    private Button btDialogSi,btDialogNo;
    private EditText edName,edCedula,edImagen;
    private Spinner spPerfil,spCargo;
    Animation animation;
    private String name, cedula,imagen,perfil,cargo;
    private User user;

    public interface LisenerDailog{
        public void OnClickSI(User user);
    }

    private LisenerDailog lisenerDailog;
    public DialogCreateUser(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparant);
        setCancelable(false);
        setContentView(R.layout.dialog_create_user);

        edName  = findViewById(R.id.dgName);
        edCedula  = findViewById(R.id.dgCedula);
        edImagen  = findViewById(R.id.dgImagen);
        spPerfil  = findViewById(R.id.dgPerfil);
        spCargo  = findViewById(R.id.dgCargo);

        btDialogSi = findViewById(R.id.btn_dialog_crear);
        btDialogSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edName.getText().toString();
                cedula = edCedula.getText().toString().trim();
                imagen = edImagen.getText().toString();
                perfil = spPerfil.getSelectedItem().toString();
                cargo = spCargo.getSelectedItem().toString();
                if(validateData()) {
                    lisenerDailog.OnClickSI(user);
                    dismiss();
                }else{
                    Toast.makeText(getContext(),"Diligencie todos los campos",Toast.LENGTH_LONG).show();
                }

            }
        });

        btDialogNo = findViewById(R.id.btn_dialog_cancelar);
        btDialogNo.setOnClickListener(new View.OnClickListener() {
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

    public void setOnClickListener(LisenerDailog lisenerDailogIn ){
        lisenerDailog = lisenerDailogIn;
    }

    private boolean validateData(){

        if(name.isEmpty())
            return false;
        if (cedula.isEmpty())
            return false;
        if(imagen.isEmpty())
            return false;

        user = new User();
        user.setNombre(name);
        user.setCedula(Integer.valueOf(cedula));
        user.setFoto(imagen);
        user.setCargo(cargo);

        int perf;
        if(perfil.equals(PER_SUPER))
            user.setPerfil(PERFIL_SUPERADMIN);
        else if(perfil.equals(PER_MANTE))
            user.setPerfil(PERFIL_MANTENIMIENTO);
        else if(perfil.equals(PER_OPER1))
            user.setPerfil(PERFIL_OPERARIO_1);
        else if(perfil.equals(PER_OPER2))
            user.setPerfil(PERFIL_OPERARIO_2);
        else if(perfil.equals(PER_SUPER_NIVEL1))
            user.setPerfil(PERFIL_SUPER_NIVEL_1);
        else if(perfil.equals(PER_SUPER_NIVEL2))
            user.setPerfil(PERFIL_SUPER_NIVEL_2);
        else if(perfil.equals(PER_AXILIAR))
            user.setPerfil(PERFIL_AXILIAR);
        else
            user.setPerfil(PERFIL_NULL);

        return true;
    }
}
