package com.example.appestoque.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appestoque.MainScreenActivity;
import com.example.appestoque.R;
import com.example.appestoque.usuario.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnCadastrar;
    private FirebaseAuth mAuth;
    private Usuario u;

    @Override
    protected void onCreate (Bundle savedBundleInstanceState)
    {
        super.onCreate( savedBundleInstanceState );
        setContentView( R.layout.activity_cadastro_usuario );

        txtEmail = findViewById( R.id.txt_email2 );
        txtSenha = findViewById( R.id.txt_password2 );
        btnCadastrar = findViewById( R.id.btn_cadastrar_usuario );
        mAuth = FirebaseAuth.getInstance();



        btnCadastrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                recuperarDados();
                criarLogin();
            }
        } );

    }

    private void recuperarDados()
    {
        if (txtEmail.getText().toString() == "" || txtSenha.getText().toString() == "")
        {
            Toast.makeText( this, "Você deve preencher todos os dados!", Toast.LENGTH_LONG );
        }else
        {
            u = new Usuario();
            u.setEmail( txtEmail.getText().toString() );
            u.setSenha( txtSenha.getText().toString() );

        }
    }

    private void criarLogin()
    {
        mAuth.createUserWithEmailAndPassword( u.getEmail(), u.getSenha() ).addOnCompleteListener( this, new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete (@NonNull Task <AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    u.setId( user.getUid() );
                    u.salvarDados();

                    startActivity( new Intent(CadastroActivity.this, MainScreenActivity.class ) );
                }else
                {
                    Toast.makeText( CadastroActivity.this, "Erro ao criar um login.", Toast.LENGTH_LONG ).show();
                }
            }
        } );
    }

}
