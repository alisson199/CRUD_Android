package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RelatorioMensalActivity extends AppCompatActivity {

    String gastoMensal;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_relatorio_mensal );

        System.out.println("Mudou de guia...");
        Intent it = getIntent();
        for (int i = 0; i < 12; i++) {
            if (it.hasExtra( Integer.toString( i ) )) {
                gastoMensal = (String) it.getSerializableExtra( Integer.toString( i ) );
            }
        }
        System.out.println("Passou do for...");
        TextView gasto = findViewById( R.id.lbl_valorTotal );
        gasto.setText( gastoMensal );
    }
}