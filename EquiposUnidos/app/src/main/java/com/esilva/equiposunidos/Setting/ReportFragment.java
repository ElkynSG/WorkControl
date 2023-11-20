package com.esilva.equiposunidos.Setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esilva.equiposunidos.Dialog.CustumerDialog;
import com.esilva.equiposunidos.Dialog.ProgressDialog;
import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.Report.ReportUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity context;

    private ProgressDialog progressDialog;

    public ReportFragment(Activity context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }
    public ReportFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        CardView btUser = view.findViewById(R.id.btGeneralUser);

        btUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Generando Reportes");
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ReportUser reportUser = new ReportUser(getContext());
                        boolean b = reportUser.buildReportCurrent();
                        showResult(b);
                    }
                }).start();

            }
        });

        return view;
    }

    private void showResult(boolean result){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                CustumerDialog custumerDialog;
                if(result)
                    custumerDialog = new CustumerDialog(context,"SUCCESS!","Reportes generado correctamente",!result,true);
                else
                    custumerDialog = new CustumerDialog(context,"FAIL!","Error generado el Reporte",!result,true);
                custumerDialog.show();
            }
        });

    }
}