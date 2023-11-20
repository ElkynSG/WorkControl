package com.esilva.equiposunidos.Setting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.esilva.equiposunidos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManteniEquiposFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManteniEquiposFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public interface ItemListenerMante{
        void OnClickMaquinaria();
        void OnClickMantenimiento();
        void OnClickLugares();
        void OnClickTecnicos();
    }
    private ItemListenerMante listener;

    public ManteniEquiposFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManteniEquiposFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManteniEquiposFragment newInstance(String param1, String param2) {
        ManteniEquiposFragment fragment = new ManteniEquiposFragment();
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

        View view = inflater.inflate(R.layout.fragment_manteni_equipos, container, false);
        CardView cargar1 = view.findViewById(R.id.btCargaEquipos);
        cargar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickMaquinaria();
            }
        });
        CardView cargar2 = view.findViewById(R.id.btListaManteni);
        cargar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickMantenimiento();
            }
        });
        CardView cargar3 = view.findViewById(R.id.btListaLugar);
        cargar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickLugares();
            }
        });
        CardView carga4 = view.findViewById(R.id.btListaTecnico);
        carga4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickTecnicos();
            }
        });
        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof ItemListenerMante){
            listener = (ItemListenerMante) context;
        }else{
            throw new RuntimeException(context.toString()+"listener fragment");
        }

    }
}