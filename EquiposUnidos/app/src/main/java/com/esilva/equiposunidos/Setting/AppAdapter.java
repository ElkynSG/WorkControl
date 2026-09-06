package com.esilva.equiposunidos.Setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esilva.equiposunidos.R;

import java.util.ArrayList;
public class AppAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<AppInfo> aplicaciones;

    public AppAdapter(Context context, ArrayList<AppInfo> aplicaciones) {
        this.context = context;
        this.aplicaciones = aplicaciones;
    }

    @Override
    public int getCount() {
        return aplicaciones.size();
    }

    @Override
    public Object getItem(int position) {
        return aplicaciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_app, parent, false);
        }

        ImageView imgIcono = convertView.findViewById(R.id.imgIcono);
        TextView txtNombre = convertView.findViewById(R.id.txtNombre);

        AppInfo app = aplicaciones.get(position);

        imgIcono.setImageDrawable(app.icono);
        txtNombre.setText(app.nombre);

        return convertView;
    }
}
