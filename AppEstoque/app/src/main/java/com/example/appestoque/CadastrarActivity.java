package com.example.appestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        nome = findViewById( R.id.txt_nome );
        quantidade = findViewById( R.id.txt_qtd );
        quantidadeMin = findViewById( R.id.txt_minEstoque );
        preco = findViewById( R.id.txt_preco );

        dao = new EstoqueDAO( this );

        Intent it = getIntent();
        if (it.hasExtra( "produto" )) {
            produto = (Produtos) it.getSerializableExtra( "produto" );
            nome.setText( produto.getNome() );
            quantidade.setText( produto.getQt_produtos() );
            quantidadeMin.setText( produto.getQt_min_estoque() );
            preco.setText( produto.getValor() );
        }

    }

    public void adicionar (View v) {
        if(produto == null) {
            produto = new Produtos();
            produto.setNome( nome.getText().toString() );
            produto.setQt_produtos( quantidade.getText().toString() );
            produto.setQt_min_estoque( quantidadeMin.getText().toString() );
            produto.setValor( preco.getText().toString() );

            String id = dao.inserirCompras( produto );
            Toast.makeText( this, "" + id , Toast.LENGTH_SHORT ).show();
        } else {
            produto.setNome( nome.getText().toString() );
            produto.setQt_produtos( quantidade.getText().toString() );
            produto.setQt_min_estoque( quantidadeMin.getText().toString() );
            produto.setValor( preco.getText().toString() );

            dao.atualizarCompra(produto);
            Toast.makeText( this, "Produto Atualizado." , Toast.LENGTH_SHORT ).show();
        }


        Intent it = new Intent(this, ComprasActivity.class);
        startActivity( it );;
    }

}