package com.inf3m171.ltem.appclinicaltem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnEntrar;
    TextView tvCadastrar;

//    private FirebaseAuth autenticacao;
//    private FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        tvCadastrar = (TextView) findViewById(R.id.tvCadastrar);



//        btnCadastrar.setOnClickListener() {
//            Intent i = new Intent(LoginActivity.this), Cadastr

    }
}
