package com.example.appestoque.usuario.modelo;

public class Usuario {
    private String id;
    private String email;
    private String senha;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getSenha () {
        return senha;
    }

    public void setSenha (String senha) {
        this.senha = senha;
    }

    public void salvarDados() {

    }
}
