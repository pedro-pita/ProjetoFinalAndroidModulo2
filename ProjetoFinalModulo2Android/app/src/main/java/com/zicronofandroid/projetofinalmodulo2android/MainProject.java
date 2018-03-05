package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;

public class MainProject extends AppCompatActivity implements View.OnClickListener {

    private Button contas, imoveis, clientes;

    Session ss;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_project);
        ss = new Session(this);
        db = new DataBaseHelper(this);
        if(!(db.getUserToEdit(ss.getLogin()).getLevel().equals("1"))){
            Toast.makeText(getApplicationContext(), "Este user já não tem permissões para estar aqui!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainProject.this, ShowImoveis.class));
        }
        contas = (Button) findViewById(R.id.contas);
        imoveis = (Button) findViewById(R.id.imoveis);
        clientes = (Button) findViewById(R.id.clientes);
        Toast.makeText(getApplicationContext(), "Login:" + ss.getLogin(),Toast.LENGTH_SHORT).show();
        contas.setOnClickListener(this);
        imoveis.setOnClickListener(this);
        clientes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contas:
                contas();
                break;
            case R.id.imoveis:
                imoveis();
                break;
            case R.id.clientes:
                clientes();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        logout();
        return false;
    }

    private void logout(){
        ss.setLoggedin(false, null);
        finish();
        startActivity(new Intent(MainProject.this,MainActivity.class));
    }

    void contas(){
        Intent intent = new Intent(MainProject.this,ShowUsers.class);
        startActivity(intent);
    }

    void imoveis(){
        Intent intent = new Intent(MainProject.this,ShowImoveis.class);
        startActivity(intent);
    }

    void clientes(){
        Intent intent = new Intent(MainProject.this,ShowClientes.class);
        startActivity(intent);
    }
}