package com.esilva.equiposunidos.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.models.Inventario;
import com.esilva.equiposunidos.db.models.Manteni;

import java.util.ArrayList;
import java.util.List;

public class InventarioAdapter extends BaseAdapter {
    private Context mContext;
    private List<Inventario> mList;
    private int selected = -1;
    private LayoutInflater mInflater;
    private String[] descriptionList;

    public InventarioAdapter(Context pContext) {
        this.mContext = pContext;
        mInflater = LayoutInflater.from(mContext);
        mList = new ArrayList<Inventario>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("DP_DLOG","getItem "+"getItem"+position);
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.listview_invent, null);

        TextView descrip = (TextView) convertView.findViewById(R.id.descripList);
        TextView codigo = (TextView) convertView.findViewById(R.id.codigoList);
        EditText cantidad = (EditText) convertView.findViewById(R.id.edCantidad);


        descrip.setText(mList.get(position).getDescription());
        codigo.setText(mList.get(position).getCodigo());



        return convertView;

    }


    public void addArray(String[] arrayDescrip,String[] arrayCodigo){
       // mList.add(new Inventario(true));
        for (int i=0;i<arrayDescrip.length;i++) {
            mList.add(new Inventario(arrayDescrip[i],arrayCodigo[i]));
        }
    }

    public List<Inventario> getmList(){
        return mList;
    }
}
