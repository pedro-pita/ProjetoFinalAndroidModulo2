package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;

public class ManageUsers extends AppCompatActivity implements View.OnClickListener{

    Button add, voltar;
    EditText login,password,name,level;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_users);
        db = new DataBaseHelper(getApplicationContext());

        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        name = (EditText)findViewById(R.id.name);
        level = (EditText)findViewById(R.id.level);

        add = (Button)findViewById(R.id.add);
        voltar = (Button)findViewById(R.id.voltar);

        add.setOnClickListener(this);
        voltar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add:
                addUser();
                break;
            case R.id.voltar:
                finish();
                startActivity(new Intent(ManageUsers.this, ShowUsers.class));
                break;
        }
    }

    private void addUser() {
        if ((login.length() != 0) && (password.length() != 0) && (name.length() != 0) && (level.length() != 0)) {
            if(db.verifyUserByRegiste(login.getText().toString()) == false) {
                db.addUser(login.getText().toString(), password.getText().toString(), name.getText().toString(), level.getText().toString());
                Toast.makeText(getBaseContext(), "Registado com Sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Esta conta já existe! ", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Preencha todos os valores!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ManageUsers.this, ShowUsers.class));
    }
}