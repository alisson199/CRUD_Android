package com.example.appestoque;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class ProdutosAdapter extends BaseAdapter {
    private List<Produtos> produtos;
    private Activity activity;

    ProdutosAdapter (List<Produtos> produtos, Activity activity) {
        this.activity = activity;
        this.produtos = produtos;
    }

    @Override
    public int getCount () {
        return produtos.size();
    }

    @Override
    public Object getItem (int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId (int position) {
        return produtos.get( position ).getId();
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View view =activity.getLayoutInflater().inflate( R.layout.item, parent,false);
        TextView nome = view.findViewById( R.id.lbl_nome ) ;
        TextView quantidade = view.findViewById( R.id.lbl_quantidade );
        TextView preco = view.findViewById( R.id.lbl_preco );

        Produtos p = produtos.get( position );

        String moeda = new DecimalFormat("#,##0.00").format(Double.parseDouble( p.getValor() ));

        nome.setText( p.getNome() );
        quantidade.setText( p.getQt_produtos() );
        preco.setText( "R$ " + moeda );;

        return view;
    }
}
