package com.example.appestoque;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class RelatoriosAdapter extends BaseAdapter {
    private List<String> datas;
    private Activity activity;

    RelatoriosAdapter (List<String> datas, Activity activity) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public int getCount () {
        return datas.size();
    }

    @Override
    public Object getItem (int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View view =activity.getLayoutInflater().inflate( R.layout.item_relatorio, parent,false);
        Button mes = view.findViewById( R.id.btn_mes );

        String d = datas.get( position );

        mes.setText( d );

        return view;
    }
}