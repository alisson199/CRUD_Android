package com.example.appestoque;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {
    private ConexaoBD conexao;
    private SQLiteDatabase banco;

    public EstoqueDAO(Context context) {
        conexao = new ConexaoBD( context );
        banco = conexao.getWritableDatabase();
    }

    public void inserirEstoque (Produtos produtos) {

        Cursor aux = banco.query( "tb_produtos", new String[]{"id", "nm_produtos", "qt_produtos", "qt_min_produtos", "preco"},
                "nm_produtos = ?",new String []{produtos.getNome()}, null, null, null, null);
        boolean achouproduto = false;
        int qtcont = 0;
        if(aux.getCount() > 0) {
            while (aux.moveToNext()){
                String preco = aux.getString( 4 );
                if ( Double.parseDouble( preco ) == Double.parseDouble( produtos.getValor() )) {
                    qtcont = Integer.parseInt( aux.getString( 2 ) ) + Integer.parseInt( produtos.getQt_produtos() );
                    produtos.setQt_produtos( Integer.toString( qtcont ) );
                    aux.close();
                    ContentValues values = new ContentValues();

                    values.put( "nm_produtos", produtos.getNome() );
                    values.put( "qt_produtos", produtos.getQt_produtos() );
                    values.put( "qt_min_produtos", produtos.getQt_produtos() );
                    values.put( "preco", produtos.getValor() );
                    banco.update( "tb_produtos", values, "preco = ?",
                            new String[]{
                                    produtos.getValor()
                            } );
                    achouproduto = true;
                }
            }
        }
        if (!achouproduto){
            ContentValues values = new ContentValues();
            values.put( "nm_produtos", produtos.getNome() );
            values.put( "qt_produtos", produtos.getQt_produtos() );
            values.put( "qt_min_produtos", produtos.getQt_min_estoque() );
            values.put( "preco", produtos.getValor() );
            banco.insert( "tb_produtos", null, values );

        }
        aux.close();
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

    public void inserirRelatorios(Produtos produtos, String data)
    {
        Cursor aux = banco.query( "tb_relatorios", new String[]{"rid", "nm_prod", "qt_prod", "qt_min_prod", "vlr"},
                "nm_prod = ?",new String []{produtos.getNome()}, null, null, null, null);
        boolean achouproduto = false;
        int qtcont = 0;
        if(aux.getCount() > 0) {
            while (aux.moveToNext()){
                String preco = aux.getString( 4 );
                if ( Double.parseDouble( preco ) == Double.parseDouble( produtos.getValor() )) {
                    qtcont = Integer.parseInt( aux.getString( 2 ) ) + Integer.parseInt( produtos.getQt_produtos() );
                    produtos.setQt_produtos( Integer.toString( qtcont ) );
                    aux.close();
                    ContentValues values = new ContentValues();

                    values.put( "nm_prod", produtos.getNome() );
                    values.put( "qt_prod", produtos.getQt_produtos() );
                    values.put( "qt_min_prod", produtos.getQt_min_estoque() );
                    values.put( "vlr", produtos.getValor() );
                    values.put("data", data);
                    banco.update( "tb_relatorios", values, "vlr = ?",
                            new String[]{
                                    produtos.getValor()
                            } );
                    achouproduto = true;
                }
            }
        }
        if (!achouproduto){
            ContentValues values = new ContentValues();

            values.put( "nm_prod", produtos.getNome() );
            values.put( "qt_prod", produtos.getQt_produtos() );
            values.put( "qt_min_prod", produtos.getQt_min_estoque() );
            values.put( "vlr", produtos.getValor() );
            values.put("data", data);

            banco.insert( "tb_relatorios", null, values );

        }
        aux.close();
        /*
        ContentValues values = new ContentValues();

        values.put( "nm_prod", produtos.getNome() );
        values.put( "qt_prod", produtos.getQt_produtos() );
        values.put( "qt_min_prod", produtos.getQt_min_estoque() );
        values.put( "vlr", produtos.getValor() );
        values.put("data", data);

        banco.insert( "tb_relatorios", null, values );

        */
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
        //************************* tb Estoque
        Cursor cursor = banco.query( "tb_produtos", new String[]{"id", "nm_produtos", "qt_produtos", "qt_min_produtos", "preco"},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Produtos p = new Produtos();
            p.setId( cursor.getInt( 0 ) );
            p.setNome( cursor.getString( 1 ) );
            p.setQt_produtos( cursor.getString( 2 ) );
            p.setQt_min_estoque( cursor.getString( 3 ) );
            p.setValor( cursor.getString( 4 ));

            //Se a quantidade minima é maior que a quantidade existente, então adicione.
            if(Integer.parseInt( p.getQt_produtos() ) < Integer.parseInt( p.getQt_min_estoque() ))
            {
                int aux = Integer.parseInt( p.getQt_min_estoque()) - Integer.parseInt( p.getQt_produtos() );
                p.setQt_produtos( Integer.toString( aux ) );
                produtos.add( p );
            }
        }
        cursor.close();

        //********************** tb Lista Compras
        cursor = banco.query( "tb_ListaCompras", new String[] { "lcid", "nm_produto",
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

    public int temItem () {
        Cursor cursor = banco.query( "tb_ListaCompras", new String[] { "lcid", "nm_produto",
                        "qt_produto", "qt_min_produto", "valor"},
                null, null, null, null, null);

        return cursor.getCount();
    }

    public String obterTotalMensal(String mes, String ano) {
        double total = 0;
        String data = ano + "/" + mes;
        Cursor cursor = banco.query( "tb_relatorios", new String[]{"rid", "nm_prod", "qt_prod", "qt_min_prod", "vlr","data"},
                "data = ?",new String[]{data}, null, null, null, null);
        while (cursor.moveToNext()) {
            total += Integer.parseInt( cursor.getString( 2 ) ) * Double.parseDouble( cursor.getString( 4 ) );
        }
        cursor.close();
        return Double.toString( total );
    }

    public List<Produtos> obterRelatorios(String mes, String ano) {
        List<Produtos> produtos = new ArrayList <>();
        String data = ano + "/" + mes;
        Cursor cursor = banco.query( "tb_relatorios", new String[]{"rid", "nm_prod", "qt_prod", "qt_min_prod", "vlr","data"},
                "data = ?",new String[]{data}, null, null, null, null);

        while (cursor.moveToNext()) {
            Produtos p = new Produtos();
            p.setId( cursor.getInt( 0 ) );
            p.setNome( cursor.getString( 1 ) );
            p.setQt_produtos( cursor.getString( 2 ) );
            p.setQt_min_estoque( cursor.getString( 3 ) );
            p.setValor( cursor.getString( 4 ));
            produtos.add( p );
        }
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

    public void atualizarCompra (Produtos p) {
        ContentValues values = new ContentValues();

        values.put( "nm_produto", p.getNome() );
        values.put( "qt_produto", p.getQt_produtos() );
        values.put( "qt_min_produto", p.getQt_min_estoque() );
        values.put( "valor", p.getValor() );
        banco.update( "tb_listacompras", values, "lcid = ?",
                new String []{
                        Integer.toString( p.getId() )
                });
    }

    public void deletarTodaCompras () {
        Cursor aux = banco.query( "tb_listacompras",new String []{"nm_produto"},null,null,null,null,null );
        if (aux.getCount()>0)
            banco.execSQL( "DELETE FROM tb_listacompras" );
        aux.close();
    }

    public void deletarTodoEstoque () {

        banco.execSQL( "DELETE FROM tb_produtos" );
    }

    public void retirarUnEstoque (Produtos p) {
        ContentValues values = new ContentValues();

        //values.put( "nm_produtos", p.getNome() );
        values.put( "qt_produtos", p.getQt_produtos() );
        //values.put( "qt_min_produtos", p.getQt_min_estoque() );
        //values.put( "preco", p.getValor() );
        banco.update( "tb_produtos", values, "id = ?",
                new String []{
                        Integer.toString( p.getId() )
                });

    }

}
