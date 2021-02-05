package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_screen );
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
                .setMessage( "Realmente deseja excluir todos os produtos do estoque?" )
                .setNegativeButton( "Não", null )
                .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                } ).create();
        dialog.show();
    }
}