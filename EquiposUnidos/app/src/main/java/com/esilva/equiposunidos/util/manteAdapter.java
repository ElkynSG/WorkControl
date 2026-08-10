package com.esilva.equiposunidos.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esilva.equiposunidos.R;
import com.esilva.equiposunidos.db.models.Manteni;

import java.util.ArrayList;
import java.util.List;

public class ManteAdapter extends BaseAdapter {
    private Context mContext;
    private List<Manteni> mList;
    private int selected = -1;
    private LayoutInflater mInflater;
    private String[] descriptionList;

    public ManteAdapter(Context pContext) {
        this.mContext = pContext;
        mInflater = LayoutInflater.from(mContext);
        mList = new ArrayList<Manteni>();
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

        convertView = mInflater.inflate(R.layout.listview_mante, null);

        TextView descrip = (TextView) convertView.findViewById(R.id.descriptionList);

        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1List);
        TextView tv2 = (TextView) convertView.findViewById(R.id.tv2List);
        TextView tv3 = (TextView) convertView.findViewById(R.id.tv3List);

        CheckBox cheSI = (CheckBox) convertView.findViewById(R.id.che1List);
        CheckBox cheNO = (CheckBox) convertView.findViewById(R.id.che2List);
        CheckBox cheNA = (CheckBox) convertView.findViewById(R.id.che3List);

        cheSI.setChecked(mList.get(position).isbSI());
        cheNO.setChecked(mList.get(position).isbNO());
        cheNA.setChecked(mList.get(position).isbNA());

        descrip.setText(mList.get(position).getDescrition());
        if(mList.get(position).isbTitle()){
            descrip.setBackgroundColor(mContext.getColor(R.color.color_logo));
            descrip.setTextColor(mContext.getColor(R.color.white));
            descrip.setTextSize(20);
            cheSI.setVisibility(View.GONE);
            cheNO.setVisibility(View.GONE);
            cheNA.setVisibility(View.GONE);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
        }
        //cheSI.setChecked(mList.get(position).isbSI());
        //cheNA.setChecked(mList.get(position).isbNA());

        cheNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Manteni manteni = mList.get(position);
                    cheSI.setChecked(false);
                    cheNA.setChecked(false);
                    manteni.setbNO(true);
                    manteni.setbSI(false);
                    manteni.setbNA(false);
                    mList.set(position,manteni);
                    Log.d("DP_DLOG","onCheckedChanged "+"pos no "+position);
                }
            }
        });

        cheSI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Manteni manteni = mList.get(position);
                    cheNO.setChecked(false);
                    cheNA.setChecked(false);
                    manteni.setbNO(false);
                    manteni.setbSI(true);
                    manteni.setbNA(false);
                    mList.set(position,manteni);
                    Log.d("DP_DLOG","onCheckedChanged "+"pos si "+position);
                }
            }
        });

        cheNA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Manteni manteni = mList.get(position);
                    cheSI.setChecked(false);
                    cheNO.setChecked(false);
                    manteni.setbNO(false);
                    manteni.setbSI(false);
                    manteni.setbNA(true);
                    mList.set(position,manteni);
                    Log.d("DP_DLOG","onCheckedChanged "+"pos na "+position);
                }
            }
        });

        return convertView;

    }
    public void notifyDataSetChanged(int id) {
        selected = id;
        super.notifyDataSetChanged();
    }

    public void addArray(String title,String[] array){
        mList.add(new Manteni(title,true));
        for (String st:array) {
            mList.add(new Manteni(st));
        }
    }

    public List<Manteni> getmList(){
        return mList;
    }
}
