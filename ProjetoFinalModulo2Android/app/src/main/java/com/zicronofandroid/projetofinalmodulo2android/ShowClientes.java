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
import com.zicronofandroid.projetofinalmodulo2android.model.Clientes;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;

public class ShowClientes extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    public static Context ctx;
    List<Clientes> clientes;
    RecyclerView rv;

    JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(
            Request.Method.GET,
            BASE_URL,
            null,
            this,
            this);

    private static String BASE_URL="https://www.dropbox.com/s/x1gcatagum0z5ri/desafio.json?dl=1";
    public static DataBaseHelper db;
    Session ss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data);
        db = new DataBaseHelper(getApplicationContext());
        ctx=this;
        ss = new Session(this);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getData();
        db.closeBD();
    }

    public void getData(){
        JsonTarefaImportCliente task = new JsonTarefaImportCliente();
        task.execute();
    }

    @Override
    public void onResponse (JSONObject response){
        clientes=new ArrayList<Clientes>();
        List<Clientes> lista = new ArrayList<Clientes>();

        try{
            JSONObject jsonClientes= response.getJSONObject("clientes");
            JSONArray jsonCliente= jsonClientes.getJSONArray("cliente");

            for(int i=0;i<jsonCliente.length();i++){
                JSONObject jsonClienteItem = jsonCliente.getJSONObject(i);
                String nome = jsonClienteItem.getString("nome");
                String idade = jsonClienteItem.getString("idade");
                String url_foto = jsonClienteItem.getString("url_foto");

                lista=db.getAllClientes();

                if(lista.size()<jsonCliente.length()) {
                    Log.d("CREATE", "CLIENTES");
                    db.addCliente(nome, idade, url_foto);
                }

                Clientes cliente = new Clientes(nome, idade, url_foto);
                clientes.add(cliente);
            }
            clientes = db.getAllClientes();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, e.getMessage().toString());
        }

        ClientesAdapter adapter = new ClientesAdapter(clientes,this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Erro!" + error, Toast.LENGTH_SHORT).show();
        Log.d(TAG, String.valueOf(error));
    }

    private class JsonTarefaImportCliente extends AsyncTask<String, Void, Clientes> {

        @Override
        protected Clientes doInBackground(String... params) {
            Clientes cliente = new Clientes();
            RequestQueue queue = Volley.newRequestQueue(ctx);
            queue.add(jsonObjectRequest);
            return cliente;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.config:
                irParaConfig();
                break;
            case R.id.logout:
                logout();
        }
        return false;
    }

    void irParaConfig() {
        finish();
        startActivity(new Intent(ShowClientes.this, ManageClientes.class));
    }
    private void logout(){
        ss.setLoggedin(false, null);
        finish();
        startActivity(new Intent(ShowClientes.this, MainActivity.class));
    }

}
