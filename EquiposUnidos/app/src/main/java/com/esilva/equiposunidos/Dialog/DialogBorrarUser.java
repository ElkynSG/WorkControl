package com.esilva.equiposunidos.Dialog;

import static com.esilva.equiposunidos.util.Constantes.FILE_IMAGE;
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
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DialogBorrarUser extends Dialog {
    private Button btDialogSi,btDialogNo;
    private TextView tvCedula;
    private Spinner spNombres;
    private String name, cedula,imagen,perfil,cargo;
    private User user;
    private List<User> users;
    private List<String> nombres;
    private AdminBaseDatos adminBaseDatos;

    public interface LisenerDailog{
        public void OnClickSI(boolean isOK);
    }

    private LisenerDailog lisenerDailog;
    public DialogBorrarUser(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparant);
        setCancelable(false);
        setContentView(R.layout.dialog_borrar_user);

        spNombres  = findViewById(R.id.dgNombre);
        tvCedula  = findViewById(R.id.dgcedul);

        adminBaseDatos = new AdminBaseDatos(getContext());
        users = adminBaseDatos.usu_getAll();
        if(users == null)
            return;
        nombres = new ArrayList<>();
        for (User user:users) {
            nombres.add(user.getNombre());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNombres.setAdapter(adapter);

        spNombres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvCedula.setText(String.valueOf(users.get(i).getCedula()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btDialogSi = findViewById(R.id.btn_dialog_borrar);
        btDialogSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = spNombres.getSelectedItem().toString();
                cedula = tvCedula.getText().toString();

                int i = adminBaseDatos.usu_deleteByUser(Integer.valueOf(cedula));
                if(i>0)
                    lisenerDailog.OnClickSI(true);
                else
                    lisenerDailog.OnClickSI(false);
                adminBaseDatos.closeBaseDtos();
                dismiss();

            }
        });

        btDialogNo = findViewById(R.id.btn_dialog_cancelar);
        btDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminBaseDatos.closeBaseDtos();
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

}
