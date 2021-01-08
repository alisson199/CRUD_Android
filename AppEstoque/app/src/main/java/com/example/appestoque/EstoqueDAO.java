package com.example.appestoque;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {
    private ConexaoBD conexao;
    private SQLiteDatabase banco;

    public EstoqueDAO(Context context) {
        conexao = new ConexaoBD( context );
        banco = conexao.getWritableDatabase();
    }

    public String inserirEstoque (Produtos produtos) {
        ContentValues values = new ContentValues();
        values.put( "nm_produtos", produtos.getNome() );
        values.put( "qt_produtos", produtos.getQt_produtos());
        values.put( "qt_min_produtos", produtos.getQt_min_estoque() );
        values.put( "preco", produtos.getValor() );

        long resultado = banco.insert( "tb_produtos", null, values );

        if (resultado == -1)
            return "Dado não inserido";
        else
            return "Dado inserido com sucesso";
    }

    public String inserirCompras (Produtos produtos) {
        ContentValues values = new ContentValues();

        values.put( "nm_produto", produtos.getNome() );
        values.put( "qt_produto", produtos.getQt_produtos() );
        values.put( "qt_min_produto", produtos.getQt_min_estoque() );
        values.put( "valor", produtos.getValor() );


        long resultado = banco.insert( "tb_ListaCompras", null, values );
        if (resultado == -1)
            return "Erro ao inserir dado";
        else
            return "Dado inserido com sucesso";
    }

    public List<Produtos> obterEstoque () {
        List<Produtos> produtos = new ArrayList <>();
        Cursor cursor = banco.query( "tb_produtos", new String[]{"id", "nm_produtos", "qt_produtos", "qt_min_produtos", "preco"},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Produtos p = new Produtos();
            p.setId( cursor.getInt( 0 ) );
            p.setNome( cursor.getString( 1 ) );
            p.setQt_produtos( cursor.getString( 2 ) );
            p.setQt_min_estoque( cursor.getString( 3 ) );
            p.setValor( cursor.getString( 4 ));
            produtos.add( p );
        }
        cursor.close();
        return produtos;
    }

    public List<Produtos> obterCompras () {
        List<Produtos> produtos = new ArrayList <>();
        Cursor cursor = banco.query( "tb_ListaCompras", new String[] { "lcid", "nm_produto",
                        "qt_produto", "qt_min_produto", "valor"},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Produtos p = new Produtos();
            p.setId( cursor.getInt( 0 ) );
            p.setNome( cursor.getString( 1 ) );
            p.setQt_produtos( cursor.getString( 2 ) );
            p.setQt_min_estoque( cursor.getString( 3 ) );
            p.setValor( cursor.getString( 4 ) );
            produtos.add( p );
        }
        cursor.close();
        return produtos;
    }

    public String excluirCompra (Produtos p) {
        long resultado = banco.delete( "tb_ListaCompras", "lcid = ?" ,
        new String[]{
                Integer.toString( p.getId() )
        });
        if (resultado == -1)
            return "Erro ao excluir produto";
        else
            return "Produto excluído com sucesso";
    }

    public String excluirEstoque (Produtos p){
        long resultado = banco.delete( "tb_produtos", "id = ?", new String [] {
                Integer.toString( p.getId() )
        } );
        if (resultado == -1)
            return "Erro ao excluir produto";
        else
            return "Produto excluído com sucesso";
    }

    public String atualizarCompra (Produtos p) {
        ContentValues values = new ContentValues();

        values.put( "nm_produto", p.getNome() );
        values.put( "qt_produto", p.getQt_produtos() );
        values.put( "qt_min_produto", p.getQt_min_estoque() );
        values.put( "valor", p.getValor() );
        long resultado = banco.update( "tb_listacompras", values, "lcid = ?",
                new String []{
                        Integer.toString( p.getId() )
                });

        if (resultado == -1)
            return "Erro ao atualizar produto";
        else
            return "Produto atualizado com sucesso";
    }

    public void deletarTodaCompras () {
        banco.execSQL( "DELETE FROM tb_listacompras" );
    }

    public void deletarTodoEstoque () {
        banco.execSQL( "DELETE FROM tb_produtos" );
    }
}
