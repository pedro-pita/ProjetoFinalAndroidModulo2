package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;

public class ManageImoveis extends AppCompatActivity implements View.OnClickListener {

    Button add, voltar,cofirmEdit;
    EditText descricao, tipologia, localizacao, url;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_imoveis);

        db = new DataBaseHelper(getApplicationContext());
        descricao = (EditText) findViewById(R.id.descricao);
        tipologia = (EditText) findViewById(R.id.tipologia);
        localizacao = (EditText) findViewById(R.id.localizacao);
        url = (EditText) findViewById(R.id.url_foto);

        add = (Button) findViewById(R.id.add);
        voltar = (Button) findViewById(R.id.voltar);
        cofirmEdit = (Button) findViewById(R.id.cofirmEdit);
        cofirmEdit.setVisibility(View.INVISIBLE);

        add.setOnClickListener(this);
        voltar.setOnClickListener(this);
        cofirmEdit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addImovel();
                break;
            case R.id.voltar:
                finish();
                startActivity(new Intent(ManageImoveis.this, ShowImoveis.class));
                break;
        }
    }
    private void addImovel() {
        if ((descricao.length() != 0) && (tipologia.length() != 0) && (localizacao.length() != 0) && (url.length() != 0)) {
            db.addImovelByUser(descricao.getText().toString(), localizacao.getText().toString(), tipologia.getText().toString(), url.getText().toString());
            Toast.makeText(getBaseContext(), "Registado com Sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Preencha todos os valores!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ManageImoveis.this, ShowImoveis.class));
    }
}
