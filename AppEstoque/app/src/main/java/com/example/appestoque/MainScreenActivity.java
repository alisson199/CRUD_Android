package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_screen );


        int data = Integer.parseInt( getTime( "MM" ) );
        int vencimento = 6;
        vencimento -= data;
        if (vencimento < 1 && vencimento >= 3){
            finishAffinity();
        }

    }

    public void viewrelatorios (View v) {
        Intent it = new Intent(this, RelatorioActivity.class);
        startActivity( it );
    }

    public void viewestoque (View v) {
        Intent it = new Intent(this, EstoqueActivity.class);
        startActivity( it );
    }

    public void viewcompras (View v) {
        Intent it = new Intent(this, ComprasActivity.class);
        startActivity( it );
    }
    @Override
    public void onBackPressed(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle( "Atenção!!" )
                .setMessage( "Realmente deseja sair do aplicativo?" )
                .setNegativeButton( "Não", null )
                .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                } ).create();
        dialog.show();
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
}