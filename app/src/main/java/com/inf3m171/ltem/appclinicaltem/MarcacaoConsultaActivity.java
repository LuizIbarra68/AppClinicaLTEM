package com.inf3m171.ltem.appclinicaltem;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class MarcacaoConsultaActivity extends AppCompatActivity {

    private EditText etNomePaciente, etData;
    private Button btnData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcacao_consulta);

        etNomePaciente = (EditText) findViewById(R.id.etNomePaciente);
        etData = (EditText) findViewById(R.id.etDataConsulta);
        btnData = (Button) findViewById(R.id.btnSelecioanrData);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarData();

            }
        });



    }

    private void selecionarData() {

        final AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        final DatePicker calendario = new DatePicker(this);
        alerta.setView(calendario);
        alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String data = calendario.getDayOfMonth()+"/"+calendario.getMonth()+"/"+calendario.getYear();
                etData.setText(data);


            }
        });

        alerta.setPositiveButton("Cancelar", null);

        alerta.show();

    }


}
