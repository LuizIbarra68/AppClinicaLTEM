package com.inf3m171.ltem.appclinicaltem;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.inf3m171.ltem.appclinicaltem.model.Consulta;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha, etNome;
    private Button btnEntrar;
    private Button btnSignOut, btnRevokeAccess;
    private TextView tvCadastrar;

    private FirebaseAuth autenticacao;
    private FirebaseAuth.AuthStateListener stateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        tvCadastrar = (TextView) findViewById(R.id.tvCadastrar);

        autenticacao = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();



        tvCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,
                        CadastroActivity.class);
                startActivity(i);
            }
        });
        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent i = new Intent(LoginActivity.this, MenuUsuarioActivity.class);
                    startActivity(i);
                }
            }
        };

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar();
            }
        });


    }

    @Override
    protected void onStart() {
        autenticacao.addAuthStateListener(stateListener);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( stateListener != null){
            autenticacao.removeAuthStateListener(stateListener);
        }
    }

    private void entrar(){
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if ( !email.isEmpty()){
            autenticacao.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if ( !task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,
                                        "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }

    }

}
