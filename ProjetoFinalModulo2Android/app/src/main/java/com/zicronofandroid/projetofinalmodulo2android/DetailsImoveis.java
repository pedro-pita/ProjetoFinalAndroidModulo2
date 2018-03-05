package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Imoveis;

public class DetailsImoveis extends AppCompatActivity {

    TextView id, localizacao, descricao, tipologia;
    DataBaseHelper db;
    Imoveis imovel;
    NetworkImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_imoveis);

        db = new DataBaseHelper(getApplicationContext());

        id = (TextView) findViewById(R.id.idView);
        localizacao = (TextView) findViewById(R.id.localizacaoView);
        descricao = (TextView) findViewById(R.id.descricaoView);
        tipologia = (TextView) findViewById(R.id.tipologiaView);
        img = (NetworkImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        String descricaoT = intent.getStringExtra("descricao");
        imovel = db.getImovelByDesc(descricaoT);
        localizacao.setText(imovel.getLocalizacao());
        descricao.setText(imovel.getDescricao());
        tipologia.setText(imovel.getTipologia());
        id.setText(String.valueOf(imovel.getId()));
        img.setImageUrl(imovel.getUrl_foto(), VolleySingleton.getmInstance(getBaseContext()).getImageLoader());
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}