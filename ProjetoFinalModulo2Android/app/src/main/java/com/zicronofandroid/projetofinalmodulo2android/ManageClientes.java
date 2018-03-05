package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Clientes;
public class ManageClientes extends AppCompatActivity implements View.OnClickListener {

    Button add, voltar;
    EditText nome, idade, url;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_clientes);
        db = new DataBaseHelper(getApplicationContext());
        nome = (EditText) findViewById(R.id.nome);
        idade = (EditText) findViewById(R.id.idade);
        url = (EditText) findViewById(R.id.url_foto);
        add = (Button) findViewById(R.id.add);
        voltar = (Button) findViewById(R.id.voltar);
        add.setOnClickListener(this);
        voltar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addCliente();
                break;
            case R.id.voltar:
                //db.closeBD();
                finish();
                startActivity(new Intent(ManageClientes.this, ShowClientes.class));
                break;
        }
    }
    private void addCliente() {
        if ((nome.length() != 0) && (idade.length() != 0) && (url.length() != 0)) {
            db.addCliente(nome.getText().toString(), idade.getText().toString(), url.getText().toString());
            Toast.makeText(getBaseContext(), "Registado com Sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Preencha todos os valores!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ManageClientes.this, ShowClientes.class));
    }
}
