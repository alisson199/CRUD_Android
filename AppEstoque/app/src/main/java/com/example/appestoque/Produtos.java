package com.example.appestoque;

public class Produtos {

    private int id;
    private String nome;
    private String qt_produtos;
    private String qt_min_estoque;
    private String valor;

    public int getId () {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome () {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getQt_produtos () {
        return qt_produtos;
    }

    public void setQt_produtos (String qt_produtos) {
        this.qt_produtos = qt_produtos;
    }

    public String getQt_min_estoque () {
        return qt_min_estoque;
    }

    public void setQt_min_estoque (String qt_min_estoque) {
        this.qt_min_estoque = qt_min_estoque;
    }

    public String getValor () {
        return valor;
    }

    public void setValor (String valor) {
        this.valor = valor;
    }

    @Override
    public String toString (){
        return nome;
    }
}
