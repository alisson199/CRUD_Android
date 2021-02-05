package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public class RelatorioActivity extends AppCompatActivity {

    private EstoqueDAO dao;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_relatorio );


        dao = new EstoqueDAO( this );

        TextView textoano = findViewById( R.id.lbl_ano );

        textoano.setText( getTime( "YYYY" ) );


    }
    private static String getTime(String format){

        if (format.isEmpty()) {
            throw new NullPointerException("A pattern não pode ser NULL!");
        }

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat formato = new SimpleDateFormat(format);
        Date data = calendar.getTime();

        return formato.format(data);
    }

    public String ano () {
        TextView a = findViewById( R.id.lbl_ano ); //Captura a label ano...
        return (String) a.getText(); // retorna o texto dela..
    }

    public void mes1 (View v) {
        String gastoMensal = dao.obterTotalMensal( "01", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "1", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes2 (View v) {
        String gastoMensal = dao.obterTotalMensal( "02", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "2", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes3 (View v) {
        String gastoMensal = dao.obterTotalMensal( "03", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "3", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes4 (View v) {
        String gastoMensal = dao.obterTotalMensal( "04", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "4", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes5 (View v) {
        String gastoMensal = dao.obterTotalMensal( "05", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "5", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes6 (View v) {
        String gastoMensal = dao.obterTotalMensal( "06", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "6", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes7 (View v) {
        String gastoMensal = dao.obterTotalMensal( "07", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "7", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes8 (View v) {
        String gastoMensal = dao.obterTotalMensal( "08", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "8", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes9 (View v) {
        String gastoMensal = dao.obterTotalMensal( "09", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "9", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes10 (View v) {
        String gastoMensal = dao.obterTotalMensal( "10", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "10", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes11 (View v) {
        String gastoMensal = dao.obterTotalMensal( "11", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "11", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void mes12 (View v) {
        String gastoMensal = dao.obterTotalMensal( "12", ano() );
        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "12", gastoMensal );
        it.putExtra( "ano", ano() );
        startActivity( it );
    }

    public void telainicial (View v) {
        finish();
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}