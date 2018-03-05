package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button login, register;
    EditText editLogin, editPass;
    DataBaseHelper db;
    CheckBox shared;
    public static Session ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(this);
        ss = new Session(this);

        if(ss.loggedin()){
            if(db.getUserToEdit(ss.getLogin()).getLevel().equals("1")){
                finish();
                startActivity(new Intent(MainActivity.this, MainProject.class));
            }else if(db.getUserToEdit(ss.getLogin()).getLevel().equals("2")){
                finish();
                startActivity(new Intent(MainActivity.this, ShowImoveis.class));
            }else{
                Toast.makeText(getApplicationContext(), "Este user já não tem permissões para estar aqui!!", Toast.LENGTH_SHORT).show();
            }
        }
        if (db.getAllUsers().size() == 0) {
            db.addUser("root", "root", "Super Utilizador", "1");
        }

        login = (Button)findViewById(R.id.btnLogin);
        register = (Button)findViewById(R.id.btnRegistar);
        editLogin = (EditText)findViewById(R.id.editLogin);
        editPass = (EditText)findViewById(R.id.editPass);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegistar:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            default:

        }
    }
    void login() {
        shared = (CheckBox) findViewById(R.id.keepLogged);
        String login = editLogin.getText().toString();
        String pass = editPass.getText().toString();
        if (db.getUser(login, pass)) {
            switch (db.getUserToEdit(login).getLevel()) {
                case "1":
                    savePreferences();
                    startActivity(new Intent(MainActivity.this, MainProject.class));
                    finish();
                    break;
                case "2":
                    savePreferences();
                    startActivity(new Intent(MainActivity.this, ShowImoveis.class));
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Este utilizador não tem premissões algumas!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Login ou password errados!", Toast.LENGTH_SHORT).show();
        }
    }
    private void savePreferences() {
        if(shared.isChecked()){
            ss.setLoggedin(true, editLogin.getText().toString());
        }else{
            ss.setLoggedin(false, editLogin.getText().toString());
        }
    }
}