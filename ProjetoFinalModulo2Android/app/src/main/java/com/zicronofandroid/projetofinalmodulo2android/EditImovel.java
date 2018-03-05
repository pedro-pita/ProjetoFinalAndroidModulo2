package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Imoveis;

public class EditImovel extends AppCompatActivity implements View.OnClickListener {

    Button voltar, cofirmEdit;
    EditText descricao, tipologia, localizacao, url, imovelOld;
    DataBaseHelper db;
    String oldDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_imovel);

        db = new DataBaseHelper(getApplicationContext());
        descricao = (EditText) findViewById(R.id.descricao);
        tipologia = (EditText) findViewById(R.id.tipologia);
        localizacao = (EditText) findViewById(R.id.localizacao);
        url = (EditText) findViewById(R.id.url_foto);

        voltar = (Button) findViewById(R.id.voltar);
        cofirmEdit = (Button) findViewById(R.id.cofirmEdit);
        cofirmEdit.setOnClickListener(this);
        voltar.setOnClickListener(this);

        Intent intent = getIntent();
        oldDesc = intent.getStringExtra("descricao");
        editarImovel();
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

    private void editarImovel() {
        Imoveis imoveis = db.getImovelByDesc(oldDesc);
        descricao.setText(imoveis.getDescricao());
        localizacao.setText(imoveis.getLocalizacao());
        tipologia.setText(imoveis.getTipologia());
        url.setText(imoveis.getUrl_foto());
    }

    private void confirmEdit() {
        if ((descricao.length() != 0) && (localizacao.length() != 0) && (tipologia.length() != 0) && (url.length() != 0)) {
            db.updateImovel(oldDesc, descricao.getText().toString(), localizacao.getText().toString(), tipologia.getText().toString(), url.getText().toString());
            Toast.makeText(getBaseContext(), "Valores modificados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    private void voltar() {
        finish();
        startActivity(new Intent(EditImovel.this, ShowImoveis.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(EditImovel.this, ShowImoveis.class));
    }
}
