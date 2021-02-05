package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RelatorioMensalActivity extends AppCompatActivity {

    String gastoMensal;

    private EstoqueDAO dao;
    private ListView listView;
    private List <Produtos> produtos;
    private List<Produtos> filtroprodutos = new ArrayList <>();
    private String mes;
    private String ano;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_relatorio_mensal );



        Intent it = getIntent();
        for (int i = 0; i < 12; i++) {
            if (it.hasExtra( Integer.toString( i ) )) {
                gastoMensal = (String) it.getSerializableExtra( Integer.toString( i ) );
                mes = "0" + Integer.toString( i );
            }
        }

        if(it.hasExtra( "ano" ))
            ano = (String) it.getSerializableExtra( "ano" );

        Log.d("meu app", "Este é o ano: " + ano + " e Este o mes: " + mes);

        listView = findViewById( R.id.listRM );
        dao = new EstoqueDAO( this );
        produtos = dao.obterRelatorios( mes,ano );
        filtroprodutos.addAll( produtos );

        ProdutosAdapter adaptador = new ProdutosAdapter( filtroprodutos, this );
        listView.setAdapter( adaptador );

        TextView gasto = findViewById( R.id.lbl_valorTotal );
        String moeda = new DecimalFormat("#,##0.00").format( Double.parseDouble( gastoMensal ));
        gasto.setText( moeda);

        listView.setAdapter( adaptador );
    }
    public void telainicial (View v) {
        finish();
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}