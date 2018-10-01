package com.inf3m171.ltem.appclinicaltem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inf3m171.ltem.appclinicaltem.model.Consulta;

public class CadastroActivity extends AppCompatActivity {


    private EditText etNome, etEmail, etSenha, etConfirmaSenha;
    private Button btnSalvar;
    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String erro = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = (EditText) findViewById(R.id.etNomeUsuario);
        etEmail = (EditText) findViewById(R.id.etEmailUsuario);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etConfirmaSenha = (EditText) findViewById(R.id.etConfirmaSenha);

        btnSalvar = (Button) findViewById(R.id.btnSalvarUsuario);

        btnSalvar.setOnClickListener(oncli);
    }

    private View.OnClickListener oncli = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(CadastroActivity.this, MenuUsuarioActivity.class);
            startActivity(i);
            criarUsuario();
        }
    };




    private void criarUsuario(){
        final String nome = etNome.getText().toString();
        String senha = etSenha.getText().toString();
        String confirmaSenha = etConfirmaSenha.getText().toString();
        final String email = etEmail.getText().toString();

        if ( !senha.isEmpty() && senha.equals(confirmaSenha) && !email.isEmpty()){
            autenticacao = FirebaseAuth.getInstance();
            autenticacao.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if ( task.isSuccessful()){
                                String id = autenticacao.getCurrentUser().getUid();
                                reference = database.getReference( "usuarios").child(id);
                                reference.child("nome").setValue(etNome.getText().toString());
                                reference.child("email").setValue(email);


                            }else {
                                erro = "Não foi possivel criar o usuário";
                            }

                        }
                    });




        }else {
            erro = "O campo e-mail deve ser preenchido e os campos de" +
                    " senha devem ser iguais!";
        }

        if ( !erro.isEmpty()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Atenção");
            alerta.setMessage(erro);
            alerta.setNeutralButton("OK", null);
            alerta.show();

        }else {
            finish();
        }
    }
}
