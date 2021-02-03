package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ComprasActivity extends AppCompatActivity {

    private ListView listView;
    private EstoqueDAO dao;

    private List<Produtos> produtos;
    private List<Produtos> filtroprodutos = new ArrayList <>();

    private TextView totpreco;
    private double valortotal;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_compras );

        listView = findViewById( R.id.listCompras );
        dao = new EstoqueDAO( this );
        produtos = dao.obterCompras();
        valortotal = 0;
        totpreco = findViewById( R.id.lbl_totPreco );

        filtroprodutos.addAll( produtos );
        ProdutosAdapter adaptador = new ProdutosAdapter( filtroprodutos, this  );
        listView.setAdapter( adaptador );
        registerForContextMenu( listView );

        atualizaTotal();

    }

    public void cadastrar (View v) {
        Intent it = new Intent(this, CadastrarActivity.class);
        startActivity( it );
    }

    public void comprar (View v) {
        String data = getTime( "YYYY/MM" );

        for(int i = 0; i < filtroprodutos.size(); i ++) {
            dao.inserirEstoque( filtroprodutos.get( i ) );
            dao.inserirRelatorios( filtroprodutos.get( i ), data );
        }
        valortotal = 0;
            if(dao.temItem()>0){
            dao.deletarTodaCompras();
            Toast.makeText( this,"Compra realizada!",Toast.LENGTH_SHORT ).show();
            Intent it = new Intent(this, MainScreenActivity.class);
            startActivity( it );
        } else
            {
                Toast.makeText( this,"Não há itens na lista de compras!",Toast.LENGTH_LONG ).show();
            }
    }

    @Override
    public void onResume () {
        super.onResume();
        produtos = dao.obterCompras();
        filtroprodutos.clear();
        filtroprodutos.addAll( produtos );
        listView.invalidateViews();
        valortotal = 0;
        atualizaTotal();
    }
    // ********** Sobre o menu de contexto
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu( menu, v, menuInfo );
        MenuInflater i = getMenuInflater();
        i.inflate( R.menu.menu_contexto, menu );
    }

    public void excluir (MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Produtos produtoExcluir = filtroprodutos.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle( "Atenção!!" )
                .setMessage( "Realmente deseja excluir o produto?" )
                .setNegativeButton( "Não", null )
                .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        filtroprodutos.remove( produtoExcluir );
                        produtos.remove( produtoExcluir );
                        dao.excluirCompra( produtoExcluir );
                        valortotal = 0;
                        atualizaTotal();
                        listView.invalidateViews();
                    }
                } ).create();
        dialog.show();
    }

    public void atualizar (MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Produtos produtoAtualizar = filtroprodutos.get(menuInfo.position);
        Intent it = new Intent(this, CadastrarActivity.class);
        it.putExtra( "produto", produtoAtualizar );
        startActivity( it );
    }

    private void atualizaTotal () {
        for(int i = 0; i < filtroprodutos.size(); i ++) {
            valortotal += Double.parseDouble( filtroprodutos.get( i ).getValor() ) * Double.parseDouble( filtroprodutos.get( i ).getQt_produtos() ) ;
        }
        String moeda = new DecimalFormat("#,##0.00").format(valortotal );
        totpreco.setText( "R$" + moeda );
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

    public void telainicial (View v) {
        Intent it = new Intent(this, MainScreenActivity.class);
        startActivity( it );
    }
}