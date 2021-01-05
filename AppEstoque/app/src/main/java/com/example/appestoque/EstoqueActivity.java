package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EstoqueActivity extends AppCompatActivity {

    private ListView listView;
    private EstoqueDAO dao;
    private List<Produtos> produtos;
    private List<Produtos> filtroprodutos = new ArrayList <>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_estoque );

        listView = findViewById( R.id.listEstoque );
        dao = new EstoqueDAO( this );
        produtos = dao.obterEstoque();
        registerForContextMenu( listView );

        filtroprodutos.addAll( produtos );
        ProdutosAdapter adaptador = new ProdutosAdapter( filtroprodutos, this  );
        listView.setAdapter( adaptador );
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu( menu, v, menuInfo );
        MenuInflater i = getMenuInflater();
        i.inflate( R.menu.menu_contexto_deletar, menu );
    }

    public void deletar (MenuItem item) {
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
                        dao.excluirEstoque( produtoExcluir );
                        listView.invalidateViews();
                    }
                } ).create();
        dialog.show();
    }
    public void deletartudo (View v) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle( "Atenção!!" )
                .setMessage( "Realmente deseja excluir todos os produtos do estoque?" )
                .setNegativeButton( "Não", null )
                .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        filtroprodutos.clear();
                        produtos.clear();
                        dao.deletarTodoEstoque();
                        listView.invalidateViews();
                    }
                } ).create();
        dialog.show();
    }


}