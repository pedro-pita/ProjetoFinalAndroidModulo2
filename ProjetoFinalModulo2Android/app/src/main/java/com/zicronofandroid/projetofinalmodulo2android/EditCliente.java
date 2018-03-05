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

public class EditCliente extends AppCompatActivity implements View.OnClickListener {

    Button voltar, cofirmEdit;
    EditText nome, idade, url;
    DataBaseHelper db;
    String oldNome;
    Clientes clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_cliente);
        db = new DataBaseHelper(getApplicationContext());

        nome = (EditText) findViewById(R.id.nome);
        idade = (EditText) findViewById(R.id.idade);
        url = (EditText) findViewById(R.id.url_foto);

        voltar = (Button) findViewById(R.id.voltar);
        cofirmEdit = (Button) findViewById(R.id.cofirmEdit);

        voltar.setOnClickListener(this);
        cofirmEdit.setOnClickListener(this);

        Intent intent = getIntent();
        oldNome = intent.getStringExtra("nome");
        Toast.makeText(getBaseContext(), oldNome, Toast.LENGTH_SHORT).show();
        editarCliente();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cofirmEdit:
                confirmEdit();
                break;
            case R.id.voltar:
                voltar();
                break;
        }
    }
    private void editarCliente(){
        clientes = db.getClienteByName(oldNome);
        nome.setText(clientes.getNome());
        idade.setText(clientes.getIdade());
        url.setText(clientes.getUrl_foto());
    }

    private void confirmEdit() {
        if ((nome.length() != 0) && (idade.length() != 0) && (url.length() != 0)) {
            db.updateCliente(oldNome, nome.getText().toString(), idade.getText().toString(), url.getText().toString());
            Toast.makeText(getBaseContext(), "Valores modificados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    private void voltar() {
        finish();
        startActivity(new Intent(EditCliente.this, ShowClientes.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(EditCliente.this, ShowClientes.class));
    }
}
