package com.esilva.equiposunidos.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.DialogBorrarUser;
import com.esilva.equiposunidos.Dialog.DialogCreateUser;
import com.esilva.equiposunidos.MainActivity;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.RegistroInOut.FormRegisterInOutActivity;
import com.esilva.equiposunidos.db.AdminBaseDatos;
import com.esilva.equiposunidos.db.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean state;

    public interface ItemListener{
        void OnClickCargar();
        void OnClickEnrolar();
        void OnClickActividad();
        void OnClickOperUser();
    }
    private ItemListener listener;
    public UserFragment() {
        state = true;
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        View view1 =  view.findViewById(R.id.view1);
        View view2 =  view.findViewById(R.id.view2);
        CardView btCrear = view.findViewById(R.id.usuarioCrear);
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogCreateUser dialogCreateUser = new DialogCreateUser(getContext());
                dialogCreateUser.setOnClickListener(new DialogCreateUser.LisenerDailog() {
                    @Override
                    public void OnClickSI(User user) {
                        AdminBaseDatos adminBaseDatos = new AdminBaseDatos(getContext());
                        long l = adminBaseDatos.usu_insert(user);
                        if(l>0){
                            messageOK(true);
                        }else{
                            messageOK(false);
                        }
                    }

                });
                dialogCreateUser.show();
                //listener.OnClickOperUser();
            }
        });
        CardView btBorrar = view.findViewById(R.id.usuarioBorrar);
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBorrarUser dialogBorrarUser = new DialogBorrarUser(getContext());
                dialogBorrarUser.setOnClickListener(new DialogBorrarUser.LisenerDailog() {
                    @Override
                    public void OnClickSI(boolean isOK) {
                        messageOKBorra(isOK);
                    }
                });
                dialogBorrarUser.show();
            }
        });

        CardView cargar = view.findViewById(R.id.registrarUsuarios);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btCrear.setVisibility(View.GONE);
                btBorrar.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                listener.OnClickCargar();
            }
        });
        CardView enrolar = view.findViewById(R.id.enrolarUsuarios);
        enrolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btCrear.setVisibility(View.GONE);
                btBorrar.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                listener.OnClickEnrolar();
            }
        });
        CardView actividad = view.findViewById(R.id.cargarActividad);
        actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btCrear.setVisibility(View.GONE);
                btBorrar.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                listener.OnClickActividad();
            }
        });



        CardView reporte = view.findViewById(R.id.operacionUsuario);
        reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state) {
                    btCrear.setVisibility(View.VISIBLE);
                    btBorrar.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    state=false;
                }else{
                    btCrear.setVisibility(View.GONE);
                    btBorrar.setVisibility(View.GONE);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    state=true;
                }
            }
        });



        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof ItemListener){
            listener = (ItemListener) context;
        }else{
            throw new RuntimeException(context.toString()+"listener fragment");
        }

    }

    private void messageOK(boolean isOK){
        CustumerDialog custumerDialog;
        if(isOK)
            custumerDialog = new CustumerDialog(getContext(),"SUCCESS!", "Usuario creado",false,false);
        else
            custumerDialog = new CustumerDialog(getContext(),"FAIL!", "Error Creando usuario",true,false);

        custumerDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                custumerDialog.dismiss();
                //startActivity(new Intent(FormRegisterInOutActivity.this, MainActivity.class));

            }
        },2000);
    }

    private void messageOKBorra(boolean isOK){
        CustumerDialog custumerDialog;
        if(isOK)
            custumerDialog = new CustumerDialog(getContext(),"SUCCESS!", "Usuario borrado",false,false);
        else
            custumerDialog = new CustumerDialog(getContext(),"FAIL!", "Error Borrando usuario",true,false);

        custumerDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                custumerDialog.dismiss();
                //startActivity(new Intent(FormRegisterInOutActivity.this, MainActivity.class));

            }
        },2000);
    }




}