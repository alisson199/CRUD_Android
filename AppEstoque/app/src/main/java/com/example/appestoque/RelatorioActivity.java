package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RelatorioActivity extends AppCompatActivity {

    EstoqueDAO dao;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_relatorio );

       dao = new EstoqueDAO( this );
    }

    public String ano () {
        TextView a = findViewById( R.id.lbl_ano );
        return (String) a.getText();
    }

    public void mes1 (View v) {
        String gastoMensal = dao.obterTotalMensal( "01", ano() );

        Intent it = new Intent(this, RelatorioMensalActivity.class);
        it.putExtra( "1", gastoMensal );
        System.out.println("Fez o putExtra...");
        startActivity( it );
    }
}