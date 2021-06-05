package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastrarActivity extends AppCompatActivity {

    private EditText nome;
    private EditText quantidade;
    private EditText quantidadeMin;
    private EditText preco;

    private EstoqueDAO dao;

    private Produtos produto = null;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastrar );

        //Capturando os inputs das views
        nome = findViewById( R.id.txt_nome );
        quantidade = findViewById( R.id.txt_qtd );
        quantidadeMin = findViewById( R.id.txt_minEstoque );
        preco = findViewById( R.id.txt_preco );

        //Fazendo instancia do DAO
        dao = new EstoqueDAO( this );

        //Capturando uma intent para verificação
        Intent it = getIntent();

        //Verificando se a intent possui a string produto - se Sim estão significa que é para atualizar.
        if (it.hasExtra( "produto" )) {
            //Capturando o produto para atualizar
            produto = (Produtos) it.getSerializableExtra( "produto" );

            //Preenchendo os campos de input
            nome.setText( produto.getNome() );
            quantidade.setText( produto.getQt_produtos() );
            quantidadeMin.setText( produto.getQt_min_estoque() );
            preco.setText( produto.getValor() );
        }

    }

    //Método para adicionar um novo produto ou atualizar
    public void adicionar (View v) {

        //Verificações para caso esteja vazio.
        if(quantidade.getText().length() <= 0){
            quantidade.setText( "0" );
        }
        if(quantidadeMin.getText().length() <= 0){
            quantidadeMin.setText( "0" );
        }
        if(preco.getText().length() <= 0){
            preco.setText( "0" );
        }

        //Verifica se é para adicionar um novo produto ou atualizar
        if(produto == null) {
            //Criando uma nova instancia de produto
            produto = new Produtos();

            //Adicionando valores dos inputs ao novo produto -- OBS: Todos precisam estar preenchidos:
            produto.setNome( nome.getText().toString() );
            produto.setQt_produtos( quantidade.getText().toString() );
            produto.setQt_min_estoque( quantidadeMin.getText().toString() );
            produto.setValor( preco.getText().toString() );

            //Chamando o dao para inserir novo produto
            String id = dao.inserirCompras( produto );
            Toast.makeText( this, "" + id , Toast.LENGTH_SHORT ).show();
        } else {
            //Adicionando valores do produto aos inputs
            produto.setNome( nome.getText().toString() );
            produto.setQt_produtos( quantidade.getText().toString() );
            produto.setQt_min_estoque( quantidadeMin.getText().toString() );
            produto.setValor( preco.getText().toString() );

            //Chamando o dao para atualizar o produto
            dao.atualizarCompra(produto);
            Toast.makeText( this, "Produto Atualizado." , Toast.LENGTH_SHORT ).show();
        }

        //Voltando para activity da lista de compras
        finish();
    }
    @Override
    public void onBackPressed(){
        finish();
    }

}