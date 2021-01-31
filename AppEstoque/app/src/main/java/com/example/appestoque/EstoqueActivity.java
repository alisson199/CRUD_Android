package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

        //Capturando a list view
        listView = findViewById( R.id.listEstoque );
        //Instanciando um dao
        dao = new EstoqueDAO( this );
        //Capturando estoque do dao
        produtos = dao.obterEstoque();
        //Criando um menu de contexto
        registerForContextMenu( listView );
        //Adicionando todos os produtos obtidos do dao, ao filtro
        filtroprodutos.addAll( produtos );
        //Adaptando a matriz para inserir na lista de uma maneira mais visual
        ProdutosAdapter adaptador = new ProdutosAdapter( filtroprodutos, this  );
        listView.setAdapter( adaptador );
    }

    //Método para inflar o menu ao pressionar a tela -- faz aparecer o menu...
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu( menu, v, menuInfo );
        MenuInflater i = getMenuInflater();
        i.inflate( R.menu.menu_contexto_deletar, menu );
    }

    //Método para deletar um produto do estoque
    public void deletar (MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Produtos produtoExcluir = filtroprodutos.get(menuInfo.position);

        //Confirmando se o usuario realmente deseja deletar o produto
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle( "Atenção!!" )
                .setMessage( "Realmente deseja excluir o produto?" )
                .setNegativeButton( "Não", null )
                .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialogInterface, int i) {
                        //Se o usuario clicar em sim, remove o produto da lista e chama o dao, chama o invalidate view para atualizar a lista
                        filtroprodutos.remove( produtoExcluir );
                        produtos.remove( produtoExcluir );
                        dao.excluirEstoque( produtoExcluir );
                        listView.invalidateViews();
                    }
                } ).create();
        dialog.show();
    }

    public void retirarUn (MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Produtos produtoRetirar = filtroprodutos.get( menuInfo.position );

        Toast.makeText( this, "Nome do produto: " + produtoRetirar.getNome() ,Toast.LENGTH_SHORT ).show();

        int diminui = Integer.parseInt( produtoRetirar.getQt_produtos() ) -1;

        if (diminui < 0)
            diminui = 0;



        produtoRetirar.setQt_produtos( Integer.toString( diminui ) );
        dao.retirarUnEstoque( produtoRetirar );

        produtos = dao.obterEstoque();
        filtroprodutos.clear();
        filtroprodutos.addAll( produtos );
        listView.invalidateViews();

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

    @Override
    public void onResume () {
        super.onResume();
        produtos = dao.obterEstoque();
        filtroprodutos.clear();
        filtroprodutos.addAll( produtos );
        listView.invalidateViews();
    }

    public void telainicial (View v) {
        Intent it = new Intent(this, MainScreenActivity.class);
        startActivity( it );
    }
}