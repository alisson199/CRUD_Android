package com.example.appestoque.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appestoque.EstoqueDAO;
import com.example.appestoque.MainScreenActivity;
import com.example.appestoque.Produtos;
import com.example.appestoque.R;
import com.example.appestoque.usuario.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnlogin;
    private Button btncadastrar;
    private FirebaseAuth mAuth;
    private Usuario u;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_usuario );

        btnlogin = findViewById( R.id.btn_login );
        btncadastrar = findViewById( R.id.btn_cadastrar );
        txtEmail = findViewById( R.id.txt_email );
        txtSenha = findViewById( R.id.txt_password );

        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                receberDados();
                fazerLogin();
            }
        } );

        btncadastrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                telaCadastrar();
            }
        } );
    }

    private void telaCadastrar () {
        startActivity( new Intent( this, CadastroActivity.class ) );
    }

    private void receberDados () {
        u = new Usuario();
        u.setEmail(txtEmail.getText().toString());
        u.setSenha(txtSenha.getText().toString());


    }
    private void fazerLogin()
    {
        mAuth.signInWithEmailAndPassword(u.getEmail(), u.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task <AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity( new Intent(LoginActivity.this, MainScreenActivity.class ) );
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}