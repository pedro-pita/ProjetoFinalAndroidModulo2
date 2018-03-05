package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Imoveis;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;

public class ShowImoveis extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    public static Context ctx;
    List<Imoveis> imoveis;
    RecyclerView rv;


    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL, null, this, this);

    private static String BASE_URL = "https://www.dropbox.com/s/x1gcatagum0z5ri/desafio.json?dl=1";

    public static DataBaseHelper db;
    Session ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data);
        db = new DataBaseHelper(getApplicationContext());
        ctx = this;
        ss = new Session(this);
        Toast.makeText(getApplicationContext(), "Login:" + ss.getLogin(), Toast.LENGTH_SHORT).show();
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getData();
        db.closeBD();
    }

    public void getData() {
        JsonTarefaImportImovel task = new JsonTarefaImportImovel();
        task.execute();
    }

    @Override
    public void onResponse(JSONObject response) {
        imoveis = new ArrayList<Imoveis>();
        List<Imoveis> listaI = new ArrayList<Imoveis>();

        try {
            JSONObject jsonImoveis = response.getJSONObject("imoveis");
            JSONArray jsonImovel = jsonImoveis.getJSONArray("imovel");

            for (int i = 0; i < jsonImovel.length(); i++) {
                JSONObject jsonImovelItem = jsonImovel.getJSONObject(i);
                String descricao = jsonImovelItem.getString("descricao");
                String url_foto = jsonImovelItem.getString("url_foto");
                String tipologia = jsonImovelItem.getString("tipologia");
                String localizacao = jsonImovelItem.getString("localizacao");
                listaI = db.getAllImoveis();
                Log.d(TAG, jsonImovelItem.getString("descricao"));
                if (listaI.size() < jsonImovel.length()) {
                    Log.d("CREATE", "IMOVEIS");
                    db.addImovel(descricao, localizacao, tipologia, url_foto);
                }
                Imoveis imovel = new Imoveis(url_foto, descricao, localizacao, tipologia);
                imoveis.add(imovel);
            }
            imoveis = db.getAllImoveis();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage().toString());
        }
        ImoveisAdapter adapter = new ImoveisAdapter(imoveis, this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error!" + error, Toast.LENGTH_SHORT).show();
        Log.d(TAG, String.valueOf(error));
    }

    private class JsonTarefaImportImovel extends AsyncTask<String, Void, Imoveis> {

        private int lengthI;

        @Override
        protected Imoveis doInBackground(String... params) {
            Imoveis imovel = new Imoveis();
            RequestQueue queue = Volley.newRequestQueue(ctx);
            queue.add(jsonObjectRequest);
            return imovel;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.config:
                boolean verificar = verificarPermissoes();
                if (verificar == true) {
                    irParaConfig();
                } else {
                    Toast.makeText(getBaseContext(), "Voce não tem permições para entrar aqui!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logout:
                logout();
        }
        return false;
    }

    void irParaConfig() {
        finish();
        startActivity(new Intent(ShowImoveis.this, ManageImoveis.class));
    }

    private void logout() {
        ss.setLoggedin(false, null);
        finish();
        startActivity(new Intent(ShowImoveis.this, MainActivity.class));
    }

    private boolean verificarPermissoes() {
        if (ss.loggedin()) {
            if (!(db.getUserToEdit(ss.getLogin()).getLevel().equals("1"))) {
                return false;
            }
        }
        return true;
    }
}