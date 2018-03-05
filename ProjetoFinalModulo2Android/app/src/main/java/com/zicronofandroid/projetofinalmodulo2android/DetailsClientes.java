package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Clientes;

public class DetailsClientes extends AppCompatActivity {

    TextView id, nome, idade;
    DataBaseHelper db;
    Clientes cliente;
    NetworkImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_clientes);

        db = new DataBaseHelper(getApplicationContext());
        nome = (TextView) findViewById(R.id.nomeView);
        idade = (TextView) findViewById(R.id.idadeView);
        id = (TextView) findViewById(R.id.idView);
        img = (NetworkImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        String nomeR = intent.getStringExtra("nome");
        Toast.makeText(getApplicationContext(), nomeR, Toast.LENGTH_SHORT).show();
        cliente = db.getClienteByName(nomeR);
        nome.setText(cliente.getNome());
        idade.setText(cliente.getIdade());
        id.setText(cliente.getId());
        img.setImageUrl(cliente.getUrl_foto(), VolleySingleton.getmInstance(getBaseContext()).getImageLoader());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}