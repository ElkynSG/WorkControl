package com.esilva.equiposunidos.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.models.DataInspeccion;
import com.esilva.equiposunidos.db.models.Manteni;

import java.util.ArrayList;
import java.util.List;

public class InspeccionAdapter extends BaseAdapter {
    private Context mContext;
    private List<DataInspeccion> mList;
    private int selected = -1;
    private LayoutInflater mInflater;
    private String[] descriptionList;

    public InspeccionAdapter(Context pContext) {
        this.mContext = pContext;
        mInflater = LayoutInflater.from(mContext);
        mList = new ArrayList<DataInspeccion>();
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

        convertView = mInflater.inflate(R.layout.listview_inspec, null);

        TextView descrip = (TextView) convertView.findViewById(R.id.tvDescripList);
        TextView msg = (TextView) convertView.findViewById(R.id.tvMsgList);
        CheckBox che_B = (CheckBox) convertView.findViewById(R.id.cheList1);
        CheckBox che_M = (CheckBox) convertView.findViewById(R.id.cheList2);

        che_B.setChecked(mList.get(position).isPosB());
        che_M.setChecked(mList.get(position).isPosM());

        msg.setText(mList.get(position).getStPare());
        descrip.setText(mList.get(position).getDescription());

        if(mList.get(position).isCheckRojo()){
            msg.setBackgroundColor(mContext.getColor(mList.get(position).getColor()==2?R.color.rojo:R.color.verde));
            msg.setTextColor(mContext.getColor(R.color.white));
        }else {
            if(mList.get(position).getColor()!= 0){
                msg.setBackgroundColor(mContext.getColor(R.color.naranja));

            }
        }

        che_B.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    DataInspeccion inpec = mList.get(position);
                    che_M.setChecked(false);
                    inpec.setPosB(true);
                    inpec.setPosM(false);


                    Log.d("DP_DLOG","onCheckedChanged "+"pos si "+position);
                    if(mList.get(position).isCheckRojo()){
                        msg.setBackgroundColor(mContext.getColor(R.color.verde));
                        msg.setText("OK");
                        inpec.setStPare("OK");
                        inpec.setColor(1);
                    }else {
                        msg.setBackgroundColor(mContext.getColor(R.color.white));
                        msg.setText("");
                        inpec.setStPare("");
                        inpec.setColor(0);
                    }


                    mList.set(position,inpec);
                }
            }
        });

        che_M.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    DataInspeccion inpec = mList.get(position);
                    che_B.setChecked(false);
                    inpec.setPosB(false);
                    inpec.setPosM(true);


                    Log.d("DP_DLOG","onCheckedChanged "+"pos na "+position);
                    if(mList.get(position).isCheckRojo()){
                        msg.setBackgroundColor(mContext.getColor(R.color.rojo));
                        msg.setText("PARE");
                        inpec.setColor(2);
                        inpec.setStPare("PARE");
                    }else {
                        msg.setBackgroundColor(mContext.getColor(R.color.naranja));
                        msg.setText("OBS");
                        inpec.setColor(2);
                        inpec.setStPare("OBS");
                    }
                    mList.set(position,inpec);
                }
            }
        });



        return convertView;

    }
    public void notifyDataSetChanged(int id) {
        selected = id;
        super.notifyDataSetChanged();
    }

    public void addArray(boolean[] rojos,String[] array){
        for (int i=0;i<rojos.length;i++) {
            mList.add(new DataInspeccion(array[i],rojos[i]));
        }
    }

    public List<DataInspeccion> getmList(){
        return mList;
    }
}
